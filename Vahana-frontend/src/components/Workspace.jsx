import React, { Component } from "react";
import { Row, Col, Layout } from "antd";
import VideoPane from "./VideoPane";
import InfoPane from "./InfoPane";

const { Content } = Layout;

class Workspace extends Component {
  render() {
    const { area, camera } = this.props;
    // const title = area + (camera==null?' ':' / '+ camera);
    //const extraurl = lodash.kebabCase(area)+'/'+lodash.kebabCase(camera);

    return (
      <Layout style={{ padding: "1% 1%" }}>
        <Content>
          <Row gutter={[16, 16]}>
            <Col span={12}>
              <VideoPane area={area} camera={camera} />
            </Col>

            <Col span={12}>
              <InfoPane area={area} camera={camera} />
            </Col>
          </Row>
        </Content>
      </Layout>
    );
  }
}

export default Workspace;
