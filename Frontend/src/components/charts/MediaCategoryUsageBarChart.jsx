import { ResponsiveBarCanvas } from '@nivo/bar';
import chartTheme from '../../util/chartTheme';

const MediaCategoryUsageBarChart = ({ data, index }) => {
    if (typeof data === 'undefined' || data.length === 0) return null;
    // TODO: Seperate into own function
    let keys = Object.keys(data[0]);
    keys.splice(keys.indexOf('Date'), 1);
    keys.sort();
    return (
        <>
            <ResponsiveBarCanvas
                animate={false}
                borderWidth={1}
                data={data}
                keys={keys}
                indexBy={index}
                margin={{ top: 50, right: 130, bottom: 70, left: 60 }}
                padding={0.05}
                valueScale={{ type: 'linear' }}
                indexScale={{ type: 'band', round: true }}
                // colors={({ data, id, indexValue }) => chartTheme(data, id, indexValue, 'media-category-usage')}
                colors={{scheme:'nivo'}}
                borderColor={{ from: 'color', modifiers: [['darker', 1.6]] }}
                axisBottom={{
                    tickSize: 5,
                    tickPadding: 5,
                    tickRotation: 40,
                    legend: 'Media type',
                    legendPosition: 'middle',
                    legendOffset: 55
                }}
                axisLeft={{
                    tickSize: 5,
                    tickPadding: 5,
                    tickRotation: 0,
                    legend: 'Printed square meters',
                    legendPosition: 'middle',
                    legendOffset: -40
                }}
                enableLabel={false}
                legends={[
                    {
                        dataFrom: 'keys',
                        anchor: 'bottom-right',
                        direction: 'column',
                        justify: false,
                        translateX: 120,
                        translateY: -10,
                        itemsSpacing: 2,
                        itemWidth: 100,
                        itemHeight: 20,
                        itemDirection: 'left-to-right',
                        itemOpacity: 0.85,
                        symbolSize: 20,
                        effects: [
                            {
                                on: 'hover',
                                style: {
                                    itemOpacity: 1
                                }
                            }
                        ],
                        toggleSerie: true
                    }
                ]}
                pixelRatio={2}
                role='application'
                tooltip={({ id, value, indexValue, color }) => {
                    return <div style={{ backgroundColor: 'rgba(68,68,68, 0.9)', padding: '5px', color: '#ffffff', fontSize: '1rem', borderRadius: '4px' }}>
                        <div style={{ display: 'flex' }}>
                            <svg width='20px' height='20px' style={{ marginRight: '5px' }}><rect width='20px' height='20px' fill={color}></rect></svg>
                            <span style={{ fontSize: '1.1rem' }}>{id}</span>
                        </div>
                        Printed square meters: <b>{value}</b>
                        <br />
                        Date: <b>{indexValue}</b>
                    </div>
                }}
            />
        </>
    );
};

export default MediaCategoryUsageBarChart;
