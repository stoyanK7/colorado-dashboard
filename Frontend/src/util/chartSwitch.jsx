import InkUsageBarChart from "../components/charts/InkUsageBarChart";
import MediaCategoryUsageBarChart from "../components/charts/MediaCategoryUsageBarChart";
import SquareMeterPerPrintModeBarChart from "../components/charts/SquareMeterPerPrintModeBarChart";
import TopMachinesWithMostPrintVolumeBarChart from "../components/charts/TopMachinesWithMostPrintVolumeBarChart";

const chartSwitch = (chartPath, data) => {
  let component;
  switch (chartPath) {
    case 'MediaCategoryUsage': component = <MediaCategoryUsageBarChart data={data} index='date' />; break;
    case 'InkUsage': component = <InkUsageBarChart data={data} index='date' />; break;
    case 'SquareMeterPerPrintMode': component = <SquareMeterPerPrintModeBarChart data={data} index='date' />; break;
    case 'TopMachinesWithMostPrintVolume': component = <TopMachinesWithMostPrintVolumeBarChart data={data} index='Printer id' />; break;
    case 'MediaTypesPerMachine': component = <SquareMeterPerPrintModeBarChart data={data} index='date' />; break;
    default: break;
  };
  return component;
};

export default chartSwitch;
