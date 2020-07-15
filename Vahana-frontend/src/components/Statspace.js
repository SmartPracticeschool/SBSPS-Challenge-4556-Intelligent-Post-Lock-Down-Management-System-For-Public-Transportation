import React, { Component } from "react";
import { Layout, Statistic, Card, Row, Col, DatePicker } from "antd";
import moment from 'moment';

import stats from "../api/stats";
import NivoBar from "./NivoBar";

const { Content } = Layout;

const dateFormat = "DD-MM-YYYY";

class Statspace extends Component {
  constructor(props) {
    super(props);
    this.state = {
      displayData: {
        daily_passenger_count: "loading...",
        total_passenger_count: "loading...",
        tickets_booked_each_day: "loading...",
        daily_social_distancing_violations: "loading...",
        daily_facial_mask_violations: "loading...",
        total_revenue_collected: "loading...",
        daily_vehicle_traffic: "loading...",
        weekly_vehicle_traffic: "loading...",
        vehicles_sanitised_per_week: "loading...",
        reliability_score: "loading...",
      },
      graphData: [],
      graphKeys: [],
      dataDay: moment().format(dateFormat)
    };
    this.showGraph("daily_passenger_count");
  }

  componentDidMount(){
    this.showStats();
  }

  componentDidUpdate(prevProps, prevState) {
    if(prevState.dataDay !== this.state.dataDay){
        this.showStats();
    }
    
  }

  showStats = () => {
    stats.get(`${this.props.user.station._id}/${this.state.dataDay}`).then((res) => {
      console.log("Stats Data: ",res);
      this.setState({
        displayData: res.data
      });
    });
  }

  showGraph = (name) => {
    //make a get call to backend
    console.log(name);
    stats.get(`${this.props.user.station._id}/${this.state.dataDay}/graphs/${name}`).then((res) => {
      console.log(res);
      this.setState({
        graphData: res.data.data,
        graphKeys: res.data.keys
      });
      console.log("0: ", this.state.graphData[0]);
    });
   
  };

  render() {
    return (
      <Layout style={{ padding: "0.5% 1.5%" }}>
        <Content>
          <Row gutter={[4, 4]}>
            <Col span={4}>
            <DatePicker format={dateFormat} defaultValue={moment()} onChange={(dateMoment, dateString)=>this.setState({dataDay:dateString})}/>
            </Col>
          </Row>

          <Row gutter={[8, 8]}>
            <Col span={8}>
              <Card
                hoverable
                onClick={() => {
                  this.showGraph("daily_passenger_count");
                }}
              >
                <Statistic
                  title="Daily passenger count"
                  value={this.state.displayData.daily_passenger_count}
                />
              </Card>
            </Col>
            <Col span={8}>
              <Card hoverable>
                <Statistic
                  title="Total passenger count"
                  value={this.state.displayData.total_passenger_count}
                />
              </Card>
            </Col>
            <Col span={8}>
              <Card hoverable>
                <Statistic
                  title="Tickets booked each day"
                  value={this.state.displayData.tickets_booked_each_day}
                />
              </Card>
            </Col>
          </Row>

          <Row gutter={[8, 8]}>
            <Col span={8}>
              <Row gutter={[8, 8]}>
                <Col span={24}>
                  <Card
                    hoverable
                    onClick={() => {
                      this.showGraph("daily_social_distancing_violations");
                    }}
                  >
                    <Statistic
                      title="Daily social distancing violations"
                      value={
                        this.state.displayData
                          .daily_social_distancing_violations
                      }
                    />
                  </Card>
                </Col>
              </Row>
              <Row gutter={[8, 8]}>
                <Col span={24}>
                  <Card
                    hoverable
                    onClick={() => {
                      this.showGraph("daily_facial_mask_violations");
                    }}
                  >
                    <Statistic
                      title="Daily facial mask violations"
                      value={
                        this.state.displayData.daily_facial_mask_violations
                      }
                    />
                  </Card>
                </Col>
              </Row>
              <Row gutter={[8, 8]}>
                <Col span={24}>
                  <Card hoverable>
                    <Statistic
                      title="Total Revenue collected"
                      value={this.state.displayData.total_revenue_collected}
                    />
                  </Card>
                </Col>
              </Row>
            </Col>
            <Col span={16}>
              <Card hoverable>
                <div style={{ height: 305 }}>
                  <NivoBar data={this.state.graphData} keys={this.state.graphKeys}/>
                </div>
              </Card>
            </Col>
          </Row>
          <Row gutter={[8, 0]}>
            <Col span={24}>
              <Card hoverable>
                <Row gutter={[8, 0]}>
                  <Col span={6}>
                    <Statistic
                      title="Daily Vehicle traffic"
                      value={this.state.displayData.daily_vehicle_traffic}
                    />
                  </Col>
                  <Col span={6}>
                    <Statistic
                      title="Weekly Vehicle traffic"
                      value={this.state.displayData.weekly_vehicle_traffic}
                    />
                  </Col>
                  <Col span={6}>
                    <Statistic
                      title="Vehicles sanitised per week"
                      value={this.state.displayData.vehicles_sanitised_per_week}
                    />
                  </Col>
                  <Col span={6}>
                    <Statistic
                      title="Reliability Score"
                      value={this.state.displayData.reliability_score}
                    />
                  </Col>
                </Row>
              </Card>
            </Col>
          </Row>
        </Content>
      </Layout>
    );
  }
}

export default Statspace;
