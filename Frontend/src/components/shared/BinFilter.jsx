const BinFilter = ({ setBin }) => {
  return (
    <div className='bins'>
      <span className='one-day' data-tip='Group by day' onClick={() => setBin('day')}>1D</span>
      <span className='one-week' data-tip='Group by week' onClick={() => setBin('week')} > 1W</span>
    </div >
  );
};

export default BinFilter;
