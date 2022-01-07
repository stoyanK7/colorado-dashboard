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
import getRandomColor from '../../util/getRandomColor';
import convertData from '../../util/convertData';

const CustomTooltip = ({ active, payload }) => {
  if (active && payload && payload.length) {
    return (
      <div className='colorado-custom-tooltip'>
        {payload[0].payload['Date'] && <p className='label'>{`Date: ${payload[0].payload['Date']}`}</p>}
        {payload[0].payload['Printer id'] && <p className='label'>{`Printer id: ${payload[0].payload['Printer id']}`}</p>}
        {payload.map(obj => {
          return <p className='label' style={{ color: obj.fill }} key={obj.dataKey}>{`${obj.dataKey}: ${obj.value}`}</p>
        })}
      </div>
    );
  };

  return null;
};

const MediaCategoryUsageBarChart = ({ data, aggregated }) => {
  const convertedData = convertData(data, 'Media category');

  const [chartDataKeys, setChartDataKeys] = useState();
  useEffect(() => {
    axios.get(`MediaCategoryUsage/ChartDataKeys`)
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
        data={convertedData}
        margin={{
          top: 35,
          right: 70,
          left: 70,
          bottom: 70
        }}>
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis
          dataKey='Date'
          textAnchor='start'
          angle={40}
          allowDuplicatedCategory={aggregated ? true : false}
          xAxisId={!aggregated ? 1 : 0} />
        {!aggregated && <XAxis dataKey='Printer id' xAxisId={0} />}
        <YAxis unit='SqM' type='number' />
        <Tooltip content={<CustomTooltip />} />
        <Legend verticalAlign='top' iconType='circle' />
        {chartDataKeys && chartDataKeys.map(key => {
          return <Bar
            dataKey={key}
            stackId='a'
            isAnimationActive={false}
            fill={getRandomColor('palette3', chartDataKeys.indexOf(key))}
            key={key} />
        })}
      </BarChart>
    </ResponsiveContainer>
  );
};

export default MediaCategoryUsageBarChart;
