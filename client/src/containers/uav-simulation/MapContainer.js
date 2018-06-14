import React, { Component } from 'react'
import { connect } from 'react-redux'

import { withMapWrapper, Map } from '../../components/map'
import './style.scss'
import { NodeLayer, EdgeLayer, PopupLayer, SymbolGeoJSON } from '../../components/layer'
import { UAVTable } from '../../components/info-table'
import generateSource, { generateUAVSource } from '../../components/map/Converter2D'
import * as CONSTANT from './Constants'

const MapWithSetting = withMapWrapper(Map)

class MapContainer extends Component {
  state = {
    activeUAV: null
  }

  showNode = nodeID => {
    if (nodeID === this.state.activeUAV) return
    this.setState({ activeUAV: nodeID })
  }
  hideNode = () => {
    if (this.state.activeUAV === null) return
    this.setState({ activeUAV: null })
  }

  renderLayer = () => {
    const { node, edge, uav } = this.props
    const { activeUAV } = this.state
    const { nodeData, edgeData } = generateSource({ node: node.data, edge: edge.data })
    const { innerData, alertOuterData, normalOuterData } = generateUAVSource(uav.data, activeUAV)
    return [
      <NodeLayer data={nodeData} key='graph-node' />,
      <SymbolGeoJSON data={nodeData} key='graph-node-symbol' />,
      <NodeLayer
        opacity={0.2}
        data={alertOuterData}
        radius={NodeLayer.RADIUS.BIG_CIRLCE}
        key='alerted-uav-outer-cirlce'
      />,
      <NodeLayer
        onMouseEnter={this.showNode}
        data={innerData}
        radius={NodeLayer.RADIUS.UAV_CIRCLE}
        key='uav-inner-cirlce'
      />,
      <NodeLayer
        opacity={0.2}
        data={normalOuterData}
        radius={NodeLayer.RADIUS.BIG_CIRLCE}
        key='non-alerted-uav-outer-cirlce'
      />,
      <EdgeLayer data={edgeData} key='graph-edge' />,
      <PopupLayer
        key='uav-popup'
        data={!activeUAV ? null : uav.data.find(k => k.id === activeUAV)}
        onMouseLeave={this.hideNode}
        renderSubInfo={() => {
          const realNode = uav.data.find(k => k.id === activeUAV)
          const { lat, lng } = realNode.position
          return Object.keys(CONSTANT.UAV_KEY).map(key => (
            <p key={key}>
              {key}:{Object.byString(realNode, CONSTANT.UAV_KEY[key]).toString()}
            </p>
          ))
        }}
      />
    ]
  }

  render () {
    const { node, edge, uav } = this.props
    const { activeUAV } = this.state
    return (
      <div id='map-container'>
        <MapWithSetting data={{ node: node.data, edge: edge.data }}>{this.renderLayer()}</MapWithSetting>
        <div key='uav-table' className='uav-table-container'>
          <UAVTable data={uav.data} activeUAV={activeUAV} />
        </div>
      </div>
    )
  }
}

const mapStateToProps = state => {
  const { node, edge, uav } = state
  return { node, edge, uav }
}

const actions = {}
export default connect(mapStateToProps, actions)(MapContainer)
