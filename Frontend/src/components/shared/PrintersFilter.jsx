import '../../css/shared/PrintersFilter.css';

import { faSearch, faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { useEffect, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import axios from 'axios';
import { motion } from 'framer-motion';

const PrintersFilter = ({ chartPath, chosenPrinters, setChosenPrinters, makeSpecificPrinterRequestHandler }) => {
  const [availablePrinters, setAvailablePrinters] = useState();
  const [menuStyle, setMenuStyle] = useState();
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

  useEffect(() => { }, [chosenPrinters]);
  const openMenuHandler = e => setMenuStyle({ y: -20 });
  const closeMenuHandler = e => setMenuStyle({ y: '-100vh' });

  return (
    <div className='specific-printers'>
      <input type='text' placeholder='Specific printers..' value={chosenPrinters} onClick={openMenuHandler} />
      {availablePrinters &&
        <motion.div
          className='printers-menu'
          initial={{ y: '-200vh' }}
          animate={menuStyle}
          transition={{ duration: 0.6, type: 'spring', stiffness: 80 }}
        >
          <h2>Available printers</h2>
          <div className="available-printer select-all">
            <label htmlFor='select-all'>Select / Deselect all</label>
            <input
              type="checkbox"
              name='select-all'
              id='select-all'
              onClick={e => {
                if (e.target.checked)
                  return setChosenPrinters([...availablePrinters]);
                return setChosenPrinters([]);
              }} />
          </div>
          <div className='available-printers' >
            {availablePrinters.map(printerId => (
              <div className='available-printer' key={printerId}>
                <label htmlFor={printerId}>{printerId}</label>
                <input
                  type="checkbox"
                  name={printerId}
                  id={printerId}
                  checked={chosenPrinters.includes(printerId)}
                  onClick={e => {
                    // Add element if checked
                    if (e.target.checked)
                      return setChosenPrinters([...chosenPrinters, e.target.name])
                    // Remove element if unchecked
                    setChosenPrinters(chosenPrinters.filter(item => {
                      return item !== e.target.name;
                    }));
                  }} />
              </div>
            ))}
          </div>
          <FontAwesomeIcon icon={faTimesCircle} className='fa-circle' onClick={closeMenuHandler} />
        </motion.div>
      }
      {/* <FontAwesomeIcon icon={faSearch} className='fa-expand' onClick={makeSpecificPrinterRequestHandler} /> */}
    </div>
  );
};

export default PrintersFilter;
