import React, { Component } from 'react'

import ReactMapboxGl from 'react-mapbox-gl'
import { MapMenu, Map3D } from './'

import { NODE_LAYER, EDGE_LAYER, BUILDING_3D_LAYER } from './Constants'

import './style.scss'

const Mapbox = ReactMapboxGl({
  accessToken: 'pk.eyJ1IjoiYW5kcmVrcmlzdGFudG8xNyIsImEiOiJjamI3dHBybDAzY21pMndvNmd1a3B0OW11In0.IudU2V7rmKm31CdJKx4n-w'
})

/**
 * Map component is the core for Mapbox component
 * @method onResetBearing to reset the bearing of the map
 * @method onResetPitch to reset the pitch of the map
 * @param mapStyle the map style
 * @param mode3D allows changing from 2D to 3D
 */
class Map extends Component {
  constructor (props) {
    super(props)
    if (props.onMapRef) props.onMapRef(this)
    this.state = {
      center: {
        lat: 1.29027,
        lng: 103.851959
      },
      zoomLevel: 14,
      pitch: 0,
      bearing: 0
    }
  }

  componentWillReceiveProps (nextProps) {
    if (!this.props.mode3D && nextProps.mode3D) this.renderLayer3D()
    if (this.props.mode3D && !nextProps.mode3D) this.removeLayer3D()
  }

  // It provides the interface to change the current state of the map
  onMapMove = map => {
    const payload = {
      zoomLevel: map.getZoom(),
      center: map.getCenter(),
      bearing: map.getBearing(),
      pitch: map.getPitch()
    }
    this.setState({ ...payload })
  }

  onResetPitch = () => {
    this.setState({ pitch: 0 })
  }

  onResetBearing = () => {
    this.setState({ bearing: 0 })
  }

  renderOverlay = () => {
    return (
      <div className={'map-overlay-container ' + this.props.overlayClassName || ''}>
        {this.props.renderOverlay && this.props.renderOverlay()}
      </div>
    )
  }

  renderLayer3D = () => {
    if (this.map) {
      console.log('add layer')
      Map3D({ map: this.map, ...this.props.data })
    }
  }

  removeLayer3D = () => {
    if (this.map) {
      console.log('remove layer')
      if (this.map.getLayer(BUILDING_3D_LAYER)) this.map.removeLayer(BUILDING_3D_LAYER)
      if (this.map.getLayer(NODE_LAYER)) this.map.removeLayer(NODE_LAYER)
      if (this.map.getLayer(EDGE_LAYER)) this.map.removeLayer(EDGE_LAYER)
    }
  }

  renderMap = () => {
    const { zoomLevel, bearing, pitch, center } = this.state
    const { mapStyle, mode3D, children, ...optionalMapProps } = this.props
    const Props = {
      ...optionalMapProps,
      zoom: [zoomLevel],
      bearing: [bearing],
      pitch: [pitch],
      onMove: this.onMapMove,
      center: [center.lng, center.lat],
      style: mapStyle,
      containerStyle: {
        position: 'absolute',
        top: 0,
        bottom: 0,
        height: '100%',
        width: '100%',
        ...this.props.style
      }
    }
    return (
      <Mapbox
        {...Props}
        onStyleLoad={map => {
          this.map = map
        }}
      >
        {!mode3D && children}
      </Mapbox>
    )
  }

  render () {
    // It provides for adding additional Map property and filter out the
    // property that we do not want to be added to the Mapbox Component
    // to avoid unnecessary error
    const { children } = this.props
    const MainMap = this.renderMap
    return (
      <div>
        <MainMap>{children}</MainMap>
        {this.renderOverlay()}
      </div>
    )
  }
}

export default Map
