import React, { Component } from "react";

import { Layout } from "antd";

class ControlApp extends Component {
  render() {
    return (
      <Layout>
        <h1>{this.props.name}</h1>
      </Layout>
    );
  }
}

export default ControlApp;
