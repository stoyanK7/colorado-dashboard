import '../../css/Chart.css';

import Loading from '../static/Loading';
import MediaCategoryBarChart from '../charts/MediaCategoryBarChart';
import useFetch from '../../hooks/useFetch';
import { useParams } from 'react-router-dom';

const Chart = () => {
  const { chart } = useParams();

  const { data, isPending, error } = useFetch(`/${chart}`);

  let component;
  switch (chart) {
    case 'PrintSquareMeterPerMediaType': component = <MediaCategoryBarChart data={data} index='date' />; break;
    // TODO: add the rest of the paths when the API has them
    default: break;
  }

  return (
    <div className='chart-wrapper-1'>
      {isPending && <Loading />}
      {error && <h1>An error occured: {error}</h1>}
      <div className='chart-wrapper-2'>
        {data && <div className='chart'>{component}</div>}
      </div>
    </div>
  );
};

export default Chart;
