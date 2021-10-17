import { ResponsiveBar } from "@nivo/bar";
const MediaCategoryBarChart = (props) => {
    let data = [
        {
            "date": "31-12-1998",
            "Monomeric_vinyl": "6.0",
            "Light_Banner": "4.0",
            "Textile": "5.0",
            "Film": "1.0",
            "Canvas": "7.0",
            "Paper": "10.0",
            "Heavy_Banner": "9.0",
            "Light_paper": "2.0",
            "Thick_film": "11.0",
            "Heavy_paper": "3.0",
            "Polymeric_and_cast_vinyl": "8.0"
        },
        {
            "date": "31-12-1999",
            "Monomeric_vinyl": "6.0",
            "Light_Banner": "4.0",
            "Textile": "5.0",
            "Film": "1.0",
            "Canvas": "7.0",
            "Paper": "10.0",
            "Heavy_Banner": "9.0",
            "Light_paper": "2.0",
            "Thick_film": "11.0",
            "Heavy_paper": "3.0",
            "Polymeric_and_cast_vinyl": "8.0"
        }
    ];

    let keys = Object.keys(data[0]);
    keys.splice(keys.indexOf("date"), 1);
    keys.sort()

    function colorTheme(props) {
        let keys = Object.keys(props.data)
        // Remove index from keys
        keys.splice(keys.indexOf(keys.find(key => props.data[key] === props.indexValue)), 1)
        keys.sort();
        function colors(id, maxId) {
            return `hsl(${id / maxId * 360}, 100%, 70%)`;
            let colorList = [
                "#564138",
                "#2E86AB",
                "#F6F5AE",
                "#F5F749",
                "#F24236",
                "#B098A4 ",
                "#DD7373",
                "#CA895F",
                "#EE4266",
                "#0EAD69",
                "#D98324"
            ]
            return colorList[id];
        }
        
        return colors(keys.indexOf(props.id), keys.length-1);
    }

    return (
        <>
        <div style={{height: "100vh"}}>
        <ResponsiveBar
        data={data}
        keys={keys}
        indexBy="date"
        margin={{ top: 50, right: 130, bottom: 50, left: 60 }}
        padding={0.05}
        valueScale={{ type: 'linear' }}
        indexScale={{ type: 'band', round: true }}
        // colors={{ scheme: 'nivo' }}
        colors={colorTheme}
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
    />
        </div>
        </>
    )
}

export default MediaCategoryBarChart;