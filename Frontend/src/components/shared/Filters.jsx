import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import PrintersFilter from './PrintersFilter';
import React from 'react';
import TimespanFilter from './TimespanFilter';
import { faExpand } from '@fortawesome/free-solid-svg-icons';
import BinFilter from './BinFilter';

const Filters = ({ chartPath, enableFullScreen, from, setFrom, to, setTo, chosenPrinters, setChosenPrinters, aggregated, setAggregated, setBin }) => {
  return (
    <>
      <BinFilter setBin={setBin}/>
      <TimespanFilter
        from={from}
        setFrom={setFrom}
        to={to}
        setTo={setTo}
        chartPath={chartPath} />
      <PrintersFilter
        chartPath={chartPath}
        chosenPrinters={chosenPrinters}
        setChosenPrinters={setChosenPrinters}
        aggregated={aggregated}
        setAggregated={setAggregated} />
      <div className='full-screen' onClick={enableFullScreen}>
        <FontAwesomeIcon icon={faExpand} className='fa-search' />
        <span>Full screen</span>
      </div>
    </>
  );
};

export default Filters;