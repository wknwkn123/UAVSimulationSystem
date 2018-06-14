import React, { Component } from 'react'

import { OpenerModal } from '../file-setting'

/**
 * DeleteConfirmationModal is for opening file which based on from:
 *  1. OpenerModal
 *
 * props:
 * 1. data 
 * 2. onClose
 * 3. onDoAction
 */
class DeleteConfirmationModal extends Component {
  onDelete = () => {
    const { onDoAction } = this.props
    onDoAction && onDoAction()
  }

  render () {
    const { data, onClose } = this.props
    return (
      <OpenerModal
        onClose={onClose}
        header='Delete confirmation'
        actionButtons={[
          {
            onClick: this.onDelete,
            positive: true,
            icon: 'checkmark',
            content: 'Delete'
          }
        ]}
      >
        <p>Are you sure to delete following data</p>
        <div>{JSON.stringify(data)}</div>
      </OpenerModal>
    )
  }
}

export default DeleteConfirmationModal
