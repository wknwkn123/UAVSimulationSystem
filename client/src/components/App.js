import React, { Component } from 'react'
import { connect } from 'react-redux'
import * as style from './style'

import { Sidebar, Container, Accordion, Icon, Button } from 'semantic-ui-react'

import ParamForm from './ParamForm'

import Map from './Map'
import Map3D from './Map3D'

import WebSocket from './Websocket'

import SampleGraph from '../assets/sampleGraph'
import Graph from '../models/Graph'
import * as action from '../actions/graph'

const PanelTitle = ({ icon, title }) => (
    <span>
        {icon && <Icon circular bordered name={icon} />}
        {title}
    </span>
)
const panels = [
    {
        title: {
            content:
                <PanelTitle
                    title="Simulation Configuration" />,
            key: 'input-title'
        },
        content: {
            content: <ParamForm />,
            key: 'vertex-content', style: { padding: 0 }
        }
    }
]

class App extends Component {
    state = { show: false }

    componentDidMount(){
        this.props.onLoadGraph({ graph: new Graph(SampleGraph) })
    }

    render() {
        const { show } = this.state
        const { mode3D } = this.props
        return (
            <Sidebar.Pushable as={Container} style={style.Root}>
                <Sidebar as={Container} animation='overlay'
                    style={style.LeftPanelContainer}
                    visible={show} icon='labeled'>
                    <Button
                        onClick={() => this.setState({ show: false })}
                        style={{ position: 'absolute', left: '23em', top: 8 }}
                        icon={show ? 'angle double left' : 'angle double right'} />
                    <Accordion
                        defaultActiveIndex={[0]}
                        panels={panels}
                        exclusive={false}
                        fluid />
                </Sidebar>
                <Sidebar.Pusher style={style.MainContainer}>
                    <Button
                        onClick={() => this.setState({ show: true })}
                        style={{ position: 'absolute', left: -8, top: 8, zIndex: 3 }}
                        icon={show ? 'angle double left' : 'angle double right'} />
                    {!mode3D ?
                         <Map />
                        :
                        <Map3D />
                    }
                    <WebSocket />
                </Sidebar.Pusher>
            </Sidebar.Pushable>
        )
    }
}

const mapStateToProps = (state) => {
    const { mode3D } = state.map
    return { mode3D }
}

export default connect(mapStateToProps, action)(App)