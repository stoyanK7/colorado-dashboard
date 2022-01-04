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
import { useEffect, useState } from 'react';

import axios from 'axios';
import getRandomColor from '../../util/getRandomColor';

const CustomTooltip = ({ active, payload }) => {
  if (active && payload && payload.length) {
    return (
      <div className='colorado-custom-tooltip'>
        {payload[0].payload['Date'] && <p className='label'>{`Date: ${payload[0].payload['Date']}`}</p>}
        {payload[0].payload['Printer id'] && <p className='label'>{`Printer id: ${payload[0].payload['Printer id']}`}</p>}
        {payload.map(obj => {
          return <p className='label' style={{ color: obj.fill }}>{`Printed square meters: ${obj.value}`}</p>
        })}
      </div>
    );
  };

  return null;
};

const TopMachinesWithMostPrintVolumeBarChart = ({ data, aggregated }) => {
  // Convert data into readable format for Recharts
  let result = Object.values(data.reduce((r, o) => {
    let key = o.Date + '-' + o['Printer id'];
    r[key] ??= { Date: o.Date, 'Printer id': o['Printer id'] };
    r[key][o['Printer id']] = (r[key][o['Printer id']] ?? 0) + o['Printed square meters'];
    return r;
  }, {}));

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
  }, []);

  return (
    <ResponsiveContainer width='100%' height='100%'>
      <BarChart
        data={result}
        margin={{
          top: 35,
          right: 70,
          left: 70,
          bottom: 70
        }}>
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis
          dataKey='Printer id'
          textAnchor='start'
          angle={40}
          allowDuplicatedCategory={aggregated ? true : false}
          xAxisId={!aggregated ? 1 : 0} />
        {!aggregated && <XAxis dataKey='Date' xAxisId={0} />}
        <YAxis unit='SqM' type='number' />
        <Tooltip content={<CustomTooltip />} />
        <Legend verticalAlign='top' iconType='circle' />
        {chartDataKeys && chartDataKeys.map(key => {
          return <Bar
            dataKey={key}
            stackId='a'
            isAnimationActive={false}
            fill={getRandomColor()}
            key={key} />
        })}
      </BarChart>
    </ResponsiveContainer>
  );
};

export default TopMachinesWithMostPrintVolumeBarChart;
