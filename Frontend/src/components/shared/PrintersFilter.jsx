import '../../css/shared/PrintersFilter.css';

import { useEffect, useState } from 'react';

import axios from 'axios';

const PrintersFilter = ({ chartPath }) => {
  const [availablePrinters, setAvailablePrinters] = useState();
  useEffect(() => {
    axios.get(`${chartPath}/AvailablePrinters`)
      .then(res => res.data)
      .then(data => {
        setAvailablePrinters(data.printerIds);
      })
      .catch(err => {
        // TODO: introduce error handling logic
      })
  }, []);

  return (
    <div className='specific-printers'>
      <input type='text' placeholder='Specific printers..' />
      {availablePrinters &&
        <div className='printers-menu'>
          <h2>Available printers</h2>
          <div className='available-printers'>
            {availablePrinters.map(printerId => (
              <div className='available-printer'>
                <label htmlFor={printerId}>{printerId}</label>
                <input type="checkbox" name={printerId} id={printerId} />
              </div>
            ))}
          </div>
        </div>
      }
    </div>
  );
};

export default PrintersFilter;
