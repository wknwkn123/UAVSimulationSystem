import React, { Component } from 'react'

import { Menu } from 'semantic-ui-react'

import MenuItem from './CommonMenuItem'
import MapStyleSetting from './MapStyleSetting'
import { LIST_MENU } from './Constants'

/**
 * MapMenu component provides the interface for user to change map style setting and
 * common menu actions which are 3D mode, reset pitch, and reset bearing
 */
class MapMenu extends Component {
  state = {
    active: null
  }

  handleClick = (event, { name, value }) => {
    if (this.state.active === name) {
      this.setState({ active: null })
    } else {
      this.setState({ active: name })
    }
    this.props.onChange && this.props.onChange(name, value)
  }

  render () {
    return (
      <div>
        <Menu compact size='mini' vertical icon='labeled' className='setting-menu map-menu'>
          {LIST_MENU.map((menu, i) => {
            const { active } = this.state
            const { name, title } = menu
            return (
              <MenuItem key={i} active={active === name} {...menu} onClick={this.handleClick}>
                {title}
              </MenuItem>
            )
          })}
        </Menu>
        <MapStyleSetting
          {...this.props}
          className={this.state.active === 'setting' ? '' : 'hidden'}
        />
      </div>
    )
  }
}

export default MapMenu
