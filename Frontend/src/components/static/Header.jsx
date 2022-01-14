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
  const [latestDataPipeline, setLatestDataPipeline] = useState();
  useEffect(() => {
    axios.get('/DataPipelineErrors/Latest')
      .then(res => res.data)
      .then(data => setLatestDataPipeline(data))
      .catch(err => setLatestDataPipeline())
  }, []);

  useEffect(() => {
    // If no error is retrieved
    if (latestDataPipeline == null)
      return setNotificationComponent(
        <>
          <FontAwesomeIcon icon={faExclamationTriangle}
            style={{ color: 'var(--warning)' }}
            data-tip='Could not connect to API.<br>Not sure if the Airflow pipeline passed successfully.'
            data-place='left' />
          <HoverTooltip backgroundColor='var(--warning)' />
        </>
      );

    // Else if the pipline passed
    return latestDataPipeline.passed ?
      setNotificationComponent(
        <>
          <FontAwesomeIcon icon={faCheckCircle}
            style={{ color: 'var(--success)' }}
            data-tip={`Airflow pipeline passed successfully on ${latestDataPipeline.dateTime}. `}
            data-place='left' />
          <HoverTooltip backgroundColor='var(--success)' />
        </>
      )
      // else it failed
      : setNotificationComponent(
        <>
          <FontAwesomeIcon icon={faTimesCircle}
            style={{ color: 'var(--error)' }}
            data-tip={`Airflow pipeline encountered errors on ${latestDataPipeline.dateTime}.<br>Click to see more.`}
            data-place='left' />
          <HoverTooltip backgroundColor='var(--error)' />
        </>
      );
  }, [latestDataPipeline]);

  return (
    <header>
      {latestDataPipeline &&
        <Link to={`/DataPipelineErrors/${latestDataPipeline.id}`} className='header-notification'>
          {notificationComponent && notificationComponent}
        </Link>}
      <Link to='/' className='header'>Colorado</Link>
    </header>
  );
};

export default Header;
