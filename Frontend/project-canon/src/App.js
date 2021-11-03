import './App.css';
import MediaCategoryBarChart from './components/MediaCategoryBarChart';

function App() {
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
    },
    {
        "date": "31-12-2000",
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
        "date": "31-12-2001",
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
        "date": "31-12-2002",
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
        "date": "31-12-2003",
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
        "date": "31-12-2004",
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
  return (
    <>
      <div style={{height: "100vh"}}>
        <MediaCategoryBarChart {...{data: data, keys: keys, index:"date"}} />
      </div>
    </>
  );
}

export default App;
