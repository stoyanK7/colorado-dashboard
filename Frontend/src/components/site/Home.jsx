import '../../css/site/Home.css';

import * as sampleChartData from '../../util/homeChartsSampleData';

import Header from '../static/Header';
import InkUsageBarChart from '../charts/InkUsageBarChart';
import { Link } from 'react-router-dom';
import MediaCategoryUsageBarChart from '../charts/MediaCategoryUsageBarChart';
import MediaTypesMerMachineBarChart from '../charts/MediaTypesPerMachineBarChart';
import SquareMeterPerPrintModeBarChart from '../charts/SquareMeterPerPrintModeBarChart';
import TopMachinesWithMostPrintVolumeBarChart from '../charts/TopMachinesWithMostPrintVolumeBarChart';

const Home = () => {
  return (
    <div className='home'>
      <Header />
      <main>
        <Link to='/InkUsage' className='chart main-chart'>
          <div className='chart-header'>Ink usage</div>
          <div className='white-overlay'></div>
          <InkUsageBarChart data={sampleChartData.inkUsageSampleData} index='Date' legend={false} />
        </Link>
        <Link to='/TopMachinesWithMostPrintVolume' className='chart side-chart-top'>
          <div className='chart-header'>Top machines with most print volume</div>
          <div className='white-overlay'></div>
          <TopMachinesWithMostPrintVolumeBarChart data={sampleChartData.topMachinesWithMostPrintVolumeSampleData} index='Printer id' legend={false} />
        </Link>
        <Link to='/MediaTypesPerMachine' className='chart side-chart-middle'>
          <div className='chart-header'>Used media types per machine</div>
          <div className='white-overlay'></div>
          <MediaTypesMerMachineBarChart data={sampleChartData.mediaTypesPerMachineSampleData} index='Date' legend={false} />
        </Link>
        <Link to='/SquareMetersPerPrintMode' className='chart second-line-left-chart'>
          <div className='chart-header'>Square meters per print mode</div>
          <div className='white-overlay'></div>
          <SquareMeterPerPrintModeBarChart data={sampleChartData.squareMetersPerPrintModeSampleData} index='Date' legend={false} />
        </Link>
        <Link to='/MediaCategoryUsage' className='chart second-line-right-chart'>
          <div className='chart-header'>Media categories usage</div>
          <div className='white-overlay'></div>
          <MediaCategoryUsageBarChart data={sampleChartData.mediaCategoryUsageSampleData} index='Date' legend={false} />
        </Link>
      </main >
    </div >
  );
};

export default Home;
