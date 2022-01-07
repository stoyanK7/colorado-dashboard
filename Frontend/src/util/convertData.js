// Convert data into readable format for Recharts
// No idea how it works, some indian guy on stackoverflow did it for me
// https://stackoverflow.com/questions/70522317/javascript-group-and-transform-array-of-objects-by-one-or-two-keys
const convertData = (data, fieldName) => {
  return Object.values(data.reduce((r, o) => {
    let key = o.Date + '-' + o['Printer id'];
    r[key] ??= { Date: o.Date, 'Printer id': o['Printer id'] };
    r[key][o[fieldName]] = (r[key][o[fieldName]] ?? 0) + o['Printed square meters'];
    return r;
  }, {}));
};

export default convertData;
