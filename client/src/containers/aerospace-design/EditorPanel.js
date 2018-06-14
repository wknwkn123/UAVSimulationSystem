import React, { Component } from 'react'
import { connect } from 'react-redux'

import * as mapDrawActions from '../../actions/mapDraw'
import * as fileActions from '../../actions/file'
import * as uiActions from '../../actions/ui'

import { Accordion, Icon, Header } from 'semantic-ui-react'
import * as CONSTANT from './Constants'
import { NodeEditor, DeleteConfirmation } from '../../components/editor'
import { OpenOpenerModal, SaveOpenerModal, FileShortcutButton } from '../../components/file-setting'
import { NodeTable, EdgeTable } from '../../components/info-table'
import { withFilter } from '../../components/helper'
import './style.scss'

const TitleWrapper = (WrappedComponent, title) => (
  <span>
    {WrappedComponent}
    {title}
  </span>
)
const IconTitle = name => <Icon circular bordered name={name} />

const NodeTableWithFilter = withFilter(NodeTable)
const EdgeTableWithFilter = withFilter(EdgeTable)

/**
 * EditorPanel container contains all the left side panel which acts as the dashboard for
 * the user to view the node+edge table where they can delete or edit the data
 */
class EditorPanel extends Component {
  componentDidMount () {
    this.props.doGetFileList && this.props.doGetFileList()
  }

  handleClickEdit = (type, index) => {
    this.props.onEditModalState({ editor: { show: type, data: this.props[type].data[index], index } })
  }

  handleClickRemove = (type, index) => {
    this.props.onEditModalState({ delete_confirmation: { show: type, data: this.props[type].data[index], index } })
  }

  generateGraphFromProps = () => {
    const { node, edge } = this.props
    return { graph: { node: node.data, edge: edge.data } }
  }

  renderContent = key => {
    const { node, edge, filename } = this.props
    switch (key) {
      case CONSTANT.NODE:
        return (
          <NodeTableWithFilter
            filterFunction={value => {
              return { data: node.data.filter(node => node.id.includes(value)) }
            }}
            onEdit={this.handleClickEdit}
            onRemove={this.handleClickRemove}
          />
        )
      case CONSTANT.EDGE:
        return [
          <FileShortcutButton
            key='generate-edge'
            basic
            color='green'
            className='generate-edge-btn'
            onClick={() => this.props.doGenerateEdges({ ...this.generateGraphFromProps(), filename })}
          >
            Generate Edge
          </FileShortcutButton>,
          <EdgeTableWithFilter
            key='table-edge'
            filterFunction={value => {
              return { data: edge.data.filter(edge => edge.source.includes(value) || edge.target.includes(value)) }
            }}
            onRemove={this.handleClickRemove}
          />
        ]
      default:
        return <div>{key}</div>
    }
  }

  closeModal = type => () => this.props.onEditModalState({ [type]: { show: false, data: null, index: null } })

  onActionEditor = data => {
    let i = this.props.editor.index
    let prevNode = this.props.node.data[i]
    if (data.id === prevNode.id) {
      this.props.onEditNode(this.props.node.data[i], data, i)
    } else {
      if(this.props.node.data.findIndex(k => k.id === data.id) === -1){
        this.props.onEditNode(this.props.node.data[i], data, i)
      } else {
        console.log('error editing')
      }
    }
    this.closeModal('editor')()
  }

  renderEditor ({ show, data }) {
    switch (show) {
      case CONSTANT.NODE:
        return <NodeEditor data={data} onClose={this.closeModal('editor')} onEdit={this.onActionEditor} />
      default:
        return null
    }
  }

  onActionDelete = (type, index) => () => {
    switch (type) {
      case CONSTANT.NODE:
        var deletedNode = this.props.node.data[index]
        const edgesToBeDeleted = this.props.edge.data.reduce((acc, edge, i) => {
          if (deletedNode.id === edge.source || deletedNode.id === edge.target) {
            acc.push(i)
          }
          return acc
        }, [])
        this.props.onRemoveNodeAndEdgeBulk(index, edgesToBeDeleted)
        break
      case CONSTANT.EDGE:
        this.props.onRemoveEdge(this.props.edge.data[index], index)
        break
      default:
        break
    }
    this.closeModal('delete_confirmation')()
  }

  renderDelete ({ show, data, index }) {
    switch (show) {
      case CONSTANT.NODE:
      case CONSTANT.EDGE:
        return (
          <DeleteConfirmation
            data={data}
            onClose={this.closeModal('delete_confirmation')}
            onDoAction={this.onActionDelete(show, index)}
          />
        )
      default:
        return null
    }
  }

  handleFileMenuClick = (event, { name }) => {
    const { node, edge, filename, filelist } = this.props
    switch (name) {
      case CONSTANT.CREATE_NEW:
        this.props.doCreateNewFile()
        break
      case CONSTANT.SAVE:
        if (filename) {
          this.props.doSaveFile({ ...this.generateGraphFromProps(), filename })
        } else {
          this.props.onEditModalState({ file: { show: name, data: filelist } })
        }
        break
      case CONSTANT.OPEN:
      case CONSTANT.SAVE_AS:
        this.props.onEditModalState({ file: { show: name, data: filelist } })
        break
      default:
        break
    }
  }

  renderFile ({ show, data }) {
    const { node, edge } = this.props
    const basicProps = {
      data: data,
      onClose: this.closeModal('file'),
      onDoAction: filename => {
        if (show === CONSTANT.OPEN) {
          this.props.doOpenFile({ filename })
        } else if (show === CONSTANT.SAVE_AS || show === CONSTANT.SAVE) {
          this.props.doSaveFile({ ...this.generateGraphFromProps(), filename })
        }
        this.closeModal('file')()
      }
    }
    switch (show) {
      case CONSTANT.OPEN:
        return <OpenOpenerModal {...basicProps} />
      case CONSTANT.SAVE:
      case CONSTANT.SAVE_AS:
        return <SaveOpenerModal {...basicProps} />
      default:
        return null
    }
  }

  render () {
    const { editor, file, delete_confirmation } = this.props
    const { filename } = this.props
    return (
      <div id='left-panel-container'>
        <div className='header'>
          <Header style={{ margin: 10 }} as='h3'>
            {filename || 'Untitled File'}
          </Header>
          <div className='file-tools-container'>
            {CONSTANT.HEADER_LIST.map((buttonProps, i) => (
              <FileShortcutButton {...buttonProps} key={i} onClick={this.handleFileMenuClick} />
            ))}
          </div>
        </div>
        <div className='main'>
          <Accordion
            exclusive={false}
            panels={CONSTANT.ACCORDION_LIST.map(({ icon, title, key }) => {
              return {
                title: { content: TitleWrapper(IconTitle(icon), title), key: key + '-title' },
                content: { content: this.renderContent(key), key: key + '-content' }
              }
            })}
          />
        </div>
        {editor.show && this.renderEditor(editor)}
        {file.show && this.renderFile(file)}
        {delete_confirmation.show && this.renderDelete(delete_confirmation)}
      </div>
    )
  }
}

const mapStateToProps = state => {
  const { node, edge } = state
  const { filename, filelist } = state.file
  const { modal } = state.ui
  return { node, edge, filename, filelist, ...modal }
}

const actions = { ...mapDrawActions, ...fileActions, ...uiActions }
export default connect(mapStateToProps, actions)(EditorPanel)
