import { useEffect, useState } from 'react';

const baseURL = 'http://localhost:8080';

const useFetch = (url) => {
  const [data, setData] = useState(null);
  const [isPending, setIsPending] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const abortController = new AbortController();

    setTimeout(() => {
      fetch(baseURL + url, { signal: abortController.signal })
        .then(res => {
          if (res.status !== 200) throw Error(res.message);
          return res.json();
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
    }, 2000);

    return () => abortController.abort();
  }, [url]);

  return { data, isPending, error };
}

export default useFetch;
