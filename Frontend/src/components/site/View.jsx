import '../../css/site/View.css';

import { disableFullScreen, enableFullScreen } from '../../util/fullScreen';

import Chart from '../shared/Chart';
import Filters from '../shared/Filters';
import Header from '../static/Header';
import chartTitleSwitch from '../../util/chartTitleSwitch';
import { useParams } from 'react-router-dom';
import { useRef } from 'react';
import useToggle from '../../hooks/useToggle';

const View = () => {
  // Gets path from URL: i.e. https://xxxxx.com/InkInfo -> InkInfo
  const { chartPath } = useParams();
  const chartTitle = chartTitleSwitch(chartPath);
  const chart = useRef(null);
  const [fullScreen, toggleFullScreen] = useToggle();

  return (
    <div className='view'>
      <Header />
      <main>
        <h1>{chartTitle}</h1>
        <Filters
          chartPath={chartPath}
          enableFullScreen={() => { enableFullScreen(chart, toggleFullScreen) }} />
        <Chart
          ref={chart}
          chartPath={chartPath}
          fullScreen={fullScreen}
          disableFullScreen={() => { disableFullScreen(chart, toggleFullScreen) }} />
      </main>
    </div>
  );
};

export default View;
