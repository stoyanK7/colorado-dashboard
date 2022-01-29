const chartTitleSwitch = (chart) => {
  let title;
  switch (chart) {
    case 'MediaCategoryUsage': title = 'Media categories usage'; break;
    case 'InkUsage': title = 'Ink usage'; break;
    case 'SquareMetersPerPrintMode': title = 'Printed square meters per print mode'; break;
    case 'TopMachinesWithMostPrintVolume': title = 'Top machines with most print volume'; break;
    case 'MediaTypesPerMachine': title = 'Used media types per machine'; break;
    default: title = 'Unknown chart'; break;
  };
  return title;
};

export default chartTitleSwitch;
