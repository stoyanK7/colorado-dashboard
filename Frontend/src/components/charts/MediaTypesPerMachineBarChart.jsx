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

import getRandomColor from '../../util/getRandomColor';

const CustomTooltip = ({ active, payload }) => {
  if (active && payload && payload.length) {
    return (
      <div className='colorado-custom-tooltip'>
        {payload[0].payload['Media type'] && <p className='label'>{`Media type: ${payload[0].payload['Media type']}`}</p>}
        {payload[0].payload['Printer id'] && <p className='label'>{`Printer id: ${payload[0].payload['Printer id']}`}</p>}
        {payload.map(obj => {
          return <p className='label' style={{ color: obj.fill }}>{`Printed square meters: ${obj.value}`}</p>
        })}
      </div>
    );
  };

  return null;
};

const MediaTypesMerMachineBarChart = ({ data, aggregated }) => {
  return (
    <ResponsiveContainer width='100%' height='100%'>
      <BarChart
        data={data}
        margin={{
          top: 35,
          right: 70,
          left: 70,
          bottom: 70
        }}>
        <CartesianGrid strokeDasharray='3 3' />
        <XAxis
          dataKey='Media type'
          textAnchor='start'
          angle={40}
          xAxisId={!aggregated ? 1 : 0}
          allowDuplicatedCategory={aggregated ? true : false}
          interval={0} />
        {!aggregated && <XAxis dataKey='Printer id' xAxisId={0} />}
        <YAxis unit='SqM' type='number' />
        <Tooltip content={<CustomTooltip />} />
        <Legend verticalAlign='top' iconType='circle' />
        <Bar
          dataKey='Printed square meters'
          isAnimationActive={false}
          stackId='a'
          fill={getRandomColor('palette5', 2)} />
      </BarChart>
    </ResponsiveContainer>
  );
};

export default MediaTypesMerMachineBarChart;
