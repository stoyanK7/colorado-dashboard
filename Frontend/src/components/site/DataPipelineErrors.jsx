import '../../css/site/DataPipelineErrors.css';

import { faArrowAltCircleDown, faCheckCircle, faClipboard, faFileAlt, faTimesCircle } from '@fortawesome/free-regular-svg-icons';
import { useEffect, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Header from '../static/Header';
import HoverTooltip from '../shared/HoverTooltip';
import { Link } from 'react-router-dom';
import axios from 'axios';
import formatDateTime from '../../util/formatDateTime';
import getFilenameFromPath from '../../util/getFilenameFromPath';
import { useParams } from 'react-router-dom';

const DataPipelineErrors = () => {
  const { errorId } = useParams();
  const [page, setPage] = useState(0);
  const [latestDataPipeline, setLatestDataPipeline] = useState();
  const [allDataPipelines, setAllDataPipelines] = useState([]);
  useEffect(() => {
    axios.get(`/DataPipelineErrors?page=${page}`)
      .then(res => res.data)
      .then(data => setAllDataPipelines([...allDataPipelines, ...data]))
  }, [page]);
  useEffect(() => {
    axios.get(`/DataPipelineErrors/${errorId}`)
      .then(res => res.data)
      .then(data => setLatestDataPipeline(data))
      .catch(err => setLatestDataPipeline())
  }, [errorId]);
  const copyToClipboard = e => {
    const text = latestDataPipeline.log;
    navigator.clipboard.writeText(text);
  };

  const show5More = e => setPage(page + 1);

  return (
    <div className='data-pipeline-errors'>
      <Header />
      <main>
        <div className='list-of-errors'>
          {allDataPipelines && allDataPipelines.map(dataPipeline => {
            return (
              <Link to={`/DataPipelineErrors/${dataPipeline.id}`} key={dataPipeline.id} className='error-card'>
                <p className='step'>{dataPipeline.step}</p>
                <p className='datetime'>{formatDateTime(dataPipeline.dateTime)}</p>
                {dataPipeline.passed ?
                  <FontAwesomeIcon icon={faCheckCircle}
                    style={{ color: 'var(--success)' }}
                    className='status' />
                  :
                  <FontAwesomeIcon icon={faTimesCircle}
                    style={{ color: 'var(--error)' }}
                    className='status' />}
              </Link>
            );
          })}
          <FontAwesomeIcon icon={faArrowAltCircleDown}
            data-tip='Show 5 more'
            className='show-more'
            onClick={show5More} />
        </div>
        <div className='error-info'>
          <p >
            <b>Date: </b> {latestDataPipeline && latestDataPipeline.dateTime}
          </p>
          <p >
            <b>Location: </b> {(latestDataPipeline && 'location' in latestDataPipeline) && latestDataPipeline.location}
          </p>
          <p >
            <b>Step: </b> {(latestDataPipeline && 'step' in latestDataPipeline) && latestDataPipeline.step}
          </p>
          <p >
            <b>Affected graphs: </b> {(latestDataPipeline && 'affectedGraphs' in latestDataPipeline) && latestDataPipeline.affectedGraphs}
          </p>
          <p >
            <b>Log: </b> <pre></pre>
          </p>
          <div className='info-log'>
            <div className="titlebar">
              <FontAwesomeIcon icon={faFileAlt} className='titlebar-fa' />
              <b>{(latestDataPipeline && 'location' in latestDataPipeline) && getFilenameFromPath(latestDataPipeline.location)}</b>
              <div className="actions" data-tip='Copy to clipboard' onClick={copyToClipboard}>
                <FontAwesomeIcon icon={faClipboard}
                  className='titlebar-fa' />
              </div>
              <HoverTooltip />
            </div>
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
