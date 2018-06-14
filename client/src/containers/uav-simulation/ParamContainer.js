import React, { Component } from 'react'
import { connect } from 'react-redux'
import * as uavAction from '../../actions/uav'

import { Label, Divider, Button } from 'semantic-ui-react'

import { SliderLabeled } from '../../components/map'
import { RadioList } from '../../components/helper'

const PARAMS_LIST = [
  { key: 'airspaceType', title: 'Airspace', defaultValue: 'PLANARGRAPH', options: ['PLANARGRAPH', 12, 22, 32] },
  { key: 'flightScheduleType', title: 'Flight schedule', defaultValue: 'RANDOM', options: ['RANDOM', 1, 2, 3] },
  { key: 'UAV', title: 'UAVs', defaultValue: 15, minValue: 1, maxValue: 100 },
  { key: 'averageSpeed', title: 'Avg speed', defaultValue: 20, minValue: 1, maxValue: 50 },
  { key: 'simulationRate', title: 'Sim rate', defaultValue: 1, minValue: 1, maxValue: 10 }
]

const DEFAULT_STATE = {
  airspaceType: PARAMS_LIST[0].defaultValue,
  flightScheduleType: PARAMS_LIST[1].defaultValue,
  UAV: PARAMS_LIST[2].defaultValue,
  averageSpeed: PARAMS_LIST[3].defaultValue,
  simulationRate: PARAMS_LIST[4].defaultValue
}

class ParamContainer extends Component {
  state = DEFAULT_STATE

  handleParamClick = () => {
    const { type } = this.props.param
    switch (type) {
      case 'start':
        this.props.onSendParam('stop', this.state)
        break
      case 'stop':
        this.props.onSendParam('start', this.state)
        break
      default:
        break
    }
  }

  handleChange = key => value => {
    this.setState({ [key]: value })
  }

  renderParamComponent = param => {
    const { key, title, defaultValue, minValue, maxValue, options = [] } = param
    let content = null
    switch (title) {
      case 'Airspace':
      case 'Flight schedule':
        content = (
          <RadioList
            className='param-content radio-list'
            defaultValue={defaultValue}
            onChange={this.handleChange(key)}
            data={options}
          />
        )
        break
      case 'UAVs':
      case 'Avg speed':
      case 'Sim rate':
        content = (
          <SliderLabeled
            className='param-content slider'
            defaultValue={defaultValue}
            min={minValue}
            max={maxValue}
            onChange={this.handleChange(key)}
          />
        )
        break
      default:
        break
    }
    return (
      <div key={param.title}>
        <Label className='param-title' content={title} />
        {content}
      </div>
    )
  }

  render () {
    const { type } = this.props.param
    return (
      <div className='param-container'>
        <Divider horizontal style={{ height: 3 }}>
          Param Configuration
        </Divider>
        {PARAMS_LIST.map((param, i) => {
          return this.renderParamComponent(param)
        })}
        <Divider style={{ margin: 10 }} />
        <Button
          size='tiny'
          color={type === 'start' ? 'black' : 'green'}
          className={'param-button'}
          onClick={this.handleParamClick}
        >
          {type === 'start' ? 'Stop' : 'Start'}
        </Button>
      </div>
    )
  }
}

const mapStateToProps = state => {
  const { param } = state.uav
  return { param }
}

const actions = { ...uavAction }
export default connect(mapStateToProps, actions)(ParamContainer)
