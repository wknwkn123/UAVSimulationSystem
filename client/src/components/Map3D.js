import React, { Component } from 'react'
import { connect } from 'react-redux'

import { Container } from 'semantic-ui-react'

import * as style from './style'
import * as action from '../actions/map'

import ReactMapboxGl, { Source, Layer } from 'react-mapbox-gl'
import UAVLayer from './UAVLayer'
import GraphLayer from './GraphLayer'
import Point from '../models/Point'
import Edge from '../models/Edge'

import GeoJSON_3D from '../models/GeoJSON_3D'

import FileTools from './FileTools'
import SettingTools from './SettingTools'
import ATC_Table from './ATC_Table'
import GraphContainer from './GraphContainer'

const Mapbox = ReactMapboxGl({
    accessToken: 'pk.eyJ1IjoiYW5kcmVrcmlzdGFudG8xNyIsImEiOiJjamI3dHBybDAzY21pMndvNmd1a3B0OW11In0.IudU2V7rmKm31CdJKx4n-w'
})

const layer = (source, opacity) => ({
    'id': source + 'room-extrusion',
    'type': 'fill-extrusion',
    'source': source,
    'paint': {
        'fill-extrusion-color': {
            // Get the fill-extrusion-color from the source 'color' property.
            'property': 'color',
            'type': 'identity'
        },
        'fill-extrusion-height': {
            // Get fill-extrusion-height from the source 'height' property.
            'property': 'height',
            'type': 'identity'
        },
        'fill-extrusion-base': {
            // Get fill-extrusion-base from the source 'base_height' property.
            'property': 'base_height',
            'type': 'identity'
        },
        // Make extrusions slightly opaque for see through indoor walls.
        'fill-extrusion-opacity': {
            // Get fill-extrusion-base from the source 'base_height' property.
            'property': 'opacity',
            'type': 'identity'
        }
    }
})

class Map extends Component {


    addGraphSource = (map, graph) => {
        if (graph === null) return
        const { nodesGeoJSON_3D, edgesGeoJSON_3D } = graph.geoJSON_3D
        map.addSource('node', {
            type: 'geojson',
            data: nodesGeoJSON_3D
        })

        map.addSource('edge', {
            type: 'geojson',
            data: edgesGeoJSON_3D
        })

        // console.log(JSON.stringify(nodesGeoJSON_3D))
        // console.log(JSON.stringify(edgesGeoJSON_3D))

        map.addLayer(layer('node', 1))
        map.addLayer(layer('edge', 0.5))
    }

    addBuildingSource = (map) => {
        var layers = map.getStyle().layers;
        var labelLayerId;
        for (var i = 0; i < layers.length; i++) {
            if (layers[i].type === 'symbol' && layers[i].layout['text-field']) {
                labelLayerId = layers[i].id;
                break;
            }
        }
        map.addLayer({
            'id': '3d-buildings',
            'source': 'composite',
            'source-layer': 'building',
            'filter': ['==', 'extrude', 'true'],
            'type': 'fill-extrusion',
            'minzoom': 15,
            'paint': {
                'fill-extrusion-color': '#aaa',
                'fill-extrusion-height': [
                    "interpolate", ["linear"], ["zoom"],
                    15, 0,
                    15.05, ["get", "height"]
                ],
                'fill-extrusion-base': [
                    "interpolate", ["linear"], ["zoom"],
                    15, 0,
                    15.05, ["get", "min_height"]
                ],
                'fill-extrusion-opacity': .6
            }
        }, labelLayerId)
        console.log(labelLayerId)
    }

    componentWillReceiveProps(nextProps) {
        const { mapStyle, graph } = nextProps.map
        const { map } = this
        // console.log('next porps', graph)
        // if (this.props.map.mapStyle !== mapStyle) {
        //     console.log('add source and building')
        //     this.addGraphSource(map, graph)
        //     this.addBuildingSource(map)
        // }

        if (graph !== this.props.map.graph) {
            if (map.getSource('node') && map.getSource('edge')) {
                console.log('set source')
                const { nodesGeoJSON_3D, edgesGeoJSON_3D } = graph.geoJSON_3D
                map.getSource('node').setData(nodesGeoJSON_3D)
                map.getSource('edge').setData(edgesGeoJSON_3D)
            } else {
                console.log('add source')
                this.addGraphSource(map, graph)
            }
        }
    }

    render() {
        const { center, brightness, zoomLevel, pitch, bearing, mapStyle, graph } = this.props.map
        const { lat, lng } = center
        return (
            <Container style={{ ...style.MapContainer, filter: 'brightness('+brightness+"%)" }}>
                <Mapbox
                    ref={x => this.map = x}
                    onStyleLoad={(map) => {
                        map.on('style.load', () => {
                            this.addBuildingSource(map)
                            this.addGraphSource(map, graph)
                        })
                        this.addBuildingSource(map)
                        this.addGraphSource(map, graph)
                    }}
                    zoom={[zoomLevel]}
                    bearing={[bearing]}
                    pitch={[pitch]}
                    center={[lng, lat]}
                    onMove={(map) => { this.props.shouldChangeRegion({ map }) }}
                    style={mapStyle.value}
                    containerStyle={{
                        width: '100%',
                        height: '100%'
                    }}>
                </Mapbox>
                <FileTools />
                <ATC_Table />
                <SettingTools />
                <GraphContainer />
            </Container>
        )
    }

}

const mapStateToProps = (state) => {
    const { map, uav } = state
    return { map, uav }
}

export default connect(mapStateToProps, action)(Map)