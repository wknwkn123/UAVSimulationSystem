import React from 'react'
import { render } from 'react-dom'

import { createStore } from 'redux'
// import createSagaMiddleware from 'redux-saga'

import reducer from './reducers/rootReducer'
// import logger from 'redux-logger'

import Root from './Root'
import { BrowserRouter } from 'react-router-dom'

import 'semantic-ui-css/semantic.min.css'
// const sagaMiddleware = createSagaMiddleware()
const store = createStore(
  reducer,
  // applyMiddleware(logger),
)

render((
  <BrowserRouter>
    <Root store={store}/>
  </BrowserRouter>
),
  document.getElementById('root')
)