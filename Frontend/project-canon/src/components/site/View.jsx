import '../../css/View.css';

import Chart from '../shared/Chart';
import Header from '../static/Header';

const View = () => {
  return (
    <div className="view">
      <Header />
      <main>
        1D, 1W , some inputs, full screen button, whatever
        <Chart />
      </main>
    </div>
  );
};

export default View;
