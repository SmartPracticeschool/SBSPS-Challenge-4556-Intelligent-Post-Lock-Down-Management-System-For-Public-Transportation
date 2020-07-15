import React, { Component } from "react";
import "antd/dist/antd.less";
import "./TitleBar.css";
import { Menu, Layout, Typography} from "antd";
import { AlertOutlined } from "@ant-design/icons";

const { Header } = Layout;
const { Title, Text } = Typography;

class TitleBar extends Component {
  handleClick = (e) => {
    const selection = e.item.props.children[1];
    console.log(selection);
    this.props.onSelect(selection);
  };

  render() {
    return (
      <Header>
        <div
          style={{
            float: "left",
            padding: "10px",
            paddingTop: "5px",
            paddingLeft: 0,
          }}
        >
          <AlertOutlined style={{ fontSize: "35px", color: "white" }} />
        </div>

        <div className="logo">
          <Title style={{ color: "white" }} level={2}>
            {this.props.title}
          </Title>
        </div>
        <Text key="0" style={{  color: "lightgray" }}>
              {this.props.user.station.name}
              
            </Text>
        <div style={{ float: "right" }}>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={["2"]} style={{display:"inline"}}>
            
            <Menu.Item key="1" onClick={this.handleClick}>
              Control Panel
            </Menu.Item>
            <Menu.Item key="2" onClick={this.handleClick}>
              Surveillance
            </Menu.Item>
            <Menu.Item key="3" onClick={this.handleClick}>
              Vehicles
            </Menu.Item>
          </Menu>
        </div>
      </Header>
    );
  }
}

export default TitleBar;
