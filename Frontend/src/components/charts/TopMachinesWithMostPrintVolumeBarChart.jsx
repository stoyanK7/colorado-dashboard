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

const TopMachinesWithMostPrintVolumeBarChart = ({ data, aggregated }) => {
  let result = Object.values(data.reduce((r, o) => {
    let key = o.Date + '-' + o['Printer id'];
    r[key] ??= { Date: o.Date, 'Printer id': o['Printer id'] };
    r[key][o['Printer id']] = (r[key][o['Printer id']] ?? 0) + o['Printed square meters'];
    return r;
  }, {}));

  console.log(result);

  const [chartDataKeys, setChartDataKeys] = useState();
  useEffect(() => {
    axios.get(`TopMachinesWithMostPrintVolume/ChartDataKeys`)
      .then(res => res.data.dataKeys)
      .then(data => {
        setChartDataKeys(data);
      })
      .catch(err => {
        // TODO: introduce error handling logic
      })
  }, [])

  return (
    <ResponsiveContainer width='100%' height='100%'>
      <BarChart
        data={result}
        margin={{
          top: 15,
          right: 50,
          left: 30,
          bottom: 50
        }}
      >
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis
          dataKey='Printer id'
          textAnchor='start'
          angle={40}
          xAxisId={!aggregated ? 1 : 0}
          // TODO: this hides the tooltip for some reason
          // allowDuplicatedCategory={false} 
          />
        {!aggregated && <XAxis dataKey="Date" xAxisId={0} />}
        <YAxis unit='SqM' type='number'
        // TODO: set this with maxvalue to fix overflowing labels
        // domain={[0, maxValue]} 
        />
        <Tooltip />
        <Legend verticalAlign='top' iconType='circle' />
        {chartDataKeys && chartDataKeys.map(key => {
          console.log(key)
          return <Bar dataKey={key} stackId='a' fill='#49BFF9' key={key}/>
        })}
      </BarChart>
    </ResponsiveContainer>
  );
};

export default TopMachinesWithMostPrintVolumeBarChart;
