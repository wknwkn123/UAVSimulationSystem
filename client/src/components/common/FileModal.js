import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Button, Modal } from 'semantic-ui-react'
import FileDropdown from './FileDropdown'

class FileModal extends Component {

    state = { show: false }

    showModal = () => { this.setState({ show: true }) }

    render() {
        const { show } = this.state
        const { header, extraContent } = this.props
        const { onFileSelected } = this.props

        const { openType, saveType } = this.props
        return (
            <Modal
                size="mini"
                open={this.state.show}
                onClose={() => this.setState({ show: false })}>
                <Modal.Header>
                    {header}
                </Modal.Header>
                <Modal.Content>
                    <p>Select file</p>
                    {
                        openType &&
                        <FileDropdown
                            ref={(x) => this.fileDropdown = x} />
                    }
                    {
                        saveType &&
                        <FileDropdown allowAdditions
                            ref={(x) => this.fileDropdown = x} />
                    }
                </Modal.Content>
                <Modal.Actions>
                    <Button
                        onClick={() => this.setState({ show: false })}
                        negative
                        icon='close'
                        labelPosition='right'
                        content='Cancel' />
                    <Button
                        onClick={() => {
                            onFileSelected({ file: this.fileDropdown.getValue() })
                            this.setState({ show: false })
                        }}
                        positive
                        icon='checkmark'
                        labelPosition='right'
                        content={openType ? "Open" : (saveType ? "Save" : "Undefined")} />
                </Modal.Actions>
            </Modal>
        )
    }
}

export default FileModal