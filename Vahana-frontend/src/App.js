import React, { Component } from "react";
import "./App.less";
import lodash from "lodash";
import { Layout } from "antd";
import { SocketProvider } from "./contexts/socketContext";

//my custom components
import {
  SurvApp,
  ControlApp,
  VehicleApp,
  TitleBar,
  LoginHandler,
} from "./components";

//uncomment when socket ready
import io from "socket.io-client";
import { UserProvider } from "./contexts/userContext";

//uncomment when socket rdy
const ENDPOINT =  process.env.REACT_APP_BASE_URL; //change this to server domain name


class App extends Component {
  
  state = {
    tab: "surveillance",
    signedIn: false,
  };

  user = {
    username: null,
    password: null,
    station: {
      _id: null,
      name: null,
      type: null,
    },
  };

  handleTopSelect = (selection) => {
    this.setState(
      {
        tab: lodash.toLower(selection),
      },
      () => console.log(this.state)
    );
  };

  handleLogin = (values) => {
    this.user = values;
    console.log(this.user);
    this.setState(
      {
        signedIn: true,
      },
      () => console.log(this.state)
    );
  };
  render() {
    console.log("env variable for base url: ",process.env.REACT_APP_BASE_URL);
    if (this.state.signedIn === false) {
      return (
        <div className="App">
          <LoginHandler onSubmit={this.handleLogin} />
        </div>
      );
    }


    this.socket = io.connect(ENDPOINT, {rememberTransport: false});
    if (this.state.tab === "surveillance")
      return (
        <div className="App">
          <Layout>
            <TitleBar
              title="Vahana"
              onSelect={this.handleTopSelect}
              user={this.user}
            />
            <SocketProvider value={this.socket}>
              <UserProvider value={this.user}>
                <SurvApp user={this.user} />
              </UserProvider>
            </SocketProvider>
          </Layout>
        </div>
      );
    if (this.state.tab === "control panel")
      return (
        <div className="App">
          <Layout style={{ minHeight: 700 }}>
            <TitleBar
              title="Vahana"
              onSelect={this.handleTopSelect}
              user={this.user}
            />

            <ControlApp name="Control Panel" />
          </Layout>
        </div>
      );

    return (
      <div className="App">
        <Layout>
          <TitleBar
            title="Vahana"
            onSelect={this.handleTopSelect}
            user={this.user}
          />
          <SocketProvider value={this.socket}>
            <UserProvider value={this.user}>
              <VehicleApp name="Vehicles" />
            </UserProvider>
          </SocketProvider>
        </Layout>
      </div>
    );
  }
}
export default App;
