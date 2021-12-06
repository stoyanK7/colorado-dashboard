import { useEffect, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import axios from 'axios';
import { faCalendarAlt } from '@fortawesome/free-solid-svg-icons';

const TimespanFilter = ({ chartPath, to, setTo, from, setFrom }) => {
  const [min, setMin] = useState();
  const [max, setMax] = useState();

  useEffect(() => {
    axios.get(`${chartPath}/AvailableTimePeriod`)
      .then(res => {
        return res.data;
      })
      .then(data => {
        setFrom(data.from);
        setTo(data.to);

        setMin(data.from);
        setMax(data.to);
      })
      .catch(err => {
        // TODO: introduce error handling logic
      }) 
  }, []);

  return (
    <>
      {from && to &&
        <div className='timespan'>
          <div className='from'>
            <input
              type='date'
              defaultValue={from}
              min={min}
              max={max}
              onChange={(e) => setFrom(e.target.value)} />
            <FontAwesomeIcon icon={faCalendarAlt} className='fa-calendar-alt' />
          </div>
          <span>to</span>
          <div className='to'>
            <input
              type='date'
              defaultValue={to}
              min={min}
              max={max}
              onChange={(e) => setTo(e.target.value)} />
            <FontAwesomeIcon icon={faCalendarAlt} className='fa-calendar-alt' />
          </div>
        </div>
      }
    </>
  );
};

export default TimespanFilter;