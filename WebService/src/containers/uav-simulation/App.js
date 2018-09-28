import React, { Component } from 'react'
import { connect } from 'react-redux'

import * as fileActions from '../../actions/file'

import SampleGraph from '../../assets/graph/sampleGraph'

import MapContainer from './MapContainer'
import SidebarPanel from './SidebarPanel'
import UAV_Websocket from './UAVWebsocket'

import './style.scss'
class App extends Component {
  componentDidMount () {
    this.props.loadGraph({graph: SampleGraph.graph})
  }

  render () {
    return (
      <div className='app-container'>
        <SidebarPanel />
        <MapContainer />
        <UAV_Websocket />
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {}
}

const actions = { ...fileActions }
export default connect(mapStateToProps, actions)(App)
