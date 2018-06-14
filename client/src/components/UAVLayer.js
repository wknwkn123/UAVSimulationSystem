import React, { Component } from 'react'
import { connect } from 'react-redux'

import { Container } from 'semantic-ui-react'

import * as style from './style'
import * as action from '../actions/uav'

import { GeoJSONLayer } from 'react-mapbox-gl'

import CircleGeoJSON from './common/CircleGeoJSON'
import { COLOR, RADIUS } from './common/Constant'

import InfoPopup from './common/InfoPopup'

import UAV from '../models/UAV'

const InnerCirlce = ({ data, onMouseEnter }) => (
    <CircleGeoJSON
        onMouseEnter={({ features }) => onMouseEnter(features[0].properties.obj)}
        data={data}
        color={COLOR.UAV} radius={RADIUS.UAV_CIRCLE} opacity={1} />
)

const OuterCircle = ({ data, color }) => (
    <CircleGeoJSON
        data={data}
        color={color} radius={RADIUS.BIG_CIRLCE} opacity={.2} />
)

class UAVLayer extends Component {

    constructor(props) {
        super(props)

        const { alertedUAV_GeoJSON, nonAlertedUAV_GeoJSON } = UAV.separatedGeoJSON(this.props.uav.info)
        this.state = {
            alertedUAV_GeoJSON,
            nonAlertedUAV_GeoJSON,
            canUpdate: true
        }

        setInterval(() => this.setState({ canUpdate: true }), 500)
    }

    componentWillReceiveProps(nextProps) {
        if (!this.state.canUpdate) return
        const { info } = nextProps.uav
        if (this.props.uav.info !== info) {
            // console.log(this.props.uav.info, info)
            const { alertedUAV_GeoJSON, nonAlertedUAV_GeoJSON } = UAV.separatedGeoJSON(info)
            this.setState({
                alertedUAV_GeoJSON,
                nonAlertedUAV_GeoJSON,
                canUpdate: false
            })
        }
    }

    render() {
        const { alertedUAV_GeoJSON, nonAlertedUAV_GeoJSON } = this.state
        const { clickedUAV } = this.props.uav
        return [
            <InnerCirlce
                onMouseEnter={(uav) => this.props.onClickUAVInfo(new UAV(JSON.parse(uav)))}
                key="uav-alerted-inner-cirlce"
                data={alertedUAV_GeoJSON} />,
            <OuterCircle
                key="uav-alerted-outer-circle"
                data={alertedUAV_GeoJSON}
                color={COLOR.RED} />,
            <InnerCirlce
                onMouseEnter={(uav) => this.props.onClickUAVInfo(new UAV(JSON.parse(uav)))}
                key="uav-unalerted-inner-cirlce"
                data={nonAlertedUAV_GeoJSON} />,
            <OuterCircle
                key="uav-unalerted-outer-circle"
                data={nonAlertedUAV_GeoJSON}
                color={COLOR.YELLOW} />,
            <InfoPopup
                onMouseLeave={() => this.props.onClickUAVInfo(null)}
                key="uav-popup"
                data={clickedUAV ? this.props.uav.info.find(x => x.id === clickedUAV.id) : clickedUAV} />
        ]
    }

}

const mapStateToProps = (state) => {
    const { uav } = state
    return { uav }
}

export default connect(mapStateToProps, action)(UAVLayer)