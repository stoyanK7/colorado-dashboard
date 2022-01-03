import { faExpand } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const FullScreen = ({ enableFullScreen }) => {
  return (
    <div className='full-screen' onClick={enableFullScreen}>
      <FontAwesomeIcon icon={faExpand} className='fa-search' />
      <span>Full screen</span>
    </div>
  );
};

export default FullScreen;