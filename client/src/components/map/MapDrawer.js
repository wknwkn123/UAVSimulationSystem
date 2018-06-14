import React, { Component } from 'react'

import { Map, DrawerMenu } from './'
import { NodeLayer, EdgeLayer, PopupLayer } from '../layer'
import DrawControl from 'react-mapbox-gl-draw'

import { DRAW_LINE, DRAW_POINT } from './Constants'
import generateSource from './Converter2D'
import { Options } from '../info-table'

/**
 * MapDrawer component is a Map component + Drawing component which allows user to
 * add both node and edge component
 */
class MapDrawer extends Component {
  state = {
    onDraw: false,
    nodeForDrawEdge: null,
    activeNodeID: null,
    clickedNodeID: null,
    nodeData: null,
    edgeData: null
  }

  componentDidMount () {
    this.setState({ ...generateSource(this.props.data, this.state) })
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.data !== this.props.data) {
      this.setState({ ...generateSource(nextProps.data, this.state) })
    }
  }

  get map () {
    return this.x
  }

  // It is to check whether the draw control is creating a new object
  // and then we delete it to re-create our own style object and to
  // retain the data as ourselves data
  handleDrawCreate = (m, e, n) => {
    const { onDraw } = this.state
    const { mode3D } = this.props
    if (mode3D) return
    if (onDraw === DRAW_POINT) {
      const { lng, lat } = e.lngLat
      this.addNode({ lat: lat.toFixed(6), lng: lng.toFixed(6) })
    }
  }

  // This provides interface to the upper layer to know that we are
  // adding a new node
  addNode = ({ lat, lng }) => {
    if (this.state.onDraw === DRAW_POINT) {
      this.setState({ onDraw: false })
      this.props.onAddNode && this.props.onAddNode({ lat, lng })
    }
  }

  // This provides interface to the upper layer to know that we are
  // adding a new edge
  addEdge = ({ source, target }) => {
    this.setState({ onDraw: false, clickedNodeID: null })
    this.props.onAddEdge && this.props.onAddEdge({ source, target })
  }

  // This contains the logic of how we can add edge between 2 nodes
  handleNodeClick = nodeID => {
    const { clickedNodeID, onDraw } = this.state
    // Only let when onDrawEdge to activate this feature
    if (!onDraw || onDraw !== DRAW_LINE) return
    // Toogle Node
    if (nodeID === clickedNodeID) {
      // Toogle Node
      this.setState({ clickedNodeID: null })
    } else if (!clickedNodeID) {
      // If it is still null
      this.setState({ clickedNodeID: nodeID })
    } else {
      const source = clickedNodeID
      const target = nodeID
      this.addEdge({ source, target })
    }
  }

  showNode = nodeID => {
    if (nodeID === this.state.activeNodeID) return
    // console.log('showing node', nodeID)
    this.setState({ activeNodeID: nodeID })
    this.setState({ ...generateSource(this.props.data, this.state) })
  }
  hideNode = () => {
    if (this.state.activeNodeID === null) return
    // console.log('hiding node')
    this.setState({ activeNodeID: null }, () => this.setState({ ...generateSource(this.props.data, this.state) }))
  }

  handleDrawerClick = name => {
    // console.log('Drawer menu clicked', name)
    if (this.state.onDraw) {
      this.setState({ onDraw: false, clickedNodeID: null })
    } else {
      this.setState({ onDraw: name, clickedNodeID: null })
    }
  }

  render () {
    const { activeNodeID, onDraw, clickedNodeID, nodeData, edgeData } = this.state
    const { data, children, renderOverlay, onEdit, onRemove, ...optionalMapProps } = this.props
    const { node, edge } = data
    return (
      <Map
        data={data}
        {...optionalMapProps}
        overlayClassName='drawer'
        onClick={this.handleDrawCreate}
        ref={this.props.onMapRef}
        renderOverlay={() => {
          const overlay = renderOverlay && renderOverlay()
          return [
            <DrawerMenu disabled={this.props.mode3D} key='drawer' onClick={this.handleDrawerClick} active={onDraw} />,
            ...overlay
          ]
        }}
      >
        {nodeData && (
          <NodeLayer data={this.state.nodeData} onMouseEnter={this.showNode} onClick={this.handleNodeClick} />
        )}
        {edgeData && <EdgeLayer data={this.state.edgeData} />}
        <PopupLayer
          data={onDraw ? null : node.find(k => k.id === activeNodeID)}
          onMouseLeave={this.hideNode}
          renderSubInfo={() => {
            const realNode = node.find(k => k.id === activeNodeID)
            const { lat, lng } = realNode.position
            return [
              <p key='lat'>lat:{lat}</p>,
              <p key='lng'>lng:{lng}</p>,
              <Options
                key='button'
                index={node.findIndex(k => k.id === activeNodeID)}
                type='node'
                onEdit={onEdit}
                onRemove={onRemove}
              />
            ]
          }}
        />
      </Map>
    )
  }
}

export default MapDrawer
