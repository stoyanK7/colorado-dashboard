import '../../css/View.css';

import { faCalendarAlt, faExpand } from '@fortawesome/free-solid-svg-icons'

import Chart from '../shared/Chart';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import Header from '../static/Header';

const View = () => {
  return (
    <div className='view'>
      <Header />
      <main>
        <div className='filters'>
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
          <div className='full-screen'>
            <FontAwesomeIcon icon={faExpand} className='fa-expand' />
            <span>Full screen</span>
          </div>
        </div>
        <Chart />
      </main>
    </div>
  );
};

export default View;
