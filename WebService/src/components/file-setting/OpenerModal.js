import React, { Component } from 'react'

import { Button, Modal } from 'semantic-ui-react'

/**
 * OpenerModal is a generic modal for this application
 * 
 * props:
 * 1. header
 * 2. actionButtons (additional buttons, i.e. Save, Edit, etc.)
 * 3. onCloseModal  (optional)
 */
class OpenerModal extends Component {
  closeModal = () => {
    this.props.onClose && this.props.onClose()
  }

  render () {
    const { header, actionButtons } = this.props
    return (
      <Modal size='mini' style={{ position: 'relative', overflow: 'initial' }} open onClose={this.closeModal}>
        <Modal.Header>{header}</Modal.Header>
        <Modal.Content>{this.props.children}</Modal.Content>
        <Modal.Actions>
          {actionButtons &&
            actionButtons.map((action, i) => {
              return <Button key={i} {...action} />
            })}
          <Button onClick={this.closeModal} negative icon='close' labelPosition='right' content='Cancel' />
        </Modal.Actions>
      </Modal>
    )
  }
}

export default OpenerModal
