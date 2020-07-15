import React, { Component } from "react";
import RTCluster from "./RTCluster";
import { Card, Row } from "antd";
import GraphTile from "./GraphTile";
import { SocketConsumer } from "../contexts/socketContext";

class InfoPane extends Component {
  render() {
    const { area, camera } = this.props;
    return (
      <div>
        <Card title="Today's stats" justify="center">
          <Row gutter={[16, 16]}>
            <GraphTile />
          </Row>
          <Row gutter={[16, 16]}>
            <SocketConsumer>
              {(socket) => (
                <RTCluster
                  socket={socket}
                  area={area}
                  camera={camera}
                  datatype="average"
                />
              )}
            </SocketConsumer>
          </Row>
          <Row gutter={[16, 12]}>
            <SocketConsumer>
              {(socket) => (
                <RTCluster
                  socket={socket}
                  area={area}
                  camera={camera}
                  datatype="average"
                />
              )}
            </SocketConsumer>
          </Row>
        </Card>
      </div>
    );
  }
}

export default InfoPane;
