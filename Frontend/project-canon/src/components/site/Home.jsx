import '../../css/site/Home.css';

import Header from '../static/Header';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div className='home'>
      <Header />
      <main>
        <div className='chart main-chart'>
          <Link to='PrintSquareMeterPerMediaType'>
            <div className='chart-header'>Printed square metres per media type</div>
            <div className='img'></div>
          </Link>
        </div>
        <div className='chart side-chart-top'>
          <div className='chart-header'>Amount of printed square metres per print mode</div>
          <div className='img'></div>
        </div>
        <div className='chart side-chart-middle'>
          <div className='chart-header'>Ink usage</div>
          <div className='img'></div>
        </div>
        <div className='chart second-line-left-chart'>
          <div className='chart-header'> Best printing machines on the market</div>
          <div className='img'></div>
        </div>
        <div className='chart second-line-right-chart'>
          <div className='chart-header'>All used media types for a selected machine</div>
          <div className='img'></div>
        </div>
      </main >
    </div >
  );
};

export default Home;
