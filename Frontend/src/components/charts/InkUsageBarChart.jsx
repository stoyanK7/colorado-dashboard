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

const InkUsageBarChart = ({ data, aggregated }) => {
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
          dataKey='Date'
          textAnchor='start'
          angle={40}
          allowDuplicatedCategory={aggregated ? true : false}
          xAxisId={!aggregated ? 1 : 0} />
        {!aggregated && <XAxis dataKey='Printer id' xAxisId={0} />}
        <YAxis unit='L' type='number' />
        <Tooltip
          content={
            <CustomTooltip
              field='Date'
              printer={(obj) => `${obj.dataKey}: ${obj.value}`} />} />
        <Legend verticalAlign='top' iconType='circle' />
        <Bar dataKey='Black' isAnimationActive={false} stackId='a' fill='#333333' />
        <Bar dataKey='Cyan' isAnimationActive={false} stackId='a' fill='#00FFFF' />
        <Bar dataKey='Magenta' isAnimationActive={false} stackId='a' fill='#FF00FF' />
        <Bar dataKey='Yellow' isAnimationActive={false} stackId='a' fill='#DDDD00' />
      </BarChart>
    </ResponsiveContainer>
  );
};

export default InkUsageBarChart;
