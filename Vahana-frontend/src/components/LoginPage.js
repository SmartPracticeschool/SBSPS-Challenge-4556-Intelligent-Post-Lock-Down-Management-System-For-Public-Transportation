import React, { useState } from "react";
import "./LoginPage.css";
import { Form, Input, Button, message } from "antd";
import { UserOutlined, LockOutlined } from "@ant-design/icons";
import { Avatar } from "antd";
//import {getUserByUsername} from '../mockDB/users';
import users from "../api/users";

const LoginPage = (props) => {
  const [btnLoading, setBtnLoading] = useState(false);

  const onFinish = (values) => {
    //uncomment when API ready
    setBtnLoading(true);
    users.get(`/${values.username}/${values.password}`).then((res) => {
      console.log(res);
      if (res.data.success === true){
        message.info(`Welcome ${res.data.details.name}`);
        props.onSubmit(res.data.details);
      } 
    });

    //   const foundUser = getUserByUsername(values.username);
    //   if(foundUser!==undefined && foundUser.password===values.password)
    //     props.onSubmit(foundUser);
  };

  return (
    <div style={{ marginTop: "5%" }}>
      <Form
        name="normal_login"
        className="login-form"
        onFinish={onFinish}
        style={{ margin: "auto" }}
      >
        <div
          className="avaholder"
          style={{ textAlign: "center", padding: "10%" }}
        >
          <Avatar size={64} icon={<UserOutlined />} />
        </div>
        <Form.Item
          name="username"
          rules={[
            {
              required: true,
              message: "Please input your Username!",
            },
          ]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="Username"
          />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            {
              required: true,
              message: "Please input your Password!",
            },
          ]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="Password"
          />
        </Form.Item>

        <Form.Item>
          <Button
            type="primary"
            htmlType="submit"
            className="login-form-button"
            loading={btnLoading}
          >
            Log in
          </Button>
          Or{" "}
          <Button type="link" onClick={props.onRegisterClick}>
            register now!
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default LoginPage;
