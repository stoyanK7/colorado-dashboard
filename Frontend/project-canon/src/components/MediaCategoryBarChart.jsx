import { ResponsiveBar } from "@nivo/bar";
import ChartTheme from "./ChartTheme";
const MediaCategoryBarChart = ({data, index, keys}) => {
    keys.sort()

    return (
        <>
        <ResponsiveBar
        data={data}
        keys={keys}
        indexBy={index}
        margin={{ top: 50, right: 130, bottom: 50, left: 60 }}
        padding={0.05}
        valueScale={{ type: 'linear' }}
        indexScale={{ type: 'band', round: true }}
        colors={ChartTheme}
        borderWidth="0.5px"
        borderColor={{ from: 'color', modifiers: [ [ 'darker', 1.6 ] ] }}
        axisBottom={{
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
            legend: 'days',
            legendPosition: 'middle',
            legendOffset: 32
        }}
        axisLeft={{
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
            legend: 'usages',
            legendPosition: 'middle',
            legendOffset: -40
        }}
        labelSkipWidth={12}
        labelSkipHeight={12}
        labelTextColor={{ from: 'color', modifiers: [ [ 'darker', 10 ] ] }}
        legends={[
            {
                dataFrom: 'keys',
                anchor: 'bottom-right',
                direction: 'column',
                justify: false,
                translateX: 120,
                translateY: 0,
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
        role="application"
        tooltip={({id, value, indexValue}) => {
            return <div style={{background: "#111111", padding: "5px", color: "#ffffff"}}>
                <span>{id} - {indexValue}</span>
                <br/>
                <span>Usages: {value}</span>
            </div>
        }}
    />
        </>
    )
}

export default MediaCategoryBarChart;