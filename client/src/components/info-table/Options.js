import React, { Component } from 'react'

import { Button, Icon } from 'semantic-ui-react'

/**
 * OptionButton cmp is button styling for Options cmp
 */
class OptionButton extends Component {
  render () {
    const { iconName, ...additionalProps } = this.props
    return (
      <Button icon {...additionalProps} className='option-button'>
        <Icon name={iconName} />
      </Button>
    )
  }
}

/**
 * Options cmp is consisted of Edit and Remove buttons.
 * 
 * props:
 * 1. type
 * 2. onEdit
 * 3. onRemove
 */
class Options extends Component {
  render () {
    const { onEdit, onRemove, index, type } = this.props
    return (
      <div className='option-container'>
        {onEdit && <OptionButton iconName='edit' positive onClick={() => onEdit(type, index)} />}
        {onRemove && <OptionButton iconName='remove' negative onClick={() => onRemove(type, index)} />}
      </div>
    )
  }
}

export default Options
