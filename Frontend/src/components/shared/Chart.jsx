import '../../css/shared/Chart.css';

import { forwardRef, useEffect, useImperativeHandle, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Loading from '../static/Loading';
import chartSwitch from '../../util/chartSwitch';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import useFetch from '../../hooks/useFetch';

const Chart = forwardRef(({ chartPath, fullScreen, disableFullScreen }, ref) => {
  const [link, setLink] = useState();
  const [requestBody, setRequestBody] = useState();
  // Retrieve chart data
  // Assumes that URL path is same as API endpoint
  const { data, isPending, error } = useFetch(link || `/${chartPath}`, requestBody);

  // Rerender whole component when the link or request body change
  useEffect(() => { }, [link, requestBody]);

  // Functions to be called from the parent View component
  useImperativeHandle(ref, () => ({
    makeRequest(providedLink, providedRequestBody) {
      console.log("make request invoked");
      setRequestBody(providedRequestBody);
      setLink(providedLink);
    }
  }));

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
