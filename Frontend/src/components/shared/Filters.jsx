import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import PrintersFilter from './PrintersFilter';
import TimespanFilter from './TimespanFilter';
import { faExpand } from '@fortawesome/free-solid-svg-icons';

const Filters = ({ chartPath, enableFullScreen, from, setFrom, to, setTo, chosenPrinters, setChosenPrinters, makeSpecificPrinterRequestHandler }) => {
  return (
    <>
      <div className='bins'>
        <span className='one-day'>1D</span>
        <span className='one-week'>1W</span>
      </div>
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
        makeSpecificPrinterRequestHandler={makeSpecificPrinterRequestHandler} />
      <div className='full-screen' onClick={enableFullScreen}>
        <FontAwesomeIcon icon={faExpand} className='fa-search' />
        <span>Full screen</span>
      </div>
    </>
  );
};

export default Filters;