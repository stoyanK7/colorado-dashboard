import '../../css/site/View.css';

import { disableFullScreen, enableFullScreen, rotateFullScreen } from '../../util/fullScreen';
import { useEffect, useRef, useState } from 'react';

import Chart from '../shared/Chart';
import Filters from '../shared/Filters';
import Header from '../static/Header';
import React from 'react';
import chartTitleSwitch from '../../util/chartTitleSwitch';
import formatDate from '../../util/formatDate';
import { useParams } from 'react-router-dom';
import useToggle from '../../hooks/useToggle';

const View = () => {
  // Gets path from URL: i.e. https://xxxxx.com/InkInfo -> InkInfo
  const { chartPath } = useParams();
  const [chartTitle, setChartTitle] = useState('');
  const chart = useRef(null);
  const [fullScreen, toggleFullScreen] = useToggle();
  const [from, setFrom] = useState();
  const [to, setTo] = useState();
  const [link, setLink] = useState();
  const [chosenPrinters, setChosenPrinters] = useState([]);
  const [bin, setBin] = useState('day');
  const [aggregated, setAggregated] = useState(true);
  const [requestBody, setRequestBody] = useState();

  useEffect(() => {
    let requestLink = chartPath;
    if (from && to) {
      setChartTitle(`${chartTitleSwitch(chartPath)} from ${formatDate(from)} until ${formatDate(to)}`)
      setLink(requestLink + '/Period')
      setRequestBody({ from, to })
    }

    if (from && to && chosenPrinters.length > 0) {
      setChartTitle(`${chartTitleSwitch(chartPath)} from ${formatDate(from)} until ${formatDate(to)}`)
      setLink(`${requestLink}/PeriodAndPrinters?aggregated=${aggregated}`)
      setRequestBody({ from, to, printerIds: chosenPrinters })
    }
    // TODO: add rest of dependecies when available
  }, [from, to, chosenPrinters, aggregated]);


  return (
    <div className='view'>
      <Header />
      <main>
        <h1>{chartTitle}</h1>
        <Filters
          from={from}
          setFrom={setFrom}
          to={to}
          setTo={setTo}
          chartPath={chartPath}
          chosenPrinters={chosenPrinters}
          setChosenPrinters={setChosenPrinters}
          aggregated={aggregated}
          setAggregated={setAggregated}
          enableFullScreen={() => { enableFullScreen(chart, toggleFullScreen) }} />
        <Chart
          ref={chart}
          chartPath={chartPath}
          fullScreen={fullScreen}
          link={link}
          requestBody={requestBody}
          aggregated={aggregated}
          disableFullScreen={() => { disableFullScreen(chart, toggleFullScreen) }}
          rotateFullScreen={() => { rotateFullScreen(chart) }} />
      </main>
    </div>
  );
};

export default View;
