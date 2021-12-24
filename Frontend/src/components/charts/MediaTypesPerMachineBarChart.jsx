import {
  Bar,
  BarChart,
  CartesianGrid,
  Legend,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis
} from 'recharts';

import React from 'react';

const MediaTypesMerMachineBarChart = ({ data }) => {
  return (
    <ResponsiveContainer width='100%' height='100%'>
      <BarChart
        width={700}
        height={300}
        data={data}
        margin={{
          top: 20,
          right: 50,
          left: 40,
          bottom: 50
        }}
      >
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis dataKey='Media type' textAnchor='start' angle={40} />
        <YAxis unit='SqM' type='number'
        // TODO: set this with maxvalue to fix overflowing labels
        // domain={[0, maxValue]} 
        />
        <Tooltip />
        <Legend verticalAlign='top' iconType='circle' />
        <Bar dataKey='Printed square meters' stackId='a' fill='#69283A' />
      </BarChart>
    </ResponsiveContainer>
  );
};

export default MediaTypesMerMachineBarChart;
