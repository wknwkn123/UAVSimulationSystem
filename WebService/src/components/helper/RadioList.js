import React, { Component } from 'react'

import { Radio } from 'semantic-ui-react'

import './style.scss'

/**
 * RadioList component allows user to select an option from data props
 * @param {Array} data
 * @param {String} className
 * @param {function (selectedValue)} onChange
 */
class RadioList extends Component {
  constructor (props) {
    super(props)
    this.state = {
      value: props.defaultValue
    }
  }

  handleChange = (e, { value }) => {
    this.setState({ value });
    this.props.onChange && this.props.onChange(value)
  }

  render () {
    const { data, className = '' } = this.props
    return (
      <div className={className}>
        {data.map(obj => {
          return (
            <Radio
              style={{ fontSize: 'inherit' }}
              key={obj}
              label={obj}
              name={obj.toString()}
              value={obj}
              checked={this.state.value === obj}
              onChange={this.handleChange}
            />
          )
        })}
      </div>
    )
  }
}

export default RadioList
