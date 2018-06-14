import React, { Component } from 'react'

import { Input, Button, Icon } from 'semantic-ui-react'

import './style.scss'

class FilterInput extends Component {
  handleClearState = () => {
    const { onChange } = this.props
    onChange && onChange('')
  }

  handleOnChange = (e, { value }) => {
    const { onChange } = this.props
    onChange && onChange(value)
  }

  render () {
    const { placeholder } = this.props
    return (
      <Input
        className='filter-input'
        iconPosition='left'
        onChange={this.handleOnChange}
        placeholder={placeholder || 'Filter ...'}
        value={this.props.value}
        action
      >
        <Icon name='search' />
        <input />
        <Button size='mini' onClick={this.handleClearState} content='Clear' />
      </Input>
    )
  }
}

const withFilter = WrappedComponent => {
  class FilterWrapper extends Component {
    state = {
      value: ''
    }

    handleOnChange = value => {
      this.setState({ value })
    }

    render () {
      const { value } = this.state
      const { placeholder, filterFunction, ...additionalProps } = this.props
      return (
        <div>
          <FilterInput value={value} placeholder={placeholder} onChange={this.handleOnChange} />
          <WrappedComponent {...additionalProps} {...filterFunction(value)} />
        </div>
      )
    }
  }
  return FilterWrapper
}

export default withFilter
