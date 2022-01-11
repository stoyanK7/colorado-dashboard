const CustomTooltip = ({ active, payload, index, printer }) => {
  if (active && payload && payload.length) {
    return (
      <div className='colorado-custom-tooltip'>
        {payload[0].payload[index] && <p className='label'>{`${index}: ${payload[0].payload[index]}`}</p>}
        {payload[0].payload['Printer id'] && <p className='label'>{`Printer id: ${payload[0].payload['Printer id']}`}</p>}
        {payload.map(obj => <p className='label' key={obj.dataKey} style={{ color: obj.fill }}>{printer(obj)}</p>)}
      </div>
    );
  };

  return null;
};

export default CustomTooltip;
