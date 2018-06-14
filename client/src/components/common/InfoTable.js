import React, { Component } from 'react'

import PropTypes, { func } from 'prop-types'

import { Container, Table, Icon, Progress } from 'semantic-ui-react'
import * as style from '../style'
import { UAV_PROP, ASCENDING, DESCENDING } from './Constant'

class InfoTable extends Component {

    constructor(props) {
        super(props)
        const { content, sortByAlert, highlighted } = props
        const { alertedContent, nonAlertedContent } = this.separateAlertedContent(content)
        const shownColumn = content[0].infoProps
        this.state = {
            column: null,
            direction: null,
            alertedContent,
            nonAlertedContent,
            highlighted,
            shownColumn
        }
    }

    separateAlertedContent(content) {

        var alertedContent = content.reduce(function (allContent, temp) {
            if (temp.alert)
                allContent.push(temp)
            return allContent
        }, [])

        var nonAlertedContent = content.reduce(function (allContent, temp) {
            if (!temp.alert)
                allContent.push(temp)
            return allContent
        }, [])

        return { alertedContent, nonAlertedContent }
    }

    componentWillReceiveProps(nextProps) {
        // console.log(nextProps)
        const { alertedContent, nonAlertedContent } = this.separateAlertedContent(nextProps.content)
        this.setState({
            alertedContent,
            nonAlertedContent,
            highlighted: nextProps.highlighted
        })
    }

    handleSort = clickedColumn => () => {
        const { alertedContent, nonAlertedContent, column, direction } = this.state

        function arrSort(a, b) {
            return a.compareTo({ comp: b, prop: clickedColumn })
        }

        if (column !== clickedColumn) {
            this.setState({
                column: clickedColumn,
                alertedContent: alertedContent.sort(arrSort),
                nonAlertedContent: nonAlertedContent.sort(arrSort),
                direction: ASCENDING,
            })
            return
        }

        this.setState({
            nonAlertedContent: nonAlertedContent.reverse(),
            alertedContent: alertedContent.reverse(),
            direction: direction === ASCENDING ? DESCENDING : ASCENDING,
        })
    }

    render() {
        const { shownColumn, column, direction } = this.state
        const { highlighted, alertedContent, nonAlertedContent } = this.state
        return [
            <Table
                key='header'
                style={style.COMPACT.TABLE}
                fixed
                celled
                sortable
                structured
                selectable
                textAlign="center"
                collapsing
                color="grey"
                compact
                size="small"
                inverted>

                <Table.Header>
                    <Table.Row>
                        {shownColumn.map(headerValue => (
                            <Table.HeaderCell
                                key={headerValue}
                                onClick={this.handleSort(headerValue)}
                                sorted={column === headerValue ? direction : null}
                                style={style.COMPACT.ROW}
                                width={headerValue === UAV_PROP.PROGRESS ? 4 : 2}
                                textAlign="center">
                                {headerValue}
                            </Table.HeaderCell>
                        ))}
                    </Table.Row>
                </Table.Header>
            </Table>,
            <Container key='container' style={{
                width: 'auto', height: 175,
                overflowY: 'scroll', overflowX: 'hidden'
            }}>
                <Table
                    key='content'
                    style={style.COMPACT.TABLE}
                    fixed
                    celled
                    sortable
                    structured
                    selectable
                    textAlign="center"
                    collapsing
                    color="grey"
                    compact
                    size="small"
                    inverted>
                    <Table.Body>
                        {alertedContent && alertedContent.map(arr => (
                            <Table.Row active={highlighted !== null && highlighted.id === arr.id}
                                key={arr['id']}
                                onDoubleClick={this.props.onDoubleClick(arr)}>
                                {shownColumn.map(key => (
                                    <Table.Cell
                                        key={key}
                                        width={key === UAV_PROP.PROGRESS ? 4 : 2}
                                        verticalAlign="middle"
                                        style={style.COMPACT.ROW}
                                        textAlign="center">

                                        {key === UAV_PROP.PROGRESS ?
                                            <Progress progress
                                                style={style.COMPACT.PROGRESS}
                                                size="small"
                                                color="red"
                                                percent={arr[key]} />
                                            :
                                            arr[key]
                                        }
                                    </Table.Cell>
                                ))}
                            </Table.Row>
                        ))}
                        {nonAlertedContent && nonAlertedContent.map(arr => (
                            <Table.Row active={highlighted !== null && highlighted.id === arr.id}
                                key={arr['id']}
                                onDoubleClick={this.props.onDoubleClick(arr)}>
                                {shownColumn.map(key => (
                                    <Table.Cell
                                        key={key}
                                        width={key === UAV_PROP.PROGRESS ? 4 : 2}
                                        verticalAlign="middle"
                                        style={style.COMPACT.ROW}
                                        textAlign="center">

                                        {key === UAV_PROP.PROGRESS ?
                                            <Progress progress
                                                style={style.COMPACT.PROGRESS}
                                                size="small"
                                                color="green"
                                                percent={arr[key]} />
                                            :
                                            arr[key]
                                        }
                                    </Table.Cell>
                                ))}
                            </Table.Row>
                        ))}
                    </Table.Body>
                </Table>
            </Container>
        ]
    }
}

export default InfoTable