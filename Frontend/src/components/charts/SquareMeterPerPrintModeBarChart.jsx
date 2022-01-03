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
import React, { useEffect, useState } from 'react';

import axios from 'axios';

const SquareMeterPerPrintModeBarChart = ({ data, aggregated }) => {
  let result = Object.values(data.reduce((r, o) => {
    let key = o.Date + '-' + o['Printer id'];
    r[key] ??= { Date: o.Date, 'Printer id': o['Printer id'] };
    r[key][o['Print mode']] = (r[key][o['Print mode']] ?? 0) + o['Printed square meters'];
    return r;
  }, {}));

  const [chartDataKeys, setChartDataKeys] = useState();
  useEffect(() => {
    axios.get(`SquareMetersPerPrintMode/ChartDataKeys`)
      .then(res => res.data.dataKeys)
      .then(data => {
        setChartDataKeys(data);
      })
      .catch(err => {
        // TODO: introduce error handling logic
      })
  }, []);
  
  return (
    <ResponsiveContainer width='100%' height='100%'>
      <BarChart
        width={700}
        height={300}
        data={result}
        margin={{
          top: 20,
          right: 50,
          left: 40,
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
        <YAxis unit='SqM' type='number'
        // TODO: set this with maxvalue to fix overflowing labels
        // domain={[0, maxValue]} 
        />
        <Tooltip />
        <Legend verticalAlign='top' iconType='circle' />
        {chartDataKeys && chartDataKeys.map(key => {
          console.log(key)
          return <Bar dataKey={key} stackId='a' fill='#49BFF9' />
        })}
      </BarChart>
    </ResponsiveContainer>
  );
};

export default SquareMeterPerPrintModeBarChart;
