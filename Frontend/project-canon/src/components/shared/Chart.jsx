import '../../css/Chart.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Loading from '../static/Loading';
import MediaCategoryBarChart from '../charts/MediaCategoryBarChart';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { forwardRef } from 'react';
import useFetch from '../../hooks/useFetch';
import { useParams } from 'react-router-dom';

const Chart = forwardRef(({ setChartTitle, isFullScreen, toggleFullScreen }, ref) => {
  const { chart } = useParams();

  const { data, isPending, error } = useFetch(`/${chart}`);

  let component;
  switch (chart) {
    case 'PrintSquareMeterPerMediaType': component = <MediaCategoryBarChart data={data} index='date' />; setChartTitle('Square meter per media type'); break;
    // TODO: add the rest of the paths when the API has them
    default: break;
  };

  return (
    <div ref={ref}
      className={`${isFullScreen ? 'chart-full-screen' : ''} chart-wrapper-1`}>
      {isPending && <Loading />}
      {error && <h1>An error occured: {error}</h1>}
      <div className='chart-wrapper-2' >
        {isFullScreen && <FontAwesomeIcon icon={faTimesCircle} className='fa-circle' onClick={toggleFullScreen}/>}
        {data && <div className='chart'>{component}</div>}
      </div>
    </div>
  );
});

export default Chart;
