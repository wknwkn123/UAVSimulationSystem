import React, { Component } from 'react'
import { connect } from 'react-redux'

import { Container } from 'semantic-ui-react'

import * as style from './style'
import * as action from '../actions/map'

import ReactMapboxGl from 'react-mapbox-gl'
import UAVLayer from './UAVLayer'
import GraphLayer from './GraphLayer'

import FileTools from './FileTools'
import SettingTools from './SettingTools'
import ATC_Table from './ATC_Table'
import GraphContainer from './GraphContainer'

const Mapbox = ReactMapboxGl({
    accessToken: 'pk.eyJ1IjoiYW5kcmVrcmlzdGFudG8xNyIsImEiOiJjamI3dHBybDAzY21pMndvNmd1a3B0OW11In0.IudU2V7rmKm31CdJKx4n-w'
})

class Map extends Component {

    render() {
        const { center, brightness, zoomLevel, pitch, bearing, mapStyle } = this.props.map
        const { lat, lng } = center
        return (
            <Container style={{ ...style.MapContainer, filter: 'brightness('+brightness+"%)" }}>
                <Mapbox
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
                    <UAVLayer />
                    <GraphLayer />
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
    const { map } = state
    return { map }
}

export default connect(mapStateToProps, action)(Map)