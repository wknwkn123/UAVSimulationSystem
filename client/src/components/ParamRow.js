import React, { Component } from 'react'

import { Container, Button } from 'semantic-ui-react'

import * as style from './style'

class ParamRow extends Component {

    render() {
        const { id, left, right, vertical } = this.props
        if (!vertical)
            return (
                <Container style={{ ...style.ContentRow }}>
                    <Container style={{ flex: 0.5, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                        {left}
                    </Container>
                    <Container style={{ flex: 2, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                        {right}
                    </Container>
                </Container >
            )
        else
            return (
                <Container style={{ ...style.ContentRow, flexDirection: 'column' }}>
                    {left}
                    {right}
                </Container >
            )
    }
}


export default ParamRow