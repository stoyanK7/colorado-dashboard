import ReactTooltip from 'react-tooltip';

const HoverTooltip = ({ backgroundColor = 'var(--carnelian)' }) => {
  return (
    <ReactTooltip
      backgroundColor={backgroundColor}
      textColor='var(--white)'
    />
  );
};

export default HoverTooltip;