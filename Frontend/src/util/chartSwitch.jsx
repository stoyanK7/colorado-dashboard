import InkUsageBarChart from "../components/charts/InkUsageBarChart";
import MediaCategoryUsageBarChart from "../components/charts/MediaCategoryUsageBarChart";
import MediaTypesMerMachineBarChart from "../components/charts/MediaTypesPerMachineBarChart";
import React from 'react';
import SquareMeterPerPrintModeBarChart from "../components/charts/SquareMeterPerPrintModeBarChart";
import TopMachinesWithMostPrintVolumeBarChart from "../components/charts/TopMachinesWithMostPrintVolumeBarChart";

const chartSwitch = (chartPath, data, aggregated) => {
  let component;
  switch (chartPath) {
    case 'MediaCategoryUsage': component = <MediaCategoryUsageBarChart data={data} index='Date' />; break;
    case 'InkUsage': component = <InkUsageBarChart data={data} index='Date' aggregated={aggregated}/>; break;
    case 'SquareMeterPerPrintMode': component = <SquareMeterPerPrintModeBarChart data={data} index='Date' />; break;
    case 'TopMachinesWithMostPrintVolume': component = <TopMachinesWithMostPrintVolumeBarChart data={data} index='Printer id' />; break;
    case 'MediaTypesPerMachine': component = <MediaTypesMerMachineBarChart data={data} index='Media type' />; break;
    default: break;
  };
  return component;
};

export default chartSwitch;
