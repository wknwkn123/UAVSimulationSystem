import React, { Component } from 'react'

import { OpenerModal, FilePickerDropdown } from './index'

/**
 * OpenOpenerModal is for opening file which based on from:
 *  1. OpenerModal, and
 *  2. FilePickerDropdown (allowAdditions = false)
 * 
 * props:
 * 1. data (filename list)
 * 2. onClose
 * 3. onDoAction
 */
class OpenOpenerModal extends Component {
  state = {
    fileSelected: null
  }

  onSelectFile = fileSelected => {
    this.setState({ fileSelected })
  }

  onOpenFile = () => {
    const { fileSelected } = this.state
    const { onDoAction } = this.props
    if (fileSelected) onDoAction && onDoAction(fileSelected)
  }

  render () {
    const { data, onClose } = this.props
    return (
      <OpenerModal
        onClose={onClose}
        header='Open File'
        actionButtons={[
          {
            onClick: this.onOpenFile,
            positive: true,
            icon: 'checkmark',
            content: 'Open'
          }
        ]}
      >
        <p>Select file</p>
        <FilePickerDropdown data={data} onSelectFile={this.onSelectFile} />
      </OpenerModal>
    )
  }
}

export default OpenOpenerModal
