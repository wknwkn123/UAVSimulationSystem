import React, { Component } from 'react'

import * as style from '../style'

import { GeoJSONLayer } from 'react-mapbox-gl'

class SymbolGeoJSON extends Component {

    render() {
        const { color, data } = this.props
        return (
            <GeoJSONLayer
                data={data}
                symbolLayout={{
                    "text-field": "{title}",
                    "text-font": ["Open Sans Regular"],
                    "text-size": 10,
                    "text-offset": [0, 0.6],
                    "text-anchor": "top",
                    "icon-allow-overlap": true,
                    "icon-ignore-placement": true,
                }}
                symbolPaint={{
                    "text-color": color
                }} />
        )
    }
}

export default SymbolGeoJSON