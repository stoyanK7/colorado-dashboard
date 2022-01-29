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
  "High speed",
  "Other",
  "Max speed",
  "Reliance",
  "Backlit",
  "Specialty",
  "High quality",
  "Production"
];

const SquareMeterPerPrintModeBarChart = ({ data, aggregated = true, index, legend = true }) => {
  const [chartDataKeys, setChartDataKeys] = useState();
  useEffect(() => {
    axios.get(`SquareMetersPerPrintMode/ChartDataKeys`)
      .then(res => res.data.dataKeys)
      .then(data => setChartDataKeys(data))
      .catch(err => setChartDataKeys(defaultChartDataKeys))
  }, []);

  return (
    <ResponsiveContainer width='100%' height='100%'>
      <BarChart
        data={convertData(data, 'Print mode')}
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
        {!aggregated && <XAxis dataKey='Printer id' xAxisId={0} />}
        <YAxis unit='SqM' type='number' />
        <Tooltip
          content={
            <CustomTooltip
              index={index}
              printer={(obj) => `${obj.dataKey}: ${obj.value}`} />} />
        {legend && <Legend verticalAlign='top' iconType='circle' />}
        {chartDataKeys && chartDataKeys.map(key => {
          return <Bar
            dataKey={key}
            stackId='a'
            isAnimationActive={false}
            fill={getRandomColor('palette1', chartDataKeys.indexOf(key))}
            key={key} />
        })}
      </BarChart>
    </ResponsiveContainer>
  );
};

export default SquareMeterPerPrintModeBarChart;
