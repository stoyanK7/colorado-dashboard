import '../../css/static/Header.css';

import { faCheckCircle, faTimesCircle } from '@fortawesome/free-regular-svg-icons';
import { useEffect, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import HoverTooltip from '../shared/HoverTooltip';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { faExclamationTriangle } from '@fortawesome/free-solid-svg-icons';

const Header = () => {
  const [notificationComponent, setNotificationComponent] = useState();
  const [latestError, setLatestError] = useState();
  useEffect(() => {
    axios.get('/DataPipelineErrors/Latest')
      .then(res => res.data)
      .then(data => setLatestError(data))
      .catch(err => setLatestError())
  }, []);

  useEffect(() => {
    // If no error is retrieved
    if (latestError == null)
      return setNotificationComponent(
        <>
          <FontAwesomeIcon icon={faExclamationTriangle}
            style={{ color: 'var(--warning)' }}
            data-tip='Could not connect to API.<br>Not sure if the Airflow pipeline passed successfully.'
            data-place='left' />
          <HoverTooltip backgroundColor='var(--warning)' />
        </>
      );

    return latestError.passed ?
      setNotificationComponent(
        <>
          <FontAwesomeIcon icon={faCheckCircle}
            style={{ color: 'var(--success)' }}
            data-tip={`Airflow pipeline passed successfully on ${latestError.dateTime}. `}
            data-place='left' />
          <HoverTooltip backgroundColor='var(--success)' />
        </>
      )
      : setNotificationComponent(
        <>
          <FontAwesomeIcon icon={faTimesCircle}
            style={{ color: 'var(--error)' }}
            data-tip={`Airflow pipeline encountered errors on ${latestError.dateTime}.<br>Click to see more.`}
            data-place='left' />
          <HoverTooltip backgroundColor='var(--error)' />
        </>
      );
  }, [latestError]);

  return (
    <header>
      <Link to='/DataPipelineErrors' className='header-notification'>
        {notificationComponent && notificationComponent}
      </Link>
      <Link to='/' className='header'>Colorado</Link>
    </header>
  );
};

export default Header;
