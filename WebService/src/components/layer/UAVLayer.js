import React, { Component } from 'react'

import NodeLayer from './NodeLayer'
import EdgeLayer from './EdgeLayer'
import PopupLayer from './PopupLayer'

const InnerCirlce = ({ data, onMouseEnter }) => (
  <NodeLayer
    onMouseEnter={({ features }) => onMouseEnter(features[0].properties.obj)}
    data={data}
    color='cyan'
    radius={10}
    opacity={1}
  />
)

const OuterCircle = ({ data, color }) => <NodeLayer data={data} color={color} radius='20' opacity={0.2} />

class UAVLayer extends Component {
  constructor (props) {
    super(props)

    const { alertUAV, nonAlertUAV } = this.getUAVData(this.props.data)
    this.state = {
      activeNode: null,
      alertUAV,
      nonAlertUAV
    }
    setInterval(() => this.setState({ canUpdate: true }), 500)
  }

  componentWillReceiveProps (nextProps) {
    if (!this.state.canUpdate) return
    const data = nextProps
    if (this.props.data !== data) {
      const { alertUAV, nonAlertUAV } = this.getUAVData(data)
      this.setState({
        alertUAV,
        nonAlertUAV,
        canUpdate: false
      })
    }
  }

  // To do create converter
  getUAVData = data => {
    alertUAV = NodeLayer.convertData(data.filter(k => k.alert === true))
    nonAlertUAV = NodeLayer.convertData(data.filter(k => k.alert === false))
    return { alertUAV, nonAlertUAV }
  }

  showNode = obj => {
    this.setState({ activeNode: obj })
  }

  hideNode = () => {
    this.setState({ activeNode: null })
  }

  render () {
    const { activeNode } = this.state
    const { alertUAV, nonAlertUAV } = this.state
    return [
      <InnerCirlce onMouseEnter={this.showNode} key='uav-alerted-inner-cirlce' data={alertUAV} />,
      <OuterCircle key='uav-alerted-outer-circle' data={alertUAV} color='red' />,
      <InnerCirlce onMouseEnter={this.showNode} key='uav-unalerted-inner-cirlce' data={nonAlertUAV} />,
      <OuterCircle key='uav-unalerted-outer-circle' data={nonAlertUAV} color='yellow' />,
      <PopupLayer
        renderSubInfo={() => {
          const info = this.props.data.find(k => k === activeNode.id)
          const key = ['name', 'position', 'progress', 'collision_risk', 'revenue', 'battery']
          return key.map((k, i) => (
            <p key={i}>
              {k}: {info[k]}
            </p>
          ))
        }}
        onMouseLeave={this.hideNode}
        key='uav-popup'
        data={activeNode ? this.props.data.find(k => k === activeNode.id) : activeNode}
      />
    ]
  }
}

export default UAVLayer
