import '../../css/shared/Chart.css';

import { faRedoAlt, faTimesCircle } from '@fortawesome/free-solid-svg-icons';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Loading from '../static/Loading';
import React from 'react';
import chartSwitch from '../../util/chartSwitch';
import { forwardRef } from 'react';
import useFetch from '../../hooks/useFetch';

// Represents the chart that you see below the filters
const Chart = forwardRef(({ link, aggregated,requestBody, chartPath, fullScreen, disableFullScreen, rotateFullScreen }, ref) => {
  // Retrieve chart data
  // Assumes that URL path is same as API endpoint
  const { data, isPending, error } = useFetch(link || `/${chartPath}`, requestBody);

  return (
    <div ref={ref}
      className={`${fullScreen ? 'chart-full-screen' : ''} chart-wrapper-1`}>
      {isPending && <Loading />}
      {error && <h1>An error occured: {error}</h1>}
      <div className='chart-wrapper-2' >
        {fullScreen && <FontAwesomeIcon icon={faTimesCircle} className='fa-circle' onClick={disableFullScreen} />}
        {fullScreen && <FontAwesomeIcon icon={faRedoAlt} className='fa-redo' onClick={rotateFullScreen}/>}
        {data && <div className='chart'>{chartSwitch(chartPath, data, aggregated)}</div>}
      </div>
    </div>
  );
});

export default Chart;
