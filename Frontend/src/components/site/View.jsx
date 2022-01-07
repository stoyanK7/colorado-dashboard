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
import HoverTooltip from '../shared/HoverTooltip';

const View = () => {
  // Gets path from URL: i.e. https://xxxxx.com/InkUsage -> InkUsage
  const { chartPath } = useParams();
  const chart = useRef(null);
  const [chartTitle, setChartTitle] = useState('');
  const [requestUrl, setRequestUrl] = useState(`/${chartPath}`);
  const [requestBody, setRequestBody] = useState();
  const [fullScreen, toggleFullScreen] = useToggle();
  // Filters
  const [from, setFrom] = useState();
  const [to, setTo] = useState();
  const [chosenPrinters, setChosenPrinters] = useState([]);
  const [bin, setBin] = useState('day');
  const [aggregated, setAggregated] = useState(true);

  useEffect(() => {
    // TODO: this can be removed
    setChartTitle(`${chartTitleSwitch(chartPath)} from ${formatDate(from)} until ${formatDate(to)}`)
    if (from && to) {
      setRequestUrl(`${chartPath}/Period?aggregated=${aggregated}&bin=${bin}`);
      setRequestBody({ from, to });
    }

    if (from && to && chosenPrinters.length > 0) {
      setRequestUrl(`${chartPath}/PeriodAndPrinters?aggregated=${aggregated}&bin=${bin}`);
      setRequestBody({ from, to, printerIds: chosenPrinters });
    }
  }, [from, to, chosenPrinters, aggregated, bin]);

  return (
    <div className='view'>
      <Header />
      <HoverTooltip />
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
          setBin={setBin}
          enableFullScreen={() => { enableFullScreen(chart, toggleFullScreen) }} />
        <Chart
          ref={chart}
          chartPath={chartPath}
          fullScreen={fullScreen}
          requestUrl={requestUrl}
          requestBody={requestBody}
          aggregated={aggregated}
          disableFullScreen={() => { disableFullScreen(chart, toggleFullScreen) }}
          rotateFullScreen={() => { rotateFullScreen(chart) }} />
      </main>
    </div>
  );
};

export default View;
