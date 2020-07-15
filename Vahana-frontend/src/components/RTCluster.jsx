import React, { Component } from "react";
import { Card, Col, Statistic } from "antd";

//import { areas } from "../mockDB/cameras.js";

class RTCluster extends Component {
  constructor(props) {
    super(props);
    this.state = {
      displayData: {
        tiles: [
          {
            title: "Crowd Density",
            value: null,
          },
          {
            title: "Social Distance violations",
            value: null,
          },
          {
            title: "Mask violations",
            value: null,
          },
        ],
      },
      loading: true,
    };

    const { area, camera, datatype, socket } = props;
    this.socket = socket;

    this.event =
      "/data/" +
      area.split(" ").join("").toLowerCase() +
      "/" +
      camera.split(" ").join("").toLowerCase() +
      "/" +
      datatype.split(" ").join("").toLowerCase();

    console.log("Event called: ", "cameras");
    this.socket.emit("cameras", this.event);
  }

  componentDidMount() {
    this.socket.on(this.event, (newData) => {
      if (newData !== undefined) {
        this.setState({
          loading: false,
          displayData: newData,
        });
      }
    });
  }

  render() {
    return (
      this.state.displayData.tiles.map((tile, index) => (
        <Col key={index}>
          <Card
            hoverable
            key={index}
            size="small"
            style={{ width: 187 }}
            loading={this.state.loading}
          >
            <Statistic title={tile.title} value={tile.value} />
          </Card>
        </Col>
      ))
    );
  }
}

export default RTCluster;



//insert in useEffect to use mockdb

// let camObj;
// const { area, camera, datatype } = this.props;
// let req_id =
//   area.split(" ").join("").toLowerCase() +
//   "_" +
//   camera.split(" ").join("").toLowerCase();
// console.log(req_id);
// areas.forEach((ar, index) => {
//   if (camObj === undefined)
//     camObj = ar.cameras.find((cam) => cam._id === req_id);
// });

// console.log(camObj);
// if (camObj.data != null) {
//   if (datatype === "instant")
//     this.displayData = {
//       tiles: [
//         {
//           title: "Crowd Density",
//           value: camObj.data.instant.crowdDensity,
//         },
//         {
//           title: "Social Distance violations",
//           value: camObj.data.instant.distanceViolations,
//         },
//         {
//           title: "Mask violations",
//           value: camObj.data.instant.maskViolations,
//         },
//       ],
//     };
//   else
//     this.displayData = {
//       tiles: [
//         {
//           title: "Average Crowd Density",
//           value: camObj.data.avg.crowdDensity,
//         },
//         {
//           title: "Social Distance violations",
//           value: camObj.data.avg.distanceViolations,
//         },
//         {
//           title: "Mask violations",
//           value: camObj.data.avg.maskViolations,
//         },
//       ],
//     };
// }
