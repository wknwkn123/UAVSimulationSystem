import React, { Component } from 'react'

import { Icon, Button } from 'semantic-ui-react'

import './style.scss'
// FileShortcutButton is a class to accomodate the
// default style of the button for file shortcut
class FileShortcutButton extends Component {
  render () {
    const { className, iconName, children, ...additionalProps } = this.props
    return (
      <Button size='small' {...additionalProps} className={'file-shortcut ' + className}>
        {this.props.iconName && <Icon name={this.props.iconName} />}
        {this.props.children}
      </Button>
    )
  }
}

export default FileShortcutButton
