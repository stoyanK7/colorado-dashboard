function ChartTheme( {data, id, indexValue} ) {
    let keys = Object.keys(data)
    // Remove index from keys
    keys.splice(keys.indexOf(keys.find(key => data[key] === indexValue)), 1)
    keys.sort();
    function colors(id, maxId) {
        return `hsl(${id / maxId * 350}, 100%, 70%)`;
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
    
    return colors(keys.indexOf(id), keys.length-1);
}
export default ChartTheme;