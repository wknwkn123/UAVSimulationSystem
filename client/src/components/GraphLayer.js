import React, { Component } from 'react'
import { connect } from 'react-redux'

import { Container } from 'semantic-ui-react'

import * as style from './style'
import * as action from '../actions/graph'

import SymbolGeoJSON from './common/SymbolGeoJSON'
import LineGeoJSON from './common/LineGeoJSON'
import CircleGeoJSON from './common/CircleGeoJSON'
import { COLOR, RADIUS } from './common/Constant'

import InfoPopup from './common/InfoPopup'

import Point from '../models/Point'

class GraphLayer extends Component {

    constructor(props) {
        super(props)

        this.state = {
            clicked: null,
            nodesGeoJSON: null,
            edgesGeoJSON: null
        }
    }

    componentDidMount() {
        if (this.props.graph) {
            const { nodesGeoJSON, edgesGeoJSON } = this.props.graph.geoJSON
            this.setState({
                nodesGeoJSON,
                edgesGeoJSON
            })
        }
    }

    componentWillReceiveProps(nextProps) {
        console.log(nextProps.graph)
        const { nodesGeoJSON, edgesGeoJSON } = nextProps.graph.geoJSON
        if (nextProps.graph !== this.props.graph) {
            this.setState({
                nodesGeoJSON,
                edgesGeoJSON
            })
        }
    }

    render() {
        const { clicked, nodesGeoJSON, edgesGeoJSON } = this.state
        if (!nodesGeoJSON || !edgesGeoJSON) return null
        return [
            <CircleGeoJSON
                onMouseEnter={({ features }) =>
                    this.setState({
                        clicked: new Point(JSON.parse(features[0].properties.obj))
                    })
                }
                key="nodes"
                data={nodesGeoJSON}
                color={COLOR.CYAN} radius={5} />,
            <SymbolGeoJSON
                key="nodes-text"
                data={nodesGeoJSON}
                color={COLOR.CYAN} />,
            <LineGeoJSON
                key="edges"
                data={edgesGeoJSON}
                color={COLOR.WHITE} />,
            // <InfoPopup
            //     onMouseLeave={() => this.setState({ clicked: null })}
            //     key="nodes-popup"
            //     data={clicked} />
        ]
    }
}

const mapStateToProps = (state) => {
    const { graph } = state.map
    return { graph }
}

export default connect(mapStateToProps, action)(GraphLayer)