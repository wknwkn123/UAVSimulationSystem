import React, { Component } from 'react'

import { Menu, Icon } from 'semantic-ui-react'

// CommonMenuItem is a class to accomodate the
// default style of the button inside the Map Menu
class CommonMenuItem extends Component {
  render () {
    const { iconName, children, ...additionalProps } = this.props
    return (
      <Menu.Item {...additionalProps} className='map-menu-item'>
        {this.props.iconName && <Icon name={this.props.iconName} />}
        {this.props.children}
      </Menu.Item>
    )
  }
}

export default CommonMenuItem
