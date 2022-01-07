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
import convertData from '../../util/convertData';
import CustomTooltip from '../shared/CustomTooltip';

const TopMachinesWithMostPrintVolumeBarChart = ({ data, aggregated, index }) => {
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
        data={convertData(data, 'Printer id')}
        margin={{
          top: 35,
          right: 70,
          left: 70,
          bottom: 70
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
        <Legend verticalAlign='top' iconType='circle' />
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
