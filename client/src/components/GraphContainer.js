import React, { Component } from 'react'
import { connect } from 'react-redux'
import * as style from './style'

import { Segment, Divider } from 'semantic-ui-react'

import Graph from './common/Graph'

class GraphContainer extends Component {
    render() {
        const upTrending = { up: true, value: 1000 }
        const downTrending = { up: false, value: 1000 }
        return (
            <Segment style={style.GraphContainer}>
                <Graph title='No of UAVS' trending={upTrending} />
                <Graph title='Avg Speed' trending={upTrending} />
                <Graph title='Avg Dist' trending={downTrending} />
                <Graph title='Throughput' trending={upTrending} />
                <Graph title='Risk' trending={upTrending} />
                <Graph title='Revenue' trending={upTrending} />
                <Graph title='Cost' trending={upTrending} />
            </Segment>
        )
    }
}

const mapStateToProps = (state) => {
    return {}
}

export default connect(mapStateToProps)(GraphContainer)