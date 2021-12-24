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

const SquareMeterPerPrintModeBarChart = ({ data }) => {
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
        <XAxis dataKey='Date' textAnchor='start' angle={40} />
        <YAxis unit='SqM' type='number'
        // TODO: set this with maxvalue to fix overflowing labels
        // domain={[0, maxValue]} 
        />
        <Tooltip />
        <Legend verticalAlign='top' iconType='circle' />
        <Bar dataKey='Max speed' stackId='a' fill='#A6DA4E' />
        <Bar dataKey='High speed' stackId='a' fill='#9C2BCB' />
        <Bar dataKey='Production' stackId='a' fill='#35EA33' />
        <Bar dataKey='High quality' stackId='a' fill='#C87A9C' />
        <Bar dataKey='Specialty' stackId='a' fill='#1013A6' />
        <Bar dataKey='Backlit' stackId='a' fill='#F63B45' />
        <Bar dataKey='Reliance' stackId='a' fill='#1D3317' />
        <Bar dataKey='Other' stackId='a' fill='#69283A' />
      </BarChart>
    </ResponsiveContainer>
  );
};

export default SquareMeterPerPrintModeBarChart;
