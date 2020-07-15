import React, { Component } from "react";
import { Tabs, Card } from "antd";
import { SchedChart, InsightPage } from "./";
import { SocketConsumer } from "../contexts/socketContext";
import { UserConsumer } from "../contexts/userContext";

const { TabPane } = Tabs;

class VehicleApp extends Component {
  render() {
    return (
      <Card>
        <Tabs defaultActiveKey="1" size="large">
          <TabPane tab="Schedule" key="1">
            <SocketConsumer>
              {(socket) => (
                <UserConsumer>
                  {(user) => <SchedChart socket={socket} user={user} />}
                </UserConsumer>
              )}
            </SocketConsumer>
          </TabPane>
          <TabPane tab="Insights" key="3">
            <InsightPage />
          </TabPane>
        </Tabs>
      </Card>
    );
  }
}

export default VehicleApp;
