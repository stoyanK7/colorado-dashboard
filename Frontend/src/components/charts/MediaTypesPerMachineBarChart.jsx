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

import React, { useState, useEffect } from 'react';
import axios from 'axios';

const MediaTypesMerMachineBarChart = ({ data, aggregated }) => {

  let result = Object.values(data.reduce((r, o) => {
    let key = o.Date + '-' + o['Printer id'];
    r[key] ??= { Date: o.Date, 'Printer id': o['Printer id'] };
    r[key][o['Media type']] = (r[key][o['Media type']] ?? 0) + o['Printed square meters'];
    return r;
  }, {}));

  const [chartDataKeys, setChartDataKeys] = useState();
  useEffect(() => {
    axios.get(`MediaTypesPerMachine/ChartDataKeys`)
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
        <XAxis dataKey='Media type' textAnchor='start' angle={40} />
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

export default MediaTypesMerMachineBarChart;
