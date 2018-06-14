import React, { Component } from 'react'

import * as style from '../style'

import { GeoJSONLayer } from 'react-mapbox-gl'

class CircleGeoJSON extends Component {

    render() {
        const { color, opacity, radius, data } = this.props
        return (
            <GeoJSONLayer
                circleOnMouseEnter={({ features }) => {
                    if (this.props.onMouseEnter)
                        this.props.onMouseEnter({ features })
                }}
                data={data}
                circlePaint={{
                    "circle-radius": radius,
                    "circle-color": color,
                    "circle-opacity": opacity ? opacity : 1
                }} />
        )
    }
}

export default CircleGeoJSON