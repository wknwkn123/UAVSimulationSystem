import React, { Component } from 'react'
import { connect } from 'react-redux'

import * as uav from '../actions/uav'
import * as param from '../actions/param'

import MessageParser from '../models/MessageParser'

const PLANAR_GRAPH = 'PLANARGRAPH'
const RANDOM = 'RANDOM'
const START_SIMULATION_JSON = {
    simulationStart: 'start',
    simulationParameter: {
        airspaceType: PLANAR_GRAPH,
        flightScheduleType: RANDOM
    }
}

const STOP_SIMULATION = {
    simulationStart: 'stop'
}

const CONTINUE_SIMULATION = {
    simulationStart: 'continue'
}

class WS_UAV extends Component {
    state = { ws: null }

    setupWebsocket = () => {
        let { ws } = this.state

        ws.onopen = () => {
            this.props.onWSConnected()
        }

        ws.onclose = () => {
            this.props.onWSDisconnected()
            const waitingTime = 1000  // in ms
            setTimeout(this.connectWebsocket, waitingTime)
        }

        ws.onerror = (error) => { }


        async function doFormatData(event) {
            return new Promise((resolve, reject) => {
                const data = JSON.parse(event.data)
                if (data.coordinate) {
                    var uav = new MessageParser(data).UAV
                    resolve(uav)
                }
            })
        }

        ws.onmessage = (event) => {
            if(this.state.didStop) return
            doFormatData(event).then(uav => this.props.onAddUAV(uav))
        }
    }

    connectWebsocket = () => {
        const wsURL = 'ws://localhost:9000'
        const test = 'https:ws//316d33b2.ngrok.io/'

        this.setState({
            ws: new WebSocket(wsURL),
            didStop: false
        }, this.setupWebsocket)
    }

    componentWillReceiveProps(nextProps) {
        if (!nextProps.param.stop && this.props.param.stop) {
            // this.props.onSentParam()
            this.setState({didStop: false})
            var data = JSON.stringify({ ...START_SIMULATION_JSON, simulationParameter: nextProps.param.data })
            console.log(data)
            try { this.state.ws.send(data) } catch (err) { console.log(err) }
        }

        if (nextProps.param.stop && !this.props.param.stop) {
            // this.props.onStopSimulation()
            this.setState({didStop: true})
            var data = JSON.stringify(STOP_SIMULATION)
            console.log(data)
            try { this.state.ws.send(data) } catch (err) { console.log(err) }
        }
    }

    componentWillUnmount() {
        if (this.state.ws) {
            this.state.ws.send(JSON.stringify(STOP_SIMULATION))
            this.setState({ ws: null })
        }
    }

    componentDidMount() {
        this.connectWebsocket()
        // console.log('did mount')
        // this.props.startSimulation()
    }

    render() {
        return null
    }
}

const mapStateToProps = (state) => {
    const { time, param } = state
    return { time, param }
}

export default connect(mapStateToProps, { ...uav, ...param })(WS_UAV) 
