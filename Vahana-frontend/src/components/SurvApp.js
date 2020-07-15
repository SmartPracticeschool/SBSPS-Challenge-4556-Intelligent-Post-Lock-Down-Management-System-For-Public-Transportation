import React, { Component } from "react";
import { Sidemenu, Workspace } from "./";
import { Layout } from "antd";
import Statspace from "./Statspace";
import { UserConsumer } from "../contexts/userContext";

class SurvApp extends Component {
  state = {
    area: null,
    camera: "Stats",
  };

  handleSideSelect = (selection) => {
    this.setState({
      area: selection[0],
      camera: selection[1],
    });
  };

  render() {
    if (this.state.camera === "Stats")
      return (
        <Layout>
          {/*pass station={name:" ",type:" "} to sidemenu*/}
          <UserConsumer>
            {(user) => (
              <Sidemenu onSelect={this.handleSideSelect} user={user} />
            )}
          </UserConsumer>
          <UserConsumer>{(user) => <Statspace user={user} />}</UserConsumer>
        </Layout>
      );

    return (
      <Layout>
        <Sidemenu onSelect={this.handleSideSelect} user={this.props.user} />
        <Workspace area={this.state.area} camera={this.state.camera} />
      </Layout>
    );
  }
}

export default SurvApp;
