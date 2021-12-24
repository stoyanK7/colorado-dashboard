import '../../css/site/Home.css';

import Header from '../static/Header';
import { Link } from 'react-router-dom';
import React from 'react';

const Home = () => {
  return (
    <div className='home'>
      <Header />
      <main>
        <Link to='/InkUsage' className='chart main-chart'>
          <div className='chart-header'>Ink usage</div>
        </Link>
        <Link to='/TopMachinesWithMostPrintVolume' className='chart side-chart-top'>
          <div className='chart-header'>Top machines with most print volume</div>
        </Link>
        <Link to='/MediaTypesPerMachine' className='chart side-chart-middle'>
          <div className='chart-header'>Used media types per machine</div>
        </Link>
        <Link to='/SquareMeterPerPrintMode' className='chart second-line-left-chart'>
          <div className='chart-header'>Square meters per print mode</div>
        </Link>
        <Link to='/MediaCategoryUsage' className='chart second-line-right-chart'>
          <div className='chart-header'>Media categories usage</div>
        </Link>
      </main >
    </div >
  );
};

export default Home;
