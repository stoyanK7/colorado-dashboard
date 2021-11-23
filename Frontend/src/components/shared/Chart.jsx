import '../../css/shared/Chart.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import InkUsageBarChart from '../charts/InkUsageBarChart';
import Loading from '../static/Loading';
import MediaCategoryUsageBarChart from '../charts/MediaCategoryUsageBarChart';
import SquareMeterPerPrintMode from '../charts/SquareMeterPerPrintMode';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { forwardRef } from 'react';
import useFetch from '../../hooks/useFetch';
import { useParams } from 'react-router-dom';

const Chart = forwardRef(({ setChartTitle, fullScreen, disableFullScreen }, ref) => {
  // Gets path from URL: i.e. https://xxxxx.com/InkInfo -> InkInfo
  const { chart } = useParams();

  // Retrieve chart data
  // Assumes that URL path is same to API endpoint
  const { data, isPending, error } = useFetch(`/${chart}`);

  // TODO: extract into another function? i.e ChartSwitch
  let component;
  switch (chart) {
    case 'MediaCategoryUsage': component = <MediaCategoryUsageBarChart data={data} index='date' />; setChartTitle('Media Categories Usage'); break;
    case 'InkUsage': component = <InkUsageBarChart data={data} index='date' />; setChartTitle('Ink Usage'); break;
    case 'SquareMeterPerPrintMode': component = <SquareMeterPerPrintMode data={data} index='date' />; setChartTitle('Printed Square Meters Per Print Mode'); break;
    // TODO: add the rest of the paths when the API supports them
    default: break;
  };

  return (
    <div ref={ref}
      className={`${fullScreen ? 'chart-full-screen' : ''} chart-wrapper-1`}>
      {isPending && <Loading />}
      {error && <h1>An error occured: {error}</h1>}
      <div className='chart-wrapper-2' >
        {fullScreen && <FontAwesomeIcon icon={faTimesCircle} className='fa-circle' onClick={disableFullScreen} />}
        {data && <div className='chart'>{component}</div>}
      </div>
    </div>
  );
});

export default Chart;
