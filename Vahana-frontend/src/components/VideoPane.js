import React from "react";
import { Card } from "antd";
import { Row } from "antd";
import RTCluster from "./RTCluster";
import altImg from "../images/camera-unavailable.png";
import Meta from "antd/lib/card/Meta";
import { SocketConsumer } from "../contexts/socketContext";
import './VideoPane.css'
//import video from "../api/video";

const baseurl = "http://localhost:8000/video/";
//console.log(video);

class VideoPane extends React.Component {
  handleError = (e) => {
    e.target.onerror = null;
    e.target.src = altImg;
  };

  render = () => {
    const { area, camera } = this.props;
    const title = area + (camera == null ? " " : " / " + camera);
    const extraurl =
      area.split(" ").join("").toLowerCase() +
      "/" +
      camera.split(" ").join("").toLowerCase();
    console.log(title);
    return (
      <Card 
        style={{paddingBottom: "3.2%"}}
        title={title}
        cover={
          <Card>
            <Row justify="center">
              <div className="responsive-container">
                <img
                  src={baseurl + extraurl}
                  width="80%"
                  onError={this.handleError}
                  alt={baseurl + extraurl}
                />
              </div>
            </Row>
          </Card>
        }
      >
        <Meta
          description={
            <Row gutter={[16, 16]} justify="center">
              <SocketConsumer>
                {(socket) => (
                  <RTCluster
                    socket={socket}
                    area={area}
                    camera={camera}
                    datatype="instant"
                  />
                )}
              </SocketConsumer>
            </Row>
          }
        />
      </Card>
    );
  };
}

export default VideoPane;
