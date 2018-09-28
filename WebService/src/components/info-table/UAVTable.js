import React, { Component } from 'react'

import { InfoTable, Options } from './index'
import { Table, Icon, Progress } from 'semantic-ui-react'

import * as CONSTANT from './Constants'
import './style.scss'

const UAVTableCell = ({ content, ...additionalProps }) => (
  <Table.Cell verticalAlign='middle' textAlign='center' width={2} {...additionalProps} className='uav-table-cell'>
    {content}
  </Table.Cell>
)

/**
 * UAVTable component
 * @param {Array} data which contains uav list
 * @param {String} activeUAV the id of the active UAV
 */
class UAVTable extends Component {
  render () {
    const { data = [], activeUAV = null } = this.props
    return (
      <InfoTable
        headerKey={CONSTANT.UAV_KEY}
        widthKey={CONSTANT.UAV_WIDTH_KEY}
        renderCustomBody={() =>
          data.map((uav, i) => {
            const { id, position, alert, battery, progress, collision_risk, revenue } = uav
            const color = alert ? 'red' : 'yellow'
            const active = activeUAV === id
            return (
              <Table.Row key={i} active={active}>
                <UAVTableCell content={id} />
                <UAVTableCell content={position.lat + ',' + position.lng} />
                <UAVTableCell content={battery} />
                <UAVTableCell
                  content={<Progress className='uav-progress' progress size='small' color={color} percent={progress} />}
                  width={4}
                />
                <UAVTableCell content={collision_risk} />
                <UAVTableCell content={'$' + revenue} />
              </Table.Row>
            )
          })
        }
      />
    )
  }
}

export default UAVTable
