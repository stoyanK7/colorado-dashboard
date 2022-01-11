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
import getRandomColor from '../../util/getRandomColor';

const MediaTypesMerMachineBarChart = ({ data, aggregated = true, index, legend = true }) => {
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
        {legend && <Legend verticalAlign='top' iconType='circle' />}
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
