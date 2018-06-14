import React, { Component } from 'react'
import { connect } from 'react-redux'
import proj4 from 'proj4'
import * as uavAction from '../../actions/uav'

import { Websocket as CustomWebsocket } from '../../components/websocket'

import * as CONSTANT from './Constants'

const lngLat = ({ x, y }) => {
  proj4.defs(
    'EPSG:3414',
    '+proj=tmerc +lat_0=1.366666666666667 +lon_0=103.8333333333333 +k=1 +x_0=28001.642 +y_0=38744.572 +ellps=WGS84 +units=m +no_defs'
  )
  var proj = proj4('EPSG:3414', 'EPSG:4326', [x, y])
  return {
    lng: parseFloat(proj[0].toFixed(6)),
    lat: parseFloat(proj[1].toFixed(6))
  }
}

class UAV_Websocket extends Component {
  state = { ws: null }

  componentDidMount () {
    this.setState(
      {
        ws: new CustomWebsocket({
          onMessage: this.parseMessage,
          url: CONSTANT.URL
        })
      },
      () => this.state.ws.start()
    )
    // setTimeout(() => this.sendStartParam(), 2000)
    // setTimeout(() => this.sendStopParam(), 4000)
  }

  componentWillReceiveProps (nextProps) {
    const { data, type } = nextProps.param
    switch (type) {
      case 'start':
        this.sendStartParam()
        break
      case 'stop':
        this.sendStopParam()
        break
      default:
        break
    }
  }

  sendStartParam = (config = {}) => {
    const param = { ...CONSTANT.START_SIMULATION_JSON, ...config }
    console.log('sending start to ws', param)
    this.state.ws.send(param)
  }

  sendStopParam = (config = {}) => {
    const param = { ...CONSTANT.STOP_SIMULATION, ...config }
    console.log('sending stop to ws', param)
    this.state.ws.send(param)
  }

  parseMessage = event => {
    const data = JSON.parse(event.data)
    if (data.constructor === Array) {
      let validUAV = data.reduce((acc, value) => {
        if (value.coordinate && value.coordinate.coor) {
          let coor = value.coordinate.coor
          acc.push({
            id: value.id,
            position: lngLat({ x: parseFloat(coor[0], 6), y: parseFloat(coor[1], 6) }),
            alert: false,
            progress: '10',
            battery: '100%',
            revenue: 100
          })
        }
        return acc
      }, [])
      this.props.onReceiveUAVList && this.props.onReceiveUAVList(validUAV)
    }
  }

  render () {
    return null
  }
}

const mapStateToProps = state => {
  const { param } = state.uav
  return { param }
}

const actions = { ...uavAction }
export default connect(mapStateToProps, actions)(UAV_Websocket)
