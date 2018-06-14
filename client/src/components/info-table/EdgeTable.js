import React, { Component } from 'react'

import { InfoTable, Options } from './index'

import * as CONSTANT from './Constants'

/**
 * Edge Table cmp is a Table visualizer for edge object which has
 * following {source, target} property and additionally
 * edit+remove button
 *
 * props:
 * 1. Data (Node data)
 * 3. function onRemove(type='edge', index)
 */
class EdgeTable extends Component {
  render () {
    const { data = [], onEdit, onRemove } = this.props
    return (
      <InfoTable
        headerKey={CONSTANT.EDGE_KEY}
        renderBody={() =>
          data.map((edge, i) => {
            const { source, target } = edge
            return [source, target, <Options index={i} type='edge' onRemove={onRemove} />]
          })
        }
      />
    )
  }
}

export default EdgeTable
