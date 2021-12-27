const BinFilter = ({ setBin }) => {
  return (
    <div className='bins'>
      <span className='one-day' onClick={() => setBin('day')}>1D</span>
      <span className='one-week' onClick={() => setBin('week')} > 1W</span>
    </div >
  );
};

export default BinFilter;
