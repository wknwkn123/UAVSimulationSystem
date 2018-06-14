import React, { Component } from 'react'

import { GeoJSONLayer } from 'react-mapbox-gl'

class LineLayer extends Component {
  render () {
    const { color = '#00FF00', data } = this.props
    return (
      <GeoJSONLayer
        data={data}
        lineLayout={{
          'line-join': 'round',
          'line-cap': 'round'
        }}
        linePaint={{
          'line-color': color,
          'line-width': 1,
          'line-opacity': 0.2
        }}
      />
    )
  }
}

export default LineLayer
