import '../../css/site/DataPipelineErrors.css';

import { faCheckCircle, faClipboard, faFileAlt, faTimesCircle } from '@fortawesome/free-regular-svg-icons';
import { useEffect, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Header from '../static/Header';
import HoverTooltip from '../shared/HoverTooltip';
import { Link } from 'react-router-dom';
import axios from 'axios';
import getFilenameFromPath from '../../util/getFilenameFromPath';
import { useParams } from 'react-router-dom';

const DataPipelineErrors = () => {
  const { errorId } = useParams();
  const [latestDataPipeline, setLatestDataPipeline] = useState();
  const [allDataPipelines, setAllDataPipelines] = useState();
  useEffect(() => {
    axios.get(`/DataPipelineErrors`)
      .then(res => res.data)
      .then(data => setAllDataPipelines(data))
      .catch(err => setAllDataPipelines())
  }, []);
  useEffect(() => {
    axios.get(`/DataPipelineErrors/${errorId}`)
      .then(res => res.data)
      .then(data => setLatestDataPipeline(data))
      .catch(err => setLatestDataPipeline())
  }, [errorId]);
  const onClickHandler = e => {
    const text = latestDataPipeline.log;
    navigator.clipboard.writeText(text);
  };

  return (
    <div className='data-pipeline-errors'>
      <Header />
      <main>
        <div className='list-of-errors'>
          {allDataPipelines && allDataPipelines.map(dataPipeline => {
            return (
              <Link to={`/DataPipelineErrors/${dataPipeline.id}`} className='error-card'>
                <p className='step'>{dataPipeline.step}</p>
                <p className='datetime'>{dataPipeline.dateTime}</p>
                {dataPipeline.passed ?
                  <FontAwesomeIcon icon={faCheckCircle}
                    style={{ color: 'var(--success)' }}
                    data-place='left'
                    className='status' />
                  :
                  <FontAwesomeIcon icon={faTimesCircle}
                    style={{ color: 'var(--error)' }}
                    data-place='left'
                    className='status' />}
              </Link>
            );
          })}
        </div>
        <div className='error-info'>
          <p >
            <b>Date: </b> {latestDataPipeline && latestDataPipeline.dateTime}
          </p>
          <p >
            <b>Location: </b> {latestDataPipeline && latestDataPipeline.location}
          </p>
          <p >
            <b>Step: </b> {latestDataPipeline && latestDataPipeline.step}
          </p>
          <p >
            <b>Affected graphs: </b> {latestDataPipeline && latestDataPipeline.affectedGraphs}
          </p>
          <p >
            <b>Log: </b> <pre></pre>
          </p>
          <div className='info-log'>
            <div className="titlebar">
              <FontAwesomeIcon icon={faFileAlt} className='titlebar-fa' />
              <b>{latestDataPipeline && getFilenameFromPath(latestDataPipeline.location)}</b>
              <div className="actions" data-tip='Copy to clipboard' onClick={onClickHandler}>
                <FontAwesomeIcon icon={faClipboard}
                  className='titlebar-fa' />
              </div>
            </div>
            <HoverTooltip />
            <pre>
              {latestDataPipeline && latestDataPipeline.log}
            </pre>
          </div>
        </div>
      </main>
    </div>
  );
};

export default DataPipelineErrors;
