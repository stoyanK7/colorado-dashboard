import ReactTooltip from 'react-tooltip';

const HoverTooltip = ({ backgroundColor = 'var(--carnelian)', id }) => {
  return (
    <ReactTooltip
      backgroundColor={backgroundColor}
      textColor='var(--white)'
      multiline={true}
      id={id}
    />
  );
};

export default HoverTooltip;