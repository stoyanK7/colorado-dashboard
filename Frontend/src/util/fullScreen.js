// TODO: needs reworking. Complete spaghetti but works 
const disableFullScreen = (chart, toggleFullScreen) => {
  toggleFullScreen();

  // after transition
  setTimeout(() => {
    chart.current.style.removeProperty('width');
    chart.current.style.removeProperty('height');
    chart.current.style.removeProperty('left');
    chart.current.style.removeProperty('top');
    chart.current.style.removeProperty('position');
  }, 500)
};

const enableFullScreen = (chart, toggleFullScreen) => {
  // before transition
  const scrollWidth = chart.current.scrollWidth;
  const scrollHeight = chart.current.scrollHeight;

  chart.current.style.setProperty('left', chart.current.offsetLeft + 'px');
  chart.current.style.setProperty('top', chart.current.offsetTop + 'px');
  chart.current.style.setProperty('position', 'fixed');
  chart.current.style.setProperty('width', scrollWidth + 'px');
  chart.current.style.setProperty('height', scrollHeight + 'px');

  // wait for code to run
  setTimeout(() => toggleFullScreen(), 100);
};

const rotateFullScreen = (chart) => {
  if (chart.current.classList.contains('chart-full-screen-rotated'))
    return chart.current.classList.remove('chart-full-screen-rotated')
  return chart.current.classList.add('chart-full-screen-rotated');
};

export { enableFullScreen, disableFullScreen, rotateFullScreen };