import { Route, Switch } from 'react-router-dom';

import Home from './Home';
import View from './View';

const App = () => {
  return (
    <Switch>
      <Route exact path='/'>
        <Home />
      </Route>
      <Route exact path='/:chart'>
        <View />
      </Route>
    </Switch>
  );
};

export default App;