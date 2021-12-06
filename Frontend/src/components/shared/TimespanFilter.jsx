import { faCalendarAlt, faExpand } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import axios from 'axios';
import { useEffect, useState } from 'react';

const TimespanFilter = ({ chartPath }) => {
  const [from, setFrom] = useState();
  const [to, setTo] = useState();

  useEffect(() => {
    axios.get(`${chartPath}/AvailableTimePeriod`)
      .then(res => {
        return res.data;
      })
      .then(data => {
        setFrom(data.from);
        setTo(data.to);
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
              min={from}
              max={to} />
            <FontAwesomeIcon icon={faCalendarAlt} className='fa-calendar-alt' />
          </div>
          <span>to</span>
          <div className='to'>
            <input
              type='date'
              defaultValue={to}
              min={from}
              max={to} />
            <FontAwesomeIcon icon={faCalendarAlt} className='fa-calendar-alt' />
          </div>
        </div>
      }
    </>
  );
}

export default TimespanFilter;