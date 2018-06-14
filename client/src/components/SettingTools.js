import React, { Component } from 'react'
import { connect } from 'react-redux'

import { Container, Button, Menu, Icon, Popup } from 'semantic-ui-react'

import * as style from './style'
import * as action from '../actions/map'

import MapSetting from './MapSettings'

class SettingTools extends Component {

    state = {
        accountSetting: false,
        mode3D: false,
        mapSetting: false
    }

    render() {
        const { accountSetting, mapSetting } = this.state
        const { mode3D } = this.props.map
        return [
            <Menu key="menu" style={style.MapSettingTools}
                compact
                size="mini"
                vertical
                icon='labeled'>

                <Menu.Item name='Account'
                    active={accountSetting}
                    style={style.COMPACT.MENU_ITEM}
                >
                    <Icon name='user circle' />
                    Account
                </Menu.Item>

                <Menu.Item name='3D'
                    active={mode3D}
                    style={style.COMPACT.MENU_ITEM}
                    onClick={this.props.toogle3DMode}
                >
                    <Icon name='eye' />
                    3D Mode
                </Menu.Item>

                <Menu.Item name='Setting'
                    active={mapSetting}
                    style={style.COMPACT.MENU_ITEM}
                    onClick={() => this.setState({ mapSetting: !this.state.mapSetting })}
                >
                    <Icon name='setting' />
                    Setting
                </Menu.Item>
            </Menu>,
            <MapSetting show={mapSetting} key="map setting" />
        ]
    }
}

const mapStateToProps = (state) => {
    const { map } = state
    return { map }
}

export default connect(mapStateToProps, action)(SettingTools)