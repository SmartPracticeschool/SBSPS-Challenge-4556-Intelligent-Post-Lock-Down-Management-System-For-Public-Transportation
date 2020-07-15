import { ResponsiveLine } from "@nivo/line";
import React from "react";

// make sure parent container have a defined height when using
// responsive component, otherwise height will be 0 and
// no chart will be rendered.
// website examples showcase many properties,
// you'll often use just a few of them.

const theme = {
  axis: {
    fontSize: "14px",
    legend: {
      text: {
        fill: "black",
        fontSize: 15,
      },
    },
  },
};

const axisLeft = {
  orient: "left",
  tickSize: 5,
  tickPadding: 5,
  tickRotation: 0,
  legend: "Count",
  legendOffset: -50,
  legendPosition: "middle",
};

const axisBottom = {
  orient: "bottom",
  tickSize: 5,
  tickPadding: 5,
  tickRotation: 0,
  legend: "Days of the Week",
  legendOffset: 40,
  legendPosition: "middle",
};

const NivoLine = (props) => {
  return (
    <ResponsiveLine
      data={props.data}
      margin={{ top: 20, right: 150, bottom: 50, left: 60 }}
      xScale={{ type: "point" }}
      yScale={{
        type: "linear",
        min: "auto",
        max: "auto",
        stacked: true,
        reverse: false,
      }}
      theme={theme}
      axisTop={null}
      axisRight={null}
      axisBottom={axisBottom}
      axisLeft={axisLeft}
      colors={{ scheme: "nivo" }}
      pointSize={10}
      pointColor={{ theme: "background" }}
      pointBorderWidth={2}
      pointBorderColor={{ from: "serieColor" }}
      pointLabel="y"
      pointLabelYOffset={-12}
      useMesh={true}
      enableGridX={false}
      legends={[
        {
          anchor: "bottom-right",
          direction: "column",
          justify: false,
          translateX: 100,
          translateY: 0,
          itemsSpacing: 0,
          itemDirection: "left-to-right",
          itemWidth: 80,
          itemHeight: 20,
          itemOpacity: 0.75,
          symbolSize: 12,
          symbolShape: "circle",
          symbolBorderColor: "rgba(0, 0, 0, .5)",
          effects: [
            {
              on: "hover",
              style: {
                itemBackground: "rgba(0, 0, 0, .03)",
                itemOpacity: 1,
              },
            },
          ],
        },
      ]}
    />
  );
};

export default NivoLine;

// legends={[
//     {
//         anchor: 'bottom-right',
//         direction: 'column',
//         justify: false,
//         translateX: 100,
//         translateY: 0,
//         itemsSpacing: 0,
//         itemDirection: 'left-to-right',
//         itemWidth: 80,
//         itemHeight: 20,
//         itemOpacity: 0.75,
//         symbolSize: 12,
//         symbolShape: 'circle',
//         symbolBorderColor: 'rgba(0, 0, 0, .5)',
//         effects: [
//             {
//                 on: 'hover',
//                 style: {
//                     itemBackground: 'rgba(0, 0, 0, .03)',
//                     itemOpacity: 1
//                 }
//             }
//         ]
//     }
// ]}
