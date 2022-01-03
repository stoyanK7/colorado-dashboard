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

const CustomTooltip = ({ active, payload, label }) => {
  console.log(payload);
  if (active && payload && payload.length) {
    return (
      <div className="custom-tooltip">
        <p className="label">{`${payload[0].payload['Media type']}`}</p>
        <p className="label">{`${label} : ${payload[0].value}`}</p>
      </div>
    );
  }

  return null;
};

const MediaTypesMerMachineBarChart = ({ data, aggregated }) => {

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
        data={data}
        margin={{
          top: 20,
          right: 50,
          left: 40,
          bottom: 100
        }}
      >
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis
          dataKey='Media type'
          textAnchor='start'
          angle={40}
          xAxisId={!aggregated ? 1 : 0}
          allowDuplicatedCategory={false}
          interval={0}
        />
        {!aggregated && <XAxis dataKey="Printer id" xAxisId={0} />}
        <YAxis unit='SqM' type='number'
        // TODO: set this with maxvalue to fix overflowing labels
        // domain={[0, maxValue]} 
        />
        <Tooltip content={<CustomTooltip />} />
        <Legend verticalAlign='top' iconType='circle' />
        <Bar dataKey='Printed square meters' stackId='a' fill='#49BFF9' />

      </BarChart>
    </ResponsiveContainer>
  );
};

export default MediaTypesMerMachineBarChart;
