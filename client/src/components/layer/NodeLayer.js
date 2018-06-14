import React, { Component } from 'react'

import { GeoJSONLayer } from 'react-mapbox-gl'
import PopupLayer from './PopupLayer'

class NodeLayer extends Component {
  static RADIUS = {
    BIG_CIRLCE: {
      base: 1.75,
      stops: [[12, 10], [14, 30], [15, 50], [16, 100]]
    },
    SMALL_CIRCLE: {
      base: 1.75,
      stops: [[12, 0], [14, 15], [15, 25], [16, 50]]
    },
    UAV_CIRCLE: {
      base: 1.75,
      stops: [[10, 2], [12, 4], [14, 5], [15, 6], [16, 7]]
    }
  }

  handleLayer = type => ({ features }) => {
    const interaction = this.props[type]
    interaction && interaction(features[0].properties.id)
  }

  render () {
    const {
      opacity = 1,
      radius = 5,
      data,
      color = {
        property: 'color',
        type: 'identity'
      }
    } = this.props
    return (
      <GeoJSONLayer
        circleOnClick={this.handleLayer('onClick')}
        circleOnMouseEnter={this.handleLayer('onMouseEnter')}
        data={data}
        circlePaint={{
          'circle-radius': radius,
          'circle-color': color,
          'circle-opacity': opacity
        }}
      />
    )
  }
}

export default NodeLayer
