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
import React, { useEffect } from 'react';

const InkUsageBarChart = ({ data, aggregated }) => {
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
        <XAxis
          dataKey='Date'
          textAnchor='start'
          angle={40}
          xAxisId={!aggregated ? 1 : 0}
          // TODO: this hides the tooltip for some reason
          // allowDuplicatedCategory={false} 
          />
        {!aggregated && <XAxis dataKey="Printer id" xAxisId={0} />}

        <YAxis unit='L' type='number'
        // TODO: set this with maxvalue to fix overflowing labels
        // domain={[0, maxValue]} 
        />
        <Tooltip />
        <Legend verticalAlign='top' iconType='circle' />
        <Bar dataKey='Black' isAnimationActive={false} stackId='a' fill='#333333' />
        <Bar dataKey='Cyan' isAnimationActive={false} stackId='a' fill='#00FFFF' />
        <Bar dataKey='Magenta' isAnimationActive={false} stackId='a' fill='#FF00FF' />
        <Bar dataKey='Yellow' isAnimationActive={false} stackId='a' fill='#DDDD00' />
      </BarChart>
    </ResponsiveContainer>
  );
};

export default InkUsageBarChart;
