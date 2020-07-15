import React from "react";
import { Row, Col, Card, Statistic} from "antd";
import GraphTile from "./GraphTile.jsx";

const InsightPage = () => {
  return (
    <Row gutter={[16,16]}>
      <Col span={12}>
        <Card hoverable>
          <Row gutter={[16, 16]}>
            <Col>
              <Card hoverable>
                <Statistic
                  title="Average daily fuel consumption"
                  value={100 + " litres"}
                />
              </Card>
            </Col>
            <Col>
              <Card hoverable>
                <Statistic title="Buses on road" value={20} />
              </Card>
            </Col>
            <Col>
              <Card hoverable>
                <Statistic title="Buses off road" value={5} />
              </Card>
            </Col>
           
          </Row>
          <Row gutter={[16, 16]}>
            <Col>
              <GraphTile
                title="Occupancy"
                name="occupichart"
                height={200}
                width={200}
              />
            </Col>
            <Col>
              <GraphTile
                title="Bus Popularity"
                name="popvsbus"
                height={200}
                width={350}
              />
            </Col>
          </Row>
        </Card>
      </Col>

      <Col span={12}>
        <Card hoverable>
          <Row gutter={[16, 16]}>
            <Col>
              <Card hoverable>
                <Statistic
                  title="Average daily fuel consumption"
                  value={100 + " litres"}
                />
              </Card>
            </Col>
            <Col>
              <Card hoverable>
                <Statistic title="Buses on road" value={20} />
              </Card>
            </Col>
            <Col>
              <Card hoverable>
                <Statistic title="Buses off road" value={5} />
              </Card>
            </Col>
            
          </Row>
          <Row gutter={[16, 16]}>
            <Col>
              <GraphTile
                title="Occupancy"
                name="gr1"
                height={200}
                width={200}
              />
            </Col>
            <Col>
              <GraphTile
                title="Bus Popularity"
                name="gr2"
                height={200}
                width={350}
              />
            </Col>
          </Row>
        </Card>
      </Col>
    </Row>
  );
};

export default InsightPage;
