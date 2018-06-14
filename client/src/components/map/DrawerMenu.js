import React, { Component } from 'react'
import { Menu } from 'semantic-ui-react'

import MenuItem from './CommonMenuItem'
import { LIST_DRAW_MENU } from './Constants'

import './style.scss'

// DrawerMenu is the UI interface built on top of the DrawControl of Mapbox
class DrawerMenu extends Component {
  // It allows to pass the click event to the upper layer which is designed for
  // the MapDrawer Component
  handleClick = (event, { name }) => {
    this.props.onClick && this.props.onClick(name)
  }

  render () {
    const { disabled, active } = this.props
    return (
      <Menu compact size='mini' vertical icon='labeled' className='drawer map-menu'>
        {LIST_DRAW_MENU.map((draw, i) => {
          const { name, img, title } = draw
          return (
            <MenuItem disabled={disabled} active={active === name} onClick={this.handleClick} key={i} name={name}>
              <img src={img} />
              {title}
            </MenuItem>
          )
        })}
      </Menu>
    )
  }
}
export default DrawerMenu
