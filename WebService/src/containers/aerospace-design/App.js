import React, { Component } from 'react'
import { connect } from 'react-redux'

import MapContainer from './MapContainer'
import EditorPanel from './EditorPanel'

import './style.scss'
class App extends Component {
  render () {
    return (
      <div className='app-container'>
        <EditorPanel />
        <MapContainer />
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {}
}

const actions = {}
export default connect(mapStateToProps, actions)(App)
