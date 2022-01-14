//  /media/stoyank/Elements/University/Semester 3/group-project-cannon/Airflow/logs/error_log1.txt
// --> error_log1.txt
const getFilenameFromPath = (str) => {
  return str.split('\\').pop().split('/').pop();
};

export default getFilenameFromPath;
