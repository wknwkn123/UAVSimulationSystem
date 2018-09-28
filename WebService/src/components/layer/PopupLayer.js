import React, { Component } from 'react'

import { Segment, Header, Container } from 'semantic-ui-react'

import { Popup } from 'react-mapbox-gl'

import './style.scss'

class PopupLayer extends Component {
  onMouseLeave = () => {
    this.props.onMouseLeave && this.props.onMouseLeave()
  }

  render () {
    const { data } = this.props
    if (!data) return null
    const { lat, lng } = data.position
    return (
      <Popup onMouseLeave={this.onMouseLeave} coordinates={[lng, lat]}>
        <Segment className='info-popup-outer'>
          <Container className='info-popup-main'>
            <Header as='h4' style={{ margin: 0 }}>
              {data.id}
            </Header>
            {this.props.renderSubInfo && this.props.renderSubInfo()}
          </Container>
        </Segment>
      </Popup>
    )
  }
}

export default PopupLayer
