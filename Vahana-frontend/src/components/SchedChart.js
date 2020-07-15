import React, { Component } from "react";
import { Table, Button } from "antd";
import schedule from "../api/schedule.js";

const { Column } = Table;

class SchedChart extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
    };
    schedule.get(`/${props.user.station._id}`).then((res) => {
      console.log(res);
      this.setState({ data: res.data });
    });
  }
  componentDidMount() {
    this.props.socket.on("/schedule/changes", (change) => {
      const { index, newRow, instr } = change;
      let newData = this.state.data;
      if (instr === "update") newData[index] = newRow;
      else if (instr === "insert") newData.splice(index, 0, newRow);
      else if (instr === "delete") newData.splice(index, 1, newRow);

      this.setState({
        data: newData,
      });
    });
  }

  render() {
    return (
      <Table dataSource={this.state.data}>
        <Column title="Vehicle ID" dataIndex="vehicle_id" key="id" width="10%" />
        <Column title="Vehicle Name" dataIndex="vehicle_name" key="name" width="13%" />
        <Column title="Arrival time" dataIndex="arrival_time" key="arr" width="10%" />
        <Column title="Departure time" dataIndex="departure_time" key="dep" width="10%" />
        <Column title="Occupancy" dataIndex="occupancy" key="occ" width="5%" />
        <Column title="Source" dataIndex="source" key="src" width="10%" />
        <Column title="Destination" dataIndex="destination" key="dest" width="10%" />
        <Column
          title="Safety status"
          dataIndex="safety_status"
          key="safstat"
          width="10%"
        />
        <Column
          title="Actions"
          dataIndex="actions"
          key="actions"
          render={(actions) =>
            actions.map((action,index) => <Button key={index}>{action}</Button>)
          }
        />
      </Table>
    );
  }
}

export default SchedChart;
