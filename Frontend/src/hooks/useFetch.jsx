import { useEffect, useState } from 'react';

import axios from 'axios';

const baseURL = 'http://localhost:8080/';

const useFetch = (url, requestBody) => {
  const [data, setData] = useState(null);
  const [isPending, setIsPending] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const abortController = new AbortController();
    axios.post(baseURL + url, {...requestBody})
      .then(res => {
        if (res.status !== 200) throw Error(res.message);
        return res.data;
      })
      .then(data => {
        setData(data);
        setError(null);
        setIsPending(false);
      })
      .catch(err => {
        if (err.name === 'AbortError') console.log('fetch aborted')
        else {
          setIsPending(false);
          setError(err.message);
        }
      });

    return () => abortController.abort();
  }, [url, requestBody]);

  return { data, isPending, error };
}

export default useFetch;
