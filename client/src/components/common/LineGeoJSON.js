import React, { Component } from 'react'

import * as style from '../style'

import { GeoJSONLayer } from 'react-mapbox-gl'

class LineGeoJSON extends Component {

    render() {
        const { color, data } = this.props
        return (
            <GeoJSONLayer
                data={data}
                lineLayout={{
                    "line-join": "round",
                    "line-cap": "round"
                }}
                linePaint={{
                    "line-color": color,
                    "line-width": 1,
                    "line-opacity": 0.2,
                }} />
        )
    }
}

export default LineGeoJSON