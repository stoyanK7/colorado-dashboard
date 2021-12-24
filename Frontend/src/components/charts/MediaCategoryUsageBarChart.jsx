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

const MediaCategoryUsageBarChart = ({ data }) => {
  return (
    <ResponsiveContainer width='100%' height='100%'>

      <BarChart
        width={700}
        height={300}
        data={data}
        margin={{
          top: 20,
          right: 30,
          left: 20,
          bottom: 5
        }}
      >
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis dataKey='date' />
        <YAxis unit='SqM' />
        <Tooltip />
        <Legend />
        <Bar dataKey='Film' stackId='a' fill='#49BFF9' />
        <Bar dataKey='Light paper < 120gsm' stackId='a' fill='#C5C714' />
        <Bar dataKey='Heavy paper > 200gsm' stackId='a' fill='#A843B1' />
        <Bar dataKey='Light banner < 400gsm' stackId='a' fill='#6735E1' />
        <Bar dataKey='Monomeric vinyl' stackId='a' fill='#BFFA7F' />
        <Bar dataKey='Canvas' stackId='a' fill='#1D3317' />
        <Bar dataKey='Polymeric & cast vinyl' stackId='a' fill='#0C5B54' />
        <Bar dataKey='Heavy banner > 400gsm' stackId='a' fill='#C8D0C8' />
        <Bar dataKey='Paper' stackId='a' fill='#3FBCB9' />
        <Bar dataKey='Thick film > 200um' stackId='a' fill='#F4236B' />
      </BarChart>
    </ResponsiveContainer>
  );
};

export default MediaCategoryUsageBarChart;
