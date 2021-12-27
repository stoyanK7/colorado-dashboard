import PrintersFilter from './PrintersFilter';
import React from 'react';
import TimespanFilter from './TimespanFilter';
import BinFilter from './BinFilter';
import FullScreen from './FullScreen';

const Filters = ({ chartPath, enableFullScreen, from, setFrom, to, setTo, chosenPrinters, setChosenPrinters, aggregated, setAggregated, setBin }) => {
  return (
    <>
      <BinFilter setBin={setBin} />
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
      <FullScreen enableFullScreen={enableFullScreen} />
    </>
  );
};

export default Filters;