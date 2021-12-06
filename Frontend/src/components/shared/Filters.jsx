import { faExpand } from '@fortawesome/free-solid-svg-icons';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useState } from 'react';
import TimespanFilter from './TimespanFilter';

const Filters = ({ chartPath, enableFullScreen }) => {


  return (
    <>
      <div className='bins'>
        <span className='one-day'>1D</span>
        <span className='one-week'>1W</span>
      </div>
      <TimespanFilter chartPath={chartPath} />
      <div className='specific-printers'>
        <input type='text' placeholder='Specific printers..' />
      </div>
      <div className='full-screen' onClick={enableFullScreen}>
        <FontAwesomeIcon icon={faExpand} className='fa-expand' />
        <span>Full screen</span>
      </div>
    </>
  );
}

export default Filters;