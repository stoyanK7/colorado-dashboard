import '../../css/View.css';

import { faCalendarAlt, faExpand } from '@fortawesome/free-solid-svg-icons';
import { useRef, useState } from 'react';

import Chart from '../shared/Chart';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Header from '../static/Header';
import useToggle from '../../hooks/useToggle';

const View = () => {
  const [chartTitle, setChartTitle] = useState('');

  const chart = useRef(null);

  const [isFullScreen, toggle] = useToggle();
  // TODO: needs reworking. complete spaghetti but works 
  const toggleFullScreen = () => {

    if (!isFullScreen) {
      // before transition
      const scrollWidth = chart.current.scrollWidth;
      const scrollHeight = chart.current.scrollHeight;

      chart.current.style.setProperty('left', chart.current.offsetLeft + 'px');
      chart.current.style.setProperty('top', chart.current.offsetTop + 'px');
      chart.current.style.setProperty('position', 'fixed');
      chart.current.style.setProperty('width', scrollWidth + 'px');
      chart.current.style.setProperty('height', scrollHeight + 'px');

      // wait for code to run
      setTimeout(() => {
        toggle();
      }, 100)
    }

    if (isFullScreen) {
      toggle();

      // after transition
      setTimeout(() => {
        chart.current.style.removeProperty('width');
        chart.current.style.removeProperty('height');
        chart.current.style.removeProperty('left');
        chart.current.style.removeProperty('top');
        chart.current.style.removeProperty('position');
      }, 500)
    }
  };

  return (
    <div className='view'>
      <Header />
      <main>
        <h1>{chartTitle && chartTitle}</h1>
        <div className='bins'>
          <span className='one-day'>1D</span>
          <span className='one-week'>1W</span>
        </div>
        <div className='timespan'>
          <div className='from'>
            <input type='date' />
            <FontAwesomeIcon icon={faCalendarAlt} className='fa-calendar-alt' />
          </div>
          <span>to</span>
          <div className='to'>
            <input type='date' />
            <FontAwesomeIcon icon={faCalendarAlt} className='fa-calendar-alt' />
          </div>
        </div>
        <div className='specific-printers'>
          <input type='text' placeholder='Specific printers..' />
        </div>
        <div className='full-screen' onClick={toggleFullScreen}>
          <FontAwesomeIcon icon={faExpand} className='fa-expand' />
          <span>Full screen</span>
        </div>
        <Chart ref={chart} setChartTitle={setChartTitle} isFullScreen={isFullScreen} toggleFullScreen={toggleFullScreen} />
      </main>
    </div>
  );
};

export default View;
