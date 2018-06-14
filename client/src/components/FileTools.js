import React, { Component } from 'react'
import { connect } from 'react-redux'

import { Container, Menu, Icon } from 'semantic-ui-react'

import FileModal from './common/FileModal'

import * as action from '../actions/tools'
import * as style from './style'

class FileTools extends Component {

    showOpenFileModal = () => { this.openFileModal.showModal() }

    showSaveFileModal = () => { this.saveFileModal.showModal() }

    render() {
        return (
            <Container style={style.MapFileTools}>
                <Menu
                    compact
                    size="mini"
                    vertical
                    icon='labeled'>

                    <Menu.Item name='open'
                        style={style.COMPACT.MENU_ITEM}
                        onClick={this.showOpenFileModal}>
                        <Icon name='open folder' />
                        Open
                    </Menu.Item>

                    <Menu.Item name='save'
                        style={style.COMPACT.MENU_ITEM}
                        onClick={this.showSaveFileModal}>
                        <Icon name='save' />
                        Save
                    </Menu.Item>

                </Menu>

                <FileModal ref={(x) => this.openFileModal = x}
                    onFileSelected={({ file }) => {
                        console.log({ file })
                    }}
                    openType
                    header="Open Simulation" />
                <FileModal ref={(x) => this.saveFileModal = x}
                    onFileSelected={({ file }) => {
                        console.log({ file })
                    }}
                    saveType
                    header="Save Simulation" />
            </Container>
        )
    }
}

const mapStateToProps = (state) => {
    return {}
}

export default connect(mapStateToProps, action)(FileTools)