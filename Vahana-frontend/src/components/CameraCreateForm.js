import React from "react";
import { Modal, Form, Input } from "antd";

const CameraCreateForm = ({ visible, onCreate, onCancel, loading }) => {
  const [form] = Form.useForm();
  return (
    <Modal
      visible={visible}
      title="Add cameras"
      okText="Add"
      cancelText="Cancel"
      onCancel={onCancel}
      confirmLoading={loading}
      onOk={() => {
        form
          .validateFields()
          .then((values) => {
            form.resetFields();
            onCreate(values);
          })
          .catch((info) => {
            console.log("Validate Failed:", info);
          });
      }}
    >
      <Form
        form={form}
        layout="vertical"
        name="form_in_modal"
        initialValues={{
          modifier: "public",
        }}
      >
        <Form.Item
          name="area"
          label="Area"
          rules={[
            {
              required: true,
              message: "Please input the area for the camera",
            },
          ]}
        >
          <Input type="textarea" placeholder="e.g.: Entrance"/>
        </Form.Item>
        <Form.Item
          name="camname"
          label="Camera Name"
          rules={[
            {
              required: true,
              message: "This camera name already exists for this area",
            },
          ]}
        >
          <Input type="textarea" placeholder="e.g.: Camera 9"/>
        </Form.Item>
        <Form.Item
          name="camip"
          label="Camera IP"
          rules={[
            {
              required: true,
              message: "This IP is invalid",
            },
          ]}
        >
          <Input type="textarea" placeholder="e.g.: 192.168.2.35:3000"/>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default CameraCreateForm;
