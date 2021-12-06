import '../../css/site/View.css';

import { disableFullScreen, enableFullScreen } from '../../util/fullScreen';
import { useRef, useState } from 'react';

import Chart from '../shared/Chart';
import Filter from '../shared/Filter';
import Header from '../static/Header';
import useToggle from '../../hooks/useToggle';

const View = () => {
  const [chartTitle, setChartTitle] = useState('');

  const chart = useRef(null);

  const [fullScreen, toggleFullScreen] = useToggle();

  return (
    <div className='view'>
      <Header />
      <main>
        <h1>{chartTitle && chartTitle}</h1>
        <Filter enableFullScreen={() => { enableFullScreen(chart, toggleFullScreen) }} />
        <Chart
          ref={chart}
          setChartTitle={setChartTitle}
          fullScreen={fullScreen}
          disableFullScreen={() => { disableFullScreen(chart, toggleFullScreen) }} />
      </main>
    </div>
  );
};

export default View;
