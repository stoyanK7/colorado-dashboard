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

import CustomTooltip from '../shared/CustomTooltip';

const InkUsageBarChart = ({ data, aggregated = true, index, legend = true }) => {
  return (
    <ResponsiveContainer width='100%' height='100%'>
      <BarChart
        data={data}
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
        <YAxis unit='L' type='number' />
        <Tooltip
          content={
            <CustomTooltip
              index={index}
              printer={(obj) => `${obj.dataKey}: ${obj.value}`} />} />
        {legend && <Legend verticalAlign='top' iconType='circle' />}
        <Bar dataKey='Black' isAnimationActive={false} stackId='a' fill='#333333' />
        <Bar dataKey='Cyan' isAnimationActive={false} stackId='a' fill='#00FFFF' />
        <Bar dataKey='Magenta' isAnimationActive={false} stackId='a' fill='#FF00FF' />
        <Bar dataKey='Yellow' isAnimationActive={false} stackId='a' fill='#DDDD00' />
      </BarChart>
    </ResponsiveContainer>
  );
};

export default InkUsageBarChart;
