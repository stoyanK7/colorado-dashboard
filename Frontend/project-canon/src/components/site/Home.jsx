import { Link } from "react-router-dom";
import classes from "./../../css/home_page.module.css";
import Chart from "../shared/Chart";

const Home = () => {
  return (
    <div className={classes.home}>
      <div className={classes.container}>
        <div className={classes.headerHome}>Colorado</div>
        <div className={`${classes.mainGraph} ${classes.graph}`}>
        <div className={classes.headerGraph}>Printed square metres per media type</div>
          <div className={classes.image}></div>
        </div>
        <div className={`${classes.sideGraphtop} ${classes.graph}`}>
        <div className={classes.headerGraph}>Amount of printed square metres per print mode</div>
          <div className={classes.image}></div>
        </div>
        <div className={`${classes.sideGrapthMiddle} ${classes.graph}`}>
        <div className={classes.headerGraph}>Ink usage</div>
          <div className={classes.image}></div>
        </div>
        <div className={`${classes.secondLineLeftGraph} ${classes.graph}`}>
        <div className={classes.headerGraph}> Best printing machines on the market</div>
          <div className={classes.image}></div>
        </div>
        <div className={`${classes.secondLineRightGraph} ${classes.graph}`}>
        <div className={classes.headerGraph}>All used media types for a selected machine</div>
          <div className={classes.image}></div>
        </div>
      </div>
    </div>
  );
};

export default Home;
