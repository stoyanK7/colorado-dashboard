// Formats date from 2021-11-20 to Nov 20, 2021
const formatDateTime = (dateTime) => {
  if (!(dateTime instanceof Date)) dateTime = new Date(dateTime);
  const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
  return dateTime.toLocaleDateString('en-GB', options);
};

export default formatDateTime;
