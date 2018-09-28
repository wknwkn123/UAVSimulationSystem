import React from 'react'
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom'
import { Provider } from 'react-redux'
import PropTypes from 'prop-types'

import Spinner from './Spinner'

import AerospaceDesign from './aerospace-design/App'
import UAVSimulation from './uav-simulation/App'

class App extends React.Component {
  static propTypes = {
    store: PropTypes.object.isRequired
  }

  shouldComponentUpdate () {
    return false
  }

  render () {
    return (
      <Provider store={this.props.store}>
        <BrowserRouter>
          <Switch>
            <Route exact path='/design' component={AerospaceDesign} />
            <Route exact path='/simulation' component={UAVSimulation} />
            <Redirect to='/design' />
          </Switch>
        </BrowserRouter>
      </Provider>
    )
  }
}

export default App
