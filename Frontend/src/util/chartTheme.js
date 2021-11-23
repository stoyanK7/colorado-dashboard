function colors(id, maxId, type) {
    let colorList;
    switch (type) {
        case 'ink-usage':
            colorList = [
                "#333333", // Black
                "#00FFFF", // Cyan
                "#FF00FF", // Magenta
                "#f7f700" // Yellow
            ];
            break;
        default: return `hsl(${id / maxId * 350}, 100%, 70%)`;
    };
    return colorList[id];
};

export default function chartTheme(data, id, indexValue, type) {
    // TODO: seperate into another function?
    let keys = Object.keys(data);
    // Remove index from keys
    keys.splice(keys.indexOf(keys.find(key => data[key] === indexValue)), 1);
    keys.sort();
    // TODO: not really clear what this does
    return colors(keys.indexOf(id), keys.length - 1, type);
};
