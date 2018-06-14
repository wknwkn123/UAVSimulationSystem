import React, { Component } from 'react'

import * as style from '../style'

import { Container, Icon, Header, Segment } from 'semantic-ui-react'

import { LineChart, Tooltip, Line, Legend, XAxis, YAxis, CartesianGrid, Label } from 'recharts'

const data = [
    { name: 'A', uv: 590, pv: 800, amt: 1400 },
    { name: 'B', uv: 868, pv: 967, amt: 1506 },
    { name: 'C', uv: 1397, pv: 1098, amt: 989 },
    { name: 'D', uv: 1480, pv: 1200, amt: 1228 },
    { name: 'E', uv: 1520, pv: 1108, amt: 1100 },
    { name: 'F', uv: 1400, pv: 680, amt: 1700 }
]

class Graph extends Component {
    render() {
        const { trending, title } = this.props
        return (
            <Container style={style.COMPACT.GRAPH}>
                <Container style={{ width: 80, margin: 0 }}>
                    <p style={{ width: 80, color: 'white', margin: 0, fontSize: '1em' }}>
                        {title}
                    </p>
                    <p style={{ fontSize: '0.8em', marginTop: 10 }}>
                        {trending.up ?
                            <Icon size="large" color="green"
                                name="arrow up" />
                            :
                            <Icon size="large" color="red"
                                name="arrow down" />
                        }
                        {trending.value}
                    </p>
                </Container>
                <Segment style={{
                    width: 90, height: 60, background: 'transparent', padding: 0, margin: 0,
                    borderColor: 'white', borderWidth: 1
                }}>
                    <LineChart width={90} height={60}
                        style={{ fontSize: '0.875em', marginLeft: 0 }}
                        data={data}>
                        <Tooltip />
                        <CartesianGrid stroke="#ccc" strokeDasharray="2 2" />
                        <Line type="linear" dataKey="uv" stroke="#ffffff" dot={false} />
                        {/* <XAxis> */}
                        {/* <Label value={xLabel} offset={0} position="insideBottom" /> */}
                        {/* </XAxis> */}
                        {/* <YAxis> */}
                        {/* <Label value={yLabel} offset={0} position="insideBottomLeft" /> */}
                        {/* </YAxis> */}
                        {/* <Legend verticalAlign="top" height={36} /> */}
                    </LineChart>
                </Segment>
            </Container>

        )
    }
}

export default Graph