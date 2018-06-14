import React, { Component } from 'react'
import { connect } from 'react-redux'

import { Container, Dropdown, Header, Divider, Segment, Checkbox } from 'semantic-ui-react'

import BrightnessSlider from './common/SliderLabeled'
import ParamRow from './ParamRow'

import * as style from './style'
import * as action from '../actions/map'

import MapboxStyle from '../models/MapboxStyle'

class MapSetting extends Component {

    state = {
        style: null,
        options: MapboxStyle.getOptions()
    }

    render() {
        if (!this.props.show) return null
        return (
            <Container style={style.MenuPopUpContainer}>
                <Header as='h3' style={style.MenuPopUpHeader}>Map Setting</Header>
                <Segment style={style.MenuPopUpContent} >
                    <ParamRow left="Map Terrain"
                        right={
                            <Dropdown
                                fluid
                                scrolling
                                search
                                selection
                                closeOnChange
                                onChange={(event, { value }) => {
                                    this.props.onChangeMapTerrain({ style: new MapboxStyle(value) })
                                }}
                                value={this.props.map.mapStyle.style}
                                options={this.state.options}
                                placeholder={"Please select style"} />
                        }
                    />
                    <Divider />
                    <ParamRow left="Brightness"
                        right={
                            <BrightnessSlider
                                defaultValue={this.props.map.brightness}
                                min={0}
                                max={100}
                                onChange={(value) => {
                                    // console.log(value)
                                    this.props.onChangeMapBrightness(value)
                                }}
                            />
                        }
                    />

                </Segment>
            </Container>
        )
    }
}

const mapStateToProps = (state) => {
    const { map } = state
    return { map }
}

export default connect(mapStateToProps, action)(MapSetting)