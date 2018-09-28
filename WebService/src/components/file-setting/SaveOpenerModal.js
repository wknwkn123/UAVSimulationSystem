import React, { Component } from 'react'

import { OpenerModal, FilePickerDropdown } from './index'

/**
 * SaveOpenerModal is for saving file which based on from:
 *  1. OpenerModal, and
 *  2. FilePickerDropdown (allowAdditions = true)
 *
 * props:
 * 1. data (filename list)
 * 2. onClose
 * 3. onDoAction
 */
class SaveOpenerModal extends Component {
  state = {
    fileSelected: null
  }

  onSaveFile = () => {
    const { fileSelected } = this.state
    const { onDoAction } = this.props
    if (fileSelected) onDoAction && onDoAction(fileSelected)
  }

  onSelectFile = fileSelected => {
    this.setState({ fileSelected })
  }

  render () {
    const { data, onDoAction, onClose } = this.props
    return (
      <OpenerModal
        onClose={onClose}
        header='Save File'
        actionButtons={[
          {
            onClick: this.onSaveFile,
            positive: true,
            icon: 'checkmark',
            content: 'Save'
          }
        ]}
      >
        <p>Select file</p>
        <FilePickerDropdown data={data} onSelectFile={this.onSelectFile} allowAdditions />
      </OpenerModal>
    )
  }
}

export default SaveOpenerModal
