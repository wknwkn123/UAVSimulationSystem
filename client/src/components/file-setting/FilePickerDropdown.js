import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Dropdown } from 'semantic-ui-react'

/**
 * FilePickerDropdown is a dropdown to select filename from the given data
 * 
 * props:
 * 1. data
 * 2. onSelectFile
 * 3. allowAdditions (optional)
 */
class FilePickerDropdown extends Component {
  constructor (props) {
    super(props)
    this.state = {
      data: props.data
    }
  }

  generateDropdownOptionsProps = data => {
    return data.map(fileName => ({
      key: fileName,
      text: fileName,
      value: fileName
    }))
  }

  handleAddition = (e, { value }) => {
    this.setState({ data: [...this.state.data, value] })
  }

  render () {
    const { data } = this.state
    const { onSelectFile, allowAdditions } = this.props
    return (
      <Dropdown
        className='file-picker dropdown'
        fluid
        scrolling
        search
        selection
        closeOnChange
        onChange={(event, { value }) => {
          onSelectFile && onSelectFile(value)
        }}
        allowAdditions={allowAdditions}
        onAddItem={this.handleAddition}
        options={this.generateDropdownOptionsProps(data)}
        placeholder='Select File'
      />
    )
  }
}

export default FilePickerDropdown
