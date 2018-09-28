import React, { Component } from 'react'

import ReactMapboxGl from 'react-mapbox-gl'
import { MapMenu } from './'
import { DARK } from './Constants'

/**
 * withMapWrapper function is a wrapper function for Map component to add the menu setting to the WrappedComponent
 * This wrapper also controls the Map component mapStyle and mode3D props to the Map Component
 * @param {*} Map 
 */
const withMapWrapper = Map => {
  class MapWithSetting extends Component {
    state = {
      mode3D: false,
      brightness: 100,
      map_style: DARK
    }

    menuOnChange = (name, value) => {
      console.log('menu on change', name, value)
      switch (name) {
        case '3D':
          this.setState({ mode3D: !this.state.mode3D })
          break
        case 'brightness':
          this.setState({ brightness: value })
          break
        case 'map_style':
          this.setState({ map_style: value })
          break
        case 'reset_pitch':
          this.map.onResetPitch()
          break
        case 'reset_bearing':
          this.map.onResetBearing()
          break
        default:
          break
      }
    }

    onMapRef = map => {
      this.map = map
    }

    render () {
      const { children, ...optionalMapProps } = this.props
      const { brightness, mode3D, map_style } = this.state
      return (
        <Map
          {...optionalMapProps}
          style={{
            filter: 'brightness(' + brightness + '%)'
          }}
          mapStyle={map_style}
          onMapRef={this.onMapRef}
          mode3D={mode3D}
          renderOverlay={() => {
            let arr = []
            const overlay = this.props.renderOverlay && this.props.renderOverlay()
            if (overlay) {
              arr.push(overlay)
            }
            arr.push(<MapMenu {...this.state} key='menu' onChange={this.menuOnChange} />)
            return arr
          }}
        >
          {children}
        </Map>
      )
    }
  }
  return MapWithSetting
}

export default withMapWrapper
