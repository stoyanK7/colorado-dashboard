import { Route, Switch } from 'react-router-dom';

import DataPipelineErrors from './DataPipelineErrors';
import Home from './Home';
import View from './View';

const App = () => {
  return (
    <Switch>
      <Route exact path='/'>
        <Home />
      </Route>
      <Route exact path='/DataPipelineErrors'>
        <DataPipelineErrors />
      </Route>
      <Route exact path='/:chartPath'>
        <View />
      </Route>
      <Route exact path='/:chartPath/*'>
        <View />
      </Route>
    </Switch>
  );
};

export default App;
