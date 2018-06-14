import React, { Component } from 'react'

import { Segment, Header, Container } from 'semantic-ui-react'

import * as style from '../style'

import { Popup } from 'react-mapbox-gl'

class InfoPopup extends Component {

    render() {
        const { data } = this.props
        if (data === null) return null
        return (
            <Popup
                onMouseLeave={this.props.onMouseLeave}
                key={data.name + "pop-up"}
                coordinates={data.lngLat}>
                <Segment style={style.PopUpContainer}>
                    <Container
                        style={style.PopUpInfo}>
                        <Header as='h5' style={{ margin: 0 }}>{data.name}</Header>
                        {data.infoProps.map((value) => {
                            return (
                                <p key={value} style={{ margin: 0 }}>
                                    {value}: {data[value]}
                                </p>
                            )
                        })}
                    </Container>
                </Segment>
            </Popup>
        )
    }

}

export default InfoPopup