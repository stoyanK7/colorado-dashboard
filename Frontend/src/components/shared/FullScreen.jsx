import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faExpand } from '@fortawesome/free-solid-svg-icons';

const FullScreen = ({ enableFullScreen }) => {
  return (
    <div className='full-screen' onClick={enableFullScreen} data-tip='Enable full screen mode'>
      <FontAwesomeIcon icon={faExpand} className='fa-search' />
      <span>Full screen</span>
    </div>
  );
};

export default FullScreen;