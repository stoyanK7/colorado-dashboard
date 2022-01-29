import { useEffect, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import HoverTooltip from './HoverTooltip';
import React from 'react';
import axios from 'axios';
import { faCalendarAlt } from '@fortawesome/free-solid-svg-icons';

const TimespanFilter = ({ chartPath, to, setTo, from, setFrom }) => {
  const [min, setMin] = useState();
  const [max, setMax] = useState();

  // When the component is first rendered, retrieve the min and max date that can be provided to the API
  useEffect(() => {
    axios.get(`${chartPath}/AvailableTimePeriod`)
      .then(res => res.data)
      .then(data => {
        let d = new Date(data.to);
        d.setMonth(d.getMonth() - 1);
        // If from is closer than one month from to, use date from, else get data for one month back
        setFrom(new Date(data.from) > d ? data.from : d.toISOString().split('T')[0]);
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
          <div className='from' >
            <HoverTooltip />
            <input
              type='date'
              data-tip='Start date'
              defaultValue={from}
              min={min}
              max={max}
              onChange={(e) => {
                if (e.target.value > to) {
                  e.target.value = from;
                  return alert('Starting date cannot be later than ending date. Chart will not be rendered.');
                }
                setFrom(e.target.value);
              }} />
            <FontAwesomeIcon icon={faCalendarAlt} className='fa-calendar-alt' />
          </div>
          <span>to</span>
          <div className='to' data-tip='Ending date'>
            <input
              type='date'
              data-tip='End date'
              defaultValue={to}
              min={min}
              max={max}
              onChange={(e) => {
                if (e.target.value < from) {
                  e.target.value = max;
                  return alert('Ending date cannot be earlier than starting date. Chart will not be rendered.');
                }
                setTo(e.target.value);
              }} />
            <FontAwesomeIcon icon={faCalendarAlt} className='fa-calendar-alt' />
          </div>
        </div>
      }
    </>
  );
};

export default TimespanFilter;