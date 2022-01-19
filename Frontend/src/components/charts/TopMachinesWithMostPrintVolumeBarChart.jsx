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

import CustomTooltip from '../shared/CustomTooltip';
import axios from 'axios';
import convertData from '../../util/convertData';
import getRandomColor from '../../util/getRandomColor';

const defaultChartDataKeys = [
  "708",
  "709",
  "710",
  "707",
  "701",
  "705",
  "703",
  "702",
  "706",
  "700",
  "704"
];

const TopMachinesWithMostPrintVolumeBarChart = ({ data, aggregated = true, index, legend = true }) => {
  const [chartDataKeys, setChartDataKeys] = useState();
  useEffect(() => {
    axios.get(`TopMachinesWithMostPrintVolume/ChartDataKeys`)
      .then(res => res.data.dataKeys)
      .then(data => setChartDataKeys(data))
      .catch(err => setChartDataKeys(defaultChartDataKeys))
  }, []);

  return (
    <ResponsiveContainer width='100%' height='100%'>
      <BarChart
        data={convertData(data, 'Printer id')}
        margin={legend ? {
          top: 35,
          right: 70,
          left: 70,
          bottom: 70
        } : {
          top: 10,
          right: 0,
          left: 0,
          bottom: 0
        }}>
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis
          dataKey={index}
          textAnchor='start'
          angle={40}
          allowDuplicatedCategory={aggregated ? true : false}
          xAxisId={!aggregated ? 1 : 0} />
        {!aggregated && <XAxis dataKey='Date' xAxisId={0} />}
        <YAxis unit='SqM' type='number' />
        <Tooltip
          content={
            <CustomTooltip
              index={'Date'}
              printer={(obj) => { return `Printed square meters: ${obj.value}` }} />} />
        {legend && <Legend verticalAlign='top' iconType='circle' />}
        {chartDataKeys && chartDataKeys.map(key => {
          return <Bar
            dataKey={key}
            stackId='a'
            isAnimationActive={false}
            fill={getRandomColor('palette9', chartDataKeys.indexOf(key))}
            key={key} />
        })}
      </BarChart>
    </ResponsiveContainer>
  );
};

export default TopMachinesWithMostPrintVolumeBarChart;
