import React, { Component } from 'react'

import { InfoTable, Options } from './index'

import * as CONSTANT from './Constants'

/**
 * Node Table cmp is a Table visualizer for Node object which has
 * following {id, position: {lat, lng, z}} property and additionally
 * edit+remove button
 *
 * props:
 * 1. Data (Node data)
 * 2. function onEdit(type='node', index)
 * 3. function onRemove(type='node', index)
 */
class NodeTable extends Component {
  render () {
    const { data = [], onEdit, onRemove } = this.props
    return (
      <InfoTable
        headerKey={CONSTANT.NODE_KEY}
        renderBody={() =>
          data.map((node, i) => {
            const { id, position } = node
            return [
              id,
              position.lat + ',' + position.lng,
              <Options index={i} type='node' onEdit={onEdit} onRemove={onRemove} />
            ]
          })
        }
      />
    )
  }
}

export default NodeTable
