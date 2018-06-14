import React, { Component } from 'react'

import { Table } from 'semantic-ui-react'

import './style.scss'
// Default Info Table Props
const defaultTableProps = {
  fixed: true,
  celled: true,
  sortable: true,
  structured: true,
  selectable: true,
  textAlign: 'center',
  collapsing: true,
  color: 'grey',
  compact: true,
  size: 'small',
  inverted: true
}

class InfoTable extends Component {
  /**
   * headerKey    : List<String> which contains the key of the data
   * headerBody   : function() which returns the body of itself
   */
  render () {
    const { headerKey, renderCustomBody, renderBody, widthKey = {} } = this.props
    return (
      <div className='info-container'>
        <Table {...defaultTableProps} className='table-header'>
          <Table.Header>
            <Table.Row>
              {headerKey.map((header, i) => (
                <Table.HeaderCell width={widthKey[header]} key={i}>
                  {header}
                </Table.HeaderCell>
              ))}
            </Table.Row>
          </Table.Header>
        </Table>
        <Table {...defaultTableProps} className='table-content'>
          <Table.Body>
            {renderBody &&
              renderBody().map((row, i) => (
                <Table.Row key={i}>{row.map((column, j) => <Table.Cell key={j}>{column}</Table.Cell>)}</Table.Row>
              ))}
            {renderCustomBody && renderCustomBody()}
          </Table.Body>
        </Table>
      </div>
    )
  }
}

export default InfoTable
