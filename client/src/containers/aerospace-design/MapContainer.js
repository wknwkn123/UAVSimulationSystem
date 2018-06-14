import React, { Component } from 'react'
import { connect } from 'react-redux'

import * as mapDrawActions from '../../actions/mapDraw'
import * as uiActions from '../../actions/ui'

import { withMapWrapper, MapDrawer, Map3D } from '../../components/map'
import './style.scss'

const Map = withMapWrapper(MapDrawer)

class MapContainer extends Component {
  handleAddNode = position => {
    console.log('Handle add node', position)
    // Add it to the data
    const id = 'node-' + this.props.node.counter
    this.props.onAddNode && this.props.onAddNode({ id, position })
  }

  handleAddEdge = ({ source, target }) => {
    console.log('Handle add edge', { source, target })
    // If source and target is already in the data
    // then ignore it
    // else add it to the data
    const edgeData = this.props.edge.data
    const sameEdge = edgeData.filter(
      k => (k.source === source && k.target === target) || (k.source === target && k.target === source)
    )
    if (sameEdge.length === 0) {
      this.props.onAddEdge && this.props.onAddEdge({ source, target })
    }
  }

  handleClickEdit = (type, index) => {
    this.props.onEditModalState({ editor: { show: type, data: this.props[type].data[index], index } })
  }

  handleClickRemove = (type, index) => {
    this.props.onEditModalState({ delete_confirmation: { show: type, data: this.props[type].data[index], index } })
  }

  render () {
    const { node, edge } = this.props
    return (
      <div id='map-container'>
        <Map
          renderMap3D={Map3D}
          onAddNode={this.handleAddNode}
          onAddEdge={this.handleAddEdge}
          data={{ node: node.data, edge: edge.data }}
          onEdit={this.handleClickEdit}
          onRemove={this.handleClickRemove}
        />
      </div>
    )
  }
}

const mapStateToProps = state => {
  const { node, edge } = state
  return { node, edge }
}

const actions = { ...mapDrawActions, ...uiActions }
export default connect(mapStateToProps, actions)(MapContainer)
