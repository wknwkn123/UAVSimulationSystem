import React, { Component } from 'react'

import { Form } from 'semantic-ui-react'
import { OpenerModal } from '../file-setting'

/**
 * This is the base editor modal component
 */
class EditorModal extends Component {
  /**
   * Editor modal will render additional actionButton which is
   * an "Edit" button for user to click on when the user finishs editing
   */
  render () {
    const { onEdit, onClose } = this.props
    return (
      <OpenerModal
        onClose={onClose}
        header='Edit Form'
        actionButtons={[
          {
            onClick: onEdit,
            positive: true,
            icon: 'checkmark',
            content: 'Edit'
          }
        ]}
      >
        <Form>{this.props.children}</Form>
        <p>Are you sure you want to edit {name}?</p>
      </OpenerModal>
    )
  }
}

export default EditorModal
