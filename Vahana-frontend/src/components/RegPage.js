import React, { useState } from "react";
import "./RegPage.css";
import { Form, Input, Button, Select, Avatar, message } from "antd";
import {
  UserOutlined,
  LockOutlined,
  EnvironmentOutlined,
} from "@ant-design/icons";
import users from "../api/users.js";

const RegPage = (props) => {

  const [requestOnProcess, setRequestOnProcess] = useState(false);

  const onFinish = (values) => {
    //chk if license already used
    const newuser = {
      name: values.name,
      username: values.username,
      password: values.password,
      station: {
        _id:
          values.stationname.split(" ").join("").toLowerCase() +
          "_" +
          values.vehicletype.toLowerCase(),
        name: values.stationname,
        type: values.vehicletype,
      },
    };

    const showErrorMsg = (text) => {
      message.error(text);
    };

    const showSuccessMsg = (text) => {
      message.success(text);
    };
    
    console.log("Trying to register...");
    setRequestOnProcess(true);
    users.post(`/adduser/${values.license}`, { newuser }).then((res) => {
      if (res.data.success) {
        showSuccessMsg("Registration successful! Login to access your dashboard.");
        props.onSubmit();
      } 
      else{
        setRequestOnProcess(true);
        showErrorMsg(res.data.message);
        console.log(res.data.message);
      } 
    });
  };

  return (
    <div style={{ marginTop: "5%" }}>
      <Form
        name="normal_reg"
        className="reg-form"
        onFinish={onFinish}
        style={{ margin: "auto" }}
      >
        <div
          className="avaholder"
          style={{ textAlign: "center", padding: "10%" }}
        >
          <Avatar size={32} icon={<UserOutlined />} />
        </div>
        <Form.Item
          name="license"
          rules={[
            {
              required: true,
              message: "Please input the provided Admin License",
            },
          ]}
        >
          <Input placeholder="License" />
        </Form.Item>
        <Form.Item
          name="name"
          rules={[
            {
              required: true,
              message: "Enter name",
            },
          ]}
        >
          <Input placeholder="Name" />
        </Form.Item>
        <Form.Item
          name="username"
          rules={[
            {
              required: true,
              message: "Set your required username!",
            },
          ]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="Set username"
          />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            {
              required: true,
              message: "Set your password!",
            },
          ]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="Set password"
          />
        </Form.Item>

        <Form.Item
          name="stationname"
          rules={[
            {
              required: true,
              message: "Station name missing",
            },
          ]}
        >
          <Input
            prefix={<EnvironmentOutlined className="site-form-item-icon" />}
            placeholder="Transport facility name"
          />
        </Form.Item>

        <Form.Item
          name="vehicletype"
          rules={[
            {
              required: true,
              message: "Vehicle type missing",
            },
          ]}
        >
          <Select placeholder="Vehicle Type">
            <Select.Option value="bus">Bus</Select.Option>
            <Select.Option value="flight">Flight</Select.Option>
            <Select.Option value="train">Train</Select.Option>
          </Select>
        </Form.Item>

        <Form.Item>
          <Button
            type="primary"
            htmlType="submit"
            className="login-form-button"
            loading={requestOnProcess}
          >
            Register
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default RegPage;
