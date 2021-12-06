import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import PrintersFilter from './PrintersFilter';
import TimespanFilter from './TimespanFilter';
import { faExpand } from '@fortawesome/free-solid-svg-icons';

const Filters = ({ chartPath, enableFullScreen, from, setFrom, to, setTo }) => {
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
        chartPath={chartPath} />
      <div className='full-screen' onClick={enableFullScreen}>
        <FontAwesomeIcon icon={faExpand} className='fa-expand' />
        <span>Full screen</span>
      </div>
    </>
  );
};

export default Filters;