import '../../css/Home.css';

import Header from '../static/Header';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div className='home'>
      <Header />
      <main>
        {/* Write all HTML here in the <main> tag */}
        <Link to='/PrintSquareMeterPerMediaType'>Print square meter per media type</Link>
      </main>
    </div>
  );
}

export default Home;
