import '../../css/shared/Chart.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Loading from '../static/Loading';
import chartSwitch from '../../util/chartSwitch';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { forwardRef } from 'react';
import useFetch from '../../hooks/useFetch';

const Chart = forwardRef(({ chartPath, fullScreen, disableFullScreen }, ref) => {  
  // Retrieve chart data
  // Assumes that URL path is same as API endpoint
  const { data, isPending, error } = useFetch(`/${chartPath}`);

  return (
    <div ref={ref}
      className={`${fullScreen ? 'chart-full-screen' : ''} chart-wrapper-1`}>
      {isPending && <Loading />}
      {error && <h1>An error occured: {error}</h1>}
      <div className='chart-wrapper-2' >
        {fullScreen && <FontAwesomeIcon icon={faTimesCircle} className='fa-circle' onClick={disableFullScreen} />}
        {data && <div className='chart'>{chartSwitch(chartPath, data)}</div>}
      </div>
    </div>
  );
});

export default Chart;
