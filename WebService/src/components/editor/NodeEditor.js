import React, { Component } from 'react'

import { Form } from 'semantic-ui-react'
import { Editor } from './index'

/**
 * This is an modal editor for Node Component to change its name,
 * and position (i.e. lat, lng, z)
 * Node Editor component is based on the Editor component
 */
class NodeEditor extends Component {
  constructor (props) {
    super(props)
    this.state = {
      ...props.data
    }
  }

  onEdit = () => {
    this.props.onEdit && this.props.onEdit(this.state)
  }

  /**
   * This function is to change the state of current data
   */
  handleChange = (e, { name, value }) => {
    this.setState({ [name]: value })
  }

  handlePositionChange = (e, { name, value }) => {
    this.setState({
      position: {
        ...this.state.position,
        [name]: value
      }
    })
  }

  /**
   * This function is to render the name and position form
   */
  renderForm = () => {
    const { id, position } = this.state
    return [
      <Form.Input value={id} name='id' onChange={this.handleChange} label='ID' placeholder='ID' />,
      Object.keys(position).map(key => {
        const label = key.toUpperCase()
        return (
          <Form.Input
            key={key}
            value={position[key]}
            name={key}
            onChange={this.handlePositionChange}
            label={label}
            placeholder={label}
          />
        )
      })
    ]
  }

  render () {
    const { id, position } = this.state
    const { data, onEdit, onClose, ...additionalProps } = this.props
    return (
      <Editor {...additionalProps} onClose={onClose} onEdit={this.onEdit}>
        {this.renderForm()}
      </Editor>
    )
  }
}

export default NodeEditor
