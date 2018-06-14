import React, { Component } from 'react'

import { MAPBOX_STYLE_OPTIONS } from './Constants'
import SliderLabeled from './SliderLabeled'
import { Grid, Dropdown, Input, Segment, Header, Divider } from 'semantic-ui-react'

const MapStyleSettingList = [
  {
    name: 'Map Style',
    id: 'map_style',
    renderComponent: ({ onChange, value }) => {
      return (
        <Dropdown
          fluid
          scrolling
          search
          selection
          closeOnChange
          onChange={(event, { value }) => {
            onChange('map_style', value)
          }}
          value={value}
          options={MAPBOX_STYLE_OPTIONS}
        />
      )
    }
  },
  {
    name: 'Brightness',
    id: 'brightness',
    renderComponent: ({ value, onChange }) => {
      return (
        <SliderLabeled
          defaultValue={value}
          min={0}
          max={100}
          onChange={value => {
            onChange('brightness', value)
          }}
        />
      )
    }
  }
]

/**
 * MapStyleSetting component is a popup component which accomodates the interface
 * to change map style and brightness
 */
class MapStyleSetting extends Component {
  render () {
    const { className } = this.props
    return (
      <Segment className={'map-style-setting pop-up ' + (className || '')}>
        <Divider horizontal>Map Setting</Divider>
        <Grid columns={2}>
          {MapStyleSettingList.map((obj, i) => {
            const { name, id } = obj
            const CustomComponent = obj.renderComponent
            return (
              <Grid.Row key={i} className='grid-row'>
                <Grid.Column width={6}>{name}</Grid.Column>
                <Grid.Column width={10}>
                  <CustomComponent value={this.props[id]} onChange={this.props.onChange} />
                </Grid.Column>
              </Grid.Row>
            )
          })}
        </Grid>
      </Segment>
    )
  }
}

export default MapStyleSetting
