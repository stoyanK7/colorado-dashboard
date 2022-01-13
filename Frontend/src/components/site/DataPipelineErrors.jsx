import '../../css/site/DataPipelineErrors.css';

import { useEffect, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Header from '../static/Header';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { faTimesCircle } from '@fortawesome/free-regular-svg-icons';

const DataPipelineErrors = () => {
  const [latestError, setLatestError] = useState();
  useEffect(() => {
    axios.get('/DataPipelineErrors/Latest')
      .then(res => res.data)
      .then(data => setLatestError(data))
      .catch(err => setLatestError())
  }, []);

  return (
    <div className='data-pipeline-errors'>
      <Header />
      <main>
        <div className='list-of-errors'>
          <Link to='/DataPipelineErrors/1' className='error-card'>
            <p className='step'>Preprocessing</p>
            <p className='datetime'>2021-12-01 12:33:22</p>
            <FontAwesomeIcon icon={faTimesCircle}
              style={{ color: 'var(--error)' }}
              data-place='left'
              className='status' />
          </Link>
          <Link to='/DataPipelineErrors/1' className='error-card'>
            <p className='step'>Preprocessing</p>
            <p className='datetime'>2021-12-01 12:33:22</p>
            <FontAwesomeIcon icon={faTimesCircle}
              style={{ color: 'var(--error)' }}
              data-place='left'
              className='status' />
          </Link>
          <Link to='/DataPipelineErrors/1' className='error-card'>
            <p className='step'>Preprocessing</p>
            <p className='datetime'>2021-12-01 12:33:22</p>
            <FontAwesomeIcon icon={faTimesCircle}
              style={{ color: 'var(--error)' }}
              data-place='left'
              className='status' />
          </Link>
          <Link to='/DataPipelineErrors/1' className='error-card'>
            <p className='step'>Preprocessing</p>
            <p className='datetime'>2021-12-01 12:33:22</p>
            <FontAwesomeIcon icon={faTimesCircle}
              style={{ color: 'var(--error)' }}
              data-place='left'
              className='status' />
          </Link>
          <Link to='/DataPipelineErrors/1' className='error-card'>
            <p className='step'>Preprocessing</p>
            <p className='datetime'>2021-12-01 12:33:22</p>
            <FontAwesomeIcon icon={faTimesCircle}
              style={{ color: 'var(--error)' }}
              data-place='left'
              className='status' />
          </Link>


        </div>
        <div className='error-info'>
          <p >
            <b>Date: </b> {latestError && latestError.dateTime}
          </p>
          <p >
            <b>Location: </b> {latestError && latestError.location}
          </p>
          <p >
            <b>Step: </b> {latestError && latestError.step}
          </p>
          <p >
            <b>Affected graphs: </b> {latestError && latestError.affectedGraphs}
          </p>
          <p >
            <b>Log: </b> <pre></pre>
          </p>
          <div className='info-log'>
            <pre>
                {latestError && latestError.log}
            </pre>
          </div>
        </div>
      </main>
    </div>
  );
};

export default DataPipelineErrors;
