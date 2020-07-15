import { ResponsiveBar } from "@nivo/bar";
import React from "react";

const theme = {
  axis: {
    fontSize: "10px",
    legend: {
      text: {
        fill: "black",
        fontSize: 13,
        fontWeight: "bold"
      },
    },
  },
};

const axisBottom = {
  tickSize: 5,
  tickPadding: 5,
  tickRotation: 0,
  legend: "Day",
  legendPosition: "middle",
  legendOffset: 32,
};

const axisLeft = {
  tickSize: 5,
  tickPadding: 5,
  tickRotation: 0,
  legend: "Count",
  legendPosition: "middle",
  legendOffset: -40,
};

const legends = [
  {
    dataFrom: "keys",
    anchor: "bottom-right",
    direction: "column",
    justify: false,
    translateX: 120,
    translateY: 0,
    itemsSpacing: 2,
    itemWidth: 100,
    itemHeight: 20,
    itemDirection: "left-to-right",
    itemOpacity: 0.85,
    symbolSize: 20,
    effects: [
      {
        on: "hover",
        style: {
          itemOpacity: 1,
        },
      },
    ],
  },
];

const NivoBar = (props) => (
  <ResponsiveBar
    data={props.data}
    keys={props.keys}
    indexBy="day"
    theme={theme}
    margin={{ top: 50, right: 150, bottom: 50, left: 60 }}
    padding={0.3}
    groupMode="grouped"
    colors={{ scheme: "nivo" }}
    borderColor={{ from: "color", modifiers: [["darker", 1.6]] }}
    axisTop={null}
    axisRight={null}
    axisBottom={axisBottom}
    axisLeft={axisLeft}
    labelSkipWidth={12}
    labelSkipHeight={12}
    labelTextColor={{ from: "color", modifiers: [["darker", 1.6]] }}
    legends={legends}
    animate={true}
    motionStiffness={90}
    motionDamping={15}
  />
);

export default NivoBar;
