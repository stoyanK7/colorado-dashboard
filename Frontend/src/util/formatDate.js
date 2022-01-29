// Formats date from 2021-11-20 to Nov 20, 2021
const formatDate = (date) => {
  if (!(date instanceof Date)) date = new Date(date);
  const options = { year: 'numeric', month: 'short', day: 'numeric' };
  return date.toLocaleDateString('en-US', options);
};

export default formatDate;
