import React from 'react'
import PropTypes from 'prop-types'
import { Provider } from 'react-redux'

import App from './components/App'

import { Switch, Route } from 'react-router-dom'

const Root = ({ store }) => (
  <Provider store={store}>
    <Switch>
      <Route exact path='/' render={(props) => <App {...props} />} />
    </Switch>
  </Provider>
)

Root.propTypes = {
  store: PropTypes.object.isRequired
}

export default Root