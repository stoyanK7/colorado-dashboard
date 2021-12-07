import '../../css/shared/PrintersFilter.css';

import { faSearch, faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { useEffect, useState } from 'react';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import axios from 'axios';
import { motion } from 'framer-motion';

const PrintersFilter = ({ chartPath, chosenPrinters, setChosenPrinters }) => {
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
  const openMenuHandler = e => setMenuStyle({ y: 50});
  const closeMenuHandler = e => setMenuStyle({ y: '-100vh' });

  return (
    <div className='specific-printers'>
      <input type='text' placeholder='Specific printers..' value={chosenPrinters} onClick={openMenuHandler} />
      {availablePrinters &&
        <motion.div 
        className='printers-menu' 
        // style={menuStyle}
        initial={{y: '-100vh'}}
        animate={menuStyle}
        transition={{duration: 0.6, type:'spring', stiffness: 80}}
        >
          <h2>Available printers</h2>
          <div className='available-printers' >
            {availablePrinters.map(printerId => (
              <div className='available-printer' key={printerId}>
                <label htmlFor={printerId}>{printerId}</label>
                <input
                  type="checkbox"
                  name={printerId}
                  id={printerId}
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
          <FontAwesomeIcon icon={faTimesCircle} className='fa-circle' onClick={closeMenuHandler}/>
        </motion.div>
      }
      <FontAwesomeIcon icon={faSearch} className='fa-expand' />
    </div>
  );
};

export default PrintersFilter;
