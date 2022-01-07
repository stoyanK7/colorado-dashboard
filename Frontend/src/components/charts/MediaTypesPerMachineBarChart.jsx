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
import CustomTooltip from '../shared/CustomTooltip';

const MediaTypesMerMachineBarChart = ({ data, aggregated, index }) => {
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
          dataKey={index}
          textAnchor='start'
          angle={40}
          xAxisId={!aggregated ? 1 : 0}
          allowDuplicatedCategory={aggregated ? true : false}
          interval={0} />
        {!aggregated && <XAxis dataKey='Printer id' xAxisId={0} />}
        <YAxis unit='SqM' type='number' />
        <Tooltip
          content={
            <CustomTooltip
              index={index}
              printer={(obj) => { return `Printed square meters: ${obj.value}` }} />} />
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
