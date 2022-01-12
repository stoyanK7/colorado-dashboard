import '../../css/static/Header.css';

import { faCheckCircle, faTimesCircle } from '@fortawesome/free-regular-svg-icons';
import { useEffect, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { faExclamationTriangle } from '@fortawesome/free-solid-svg-icons';
import HoverTooltip from '../shared/HoverTooltip';

const Header = () => {
  const [notificationComponent, setNotificationComponent] = useState();
  const [latestError, setLatestError] = useState();
  useEffect(() => {
    axios.get('/DataPipelineError/Latest')
      .then(res => res.data)
      .then(data => setLatestError(data))
      .catch(err => setLatestError())
  }, []);

  useEffect(() => {
    if (latestError == null)
      return setNotificationComponent(
        <>
          <FontAwesomeIcon icon={faExclamationTriangle} 
          style={{ color: 'var(--warning)' }} 
          className='header-notification' 
          data-tip='Could not connect to API. Not sure if the Airflow pipeline passed successfully.'/>
          <HoverTooltip backgroundColor='var(--warning)' />
        </>
      );

    return latestError.passed ?
      setNotificationComponent(
        <>
          <FontAwesomeIcon icon={faCheckCircle}
            style={{ color: 'var(--success)' }}
            className='header-notification' 
            data-tip='Airflow pipeline passed successfully.'/>
          <HoverTooltip backgroundColor='var(--success)' />
        </>
      )
      : setNotificationComponent(
        <>
          <FontAwesomeIcon icon={faTimesCircle}
            style={{ color: 'var(--error)' }}
            className='header-notification'
            data-tip='Airflow pipeline has errors. Click to see more.' />
          <HoverTooltip backgroundColor='var(--error)' />
        </>
      );
  }, [latestError]);

  return (
    <header>
      {notificationComponent && notificationComponent}
      <Link to='/'>Colorado</Link>
    </header>
  );
};

export default Header;
