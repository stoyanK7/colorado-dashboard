import './App.css';

import { useEffect, useState } from 'react';

import MediaCategoryBarChart from './components/MediaCategoryBarChart';
import axios from 'axios';

function App() {

  const [data, setData] = useState();

  useEffect(() => {
    axios.get('http://localhost:8080/PrintSquareMeterPerMediaType')
      .then(res => {
        return res.data;
      })
      .then(apiData => {
        setData(apiData);
      })
  }, [])


  
  return (
    <>
      <div style={{ height: "100vh" }}>
        {data &&
          <MediaCategoryBarChart {...{ data: data, index: "date" }} />
        }
      </div>
    </>
  );
}

export default App;
