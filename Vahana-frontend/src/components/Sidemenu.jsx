import React from "react";
import "antd/dist/antd.less";
import { Layout, Menu, message } from "antd";
import {
  EnvironmentOutlined,
  PlusOutlined,
  CameraOutlined,
  PieChartOutlined,
} from "@ant-design/icons";
import "./Sidemenu.css";
import CameraCreateForm from "./CameraCreateForm";
import menu from "../api/menu";
// import { areas, addCamera } from "../mockDB/cameras.js";

const { Sider } = Layout;
const { SubMenu } = Menu;

class Sidemenu extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showModal: false,
      contents: [],
      requestOnProcess: false
    };
  }

  componentDidMount() {
    const { station } = this.props.user;
    menu.get(`/${station._id}`).then((res) => {
      console.log(res);
      this.setState({
        showModal: false,
        contents: res.data.menuItems,
      });
    });
  }

  handleClick = (e) => {
    const selection = [
      e.item.props.parenttitle,
      e.item.props.children[1].props.children,
    ];
    console.log(selection);
    this.props.onSelect(selection);
  };

  showErrorMsg = (text) => {
    message.error(text);
  };

  showSuccessMsg = (text) => {
    message.success(text);
  };

  onCreate = (values) => {
    //const resp = addCamera(values);
    const { station } = this.props.user;
    console.log(values);
    this.setState({requestOnProcess: true});
    menu.post(`/addcam/${station._id}`, { values }).then((res) => {
      console.log(res);
      if (res.data.success)
      {
        this.setState({ contents: res.data.menuItems.menuItems, showModal: false, requestOnProcess: false });
        this.showSuccessMsg("Camera added successfully");
      }
      else
      {
        this.setState({requestOnProcess: false});
        this.showErrorMsg(res.data.message);
      }
    });
  };

  render() {
    return (
      <Sider width={150} style={{ minHeight: "91vh" }}>
        <Menu mode="vertical" style={{ height: "100%", borderRight: 0 }}>
          <Menu.Item icon={<PieChartOutlined />} onClick={this.handleClick}>
            Stats
          </Menu.Item>
          {this.state.contents.map((area) => (
            <SubMenu key={area._id} icon={<EnvironmentOutlined />} title={area.name}>
              {area.cameras.map((camera) => (
                <Menu.Item
                  key={camera._id}
                  onClick={this.handleClick}
                  parenttitle={area.name}
                  icon={<CameraOutlined />}
                >
                  {camera.name}
                </Menu.Item>
              ))}
            </SubMenu>
          ))}

          <Menu.Item
            icon={<PlusOutlined />}
            onClick={() => {
              this.setState({ showModal: true });
            }}
          >
            Add camera
          </Menu.Item>
        </Menu>
        <CameraCreateForm
          visible={this.state.showModal}
          onCreate={this.onCreate}
          onCancel={() => {
            this.setState({ showModal: false });
          }}
          loading={this.state.requestOnProcess}
        />
      </Sider>
    );
  }
}

export default Sidemenu;
