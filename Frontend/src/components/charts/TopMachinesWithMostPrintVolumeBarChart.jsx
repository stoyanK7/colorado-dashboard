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

const TopMachinesWithMostPrintVolumeBarChart = ({ data }) => {
  return (
    <ResponsiveContainer width='100%' height='100%'>
      <BarChart
        data={data}
        margin={{
          top: 15,
          right: 50,
          left: 30,
          bottom: 50
        }}
      >
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis dataKey='Printer id' textAnchor='start' angle={40} />
        <YAxis unit='SqM' type='number'
        // TODO: set this with maxvalue to fix overflowing labels
        // domain={[0, maxValue]} 
        />
        <Tooltip />
        <Legend verticalAlign='top' iconType='circle' />
        <Bar dataKey='Printed square meters' stackId='a' fill='#333333' />
      </BarChart>
    </ResponsiveContainer>
  );
};

export default TopMachinesWithMostPrintVolumeBarChart;
