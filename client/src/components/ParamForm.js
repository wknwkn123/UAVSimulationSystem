import React, { Component } from 'react'
import { connect } from 'react-redux'

import { Container, Input, Label, Segment, Header, Button, Divider, Dropdown, Icon } from 'semantic-ui-react'
import ParamRow from './ParamRow'
import FileSelection from './common/FileDropdown'
import Slider from './common/SliderLabeled'
import RadioList from './common/RadioList'

import * as action from '../actions/param'
import * as style from './style'

import { PARAMS } from '../reducers/paramReducer'

class ParamForm extends Component {

    getAllParamValue = () => {
        var value = {}
        var isValid = true
        PARAMS.forEach((param) => {
            var item = this[param.key].getValue()
            value[param.key] = item
            if (item === "") isValid = false
        })
        return { value, isValid }
    }

    render() {
        const { id, left, right, contents, currentFilename, param } = this.props
        return (
            <Container style={{ ...style.ParamContainer, position: 'relative' }}>
                <Container style={{ ...style.MenuPopUpContent, margin: 5, padding: 5 }} >
                    {
                        PARAMS.map((paramDefault) => {
                            const { content } = paramDefault

                            const slider = () => <Slider
                                ref={x => this[paramDefault.key] = x}
                                labeled
                                defaultValue={paramDefault.value}
                                min={paramDefault.minVal}
                                max={paramDefault.maxVal} />

                            const file = () => (
                                <RadioList
                                    defaultValue={paramDefault.value}
                                    ref={x => this[paramDefault.key] = x}
                                    data={paramDefault.options}
                                />
                            )

                            var rightContent = content === 'slider' ? slider() : file()
                            return (
                                <ParamRow
                                    vertical={content !== 'slider'}
                                    key={paramDefault.key}
                                    id={paramDefault.key}
                                    left={<Label style={{ width: '95%', paddidng: 2 }} content={paramDefault.placeholder} />}
                                    right={rightContent} />
                            )
                        })
                    }
                    <Divider style={{ margin: 5 }} />
                    <Container style={{ ...style.MenuPopUpFooter, ...style.ContentRow, width: '100%', justifyContent: 'flex-end' }}>
                        {/* More Compact Button font size and padding */}
                        {/* <Button
                            size='tiny'
                            color='black'
                            onClick={this.props.onStopSimulation}
                            style={{ width: 'auto', height: 'auto', padding: 8 }}>Stop</Button> */}
                        {/* <Button
                            size='tiny'
                            color={param.pause ? "red" : "blue"}
                            onClick={this.props.onTooglePauseParam}
                            style={{ width: 'auto', height: 'auto', padding: 8 }}>
                            {param.pause ? "Pause" : "Resume"}
                        </Button> */}
                        <Button
                            // disabled={!param.ws}
                            size='tiny'
                            color={param.stop ? 'black' : 'green'}
                            onClick={() => { param.stop ? this.props.onSubmitParam(this.getAllParamValue().value) : this.props.onStopSimulation() }}
                            style={{ width: 'auto', height: 'auto', padding: 8 }}>
                            {param.stop ? 'Start' : 'Stop'}
                        </Button>
                    </Container>
                </Container>
            </Container >
        )
    }
}

const mapStateToProps = (state) => {
    const { currentFilename } = state.file
    const { param } = state
    return { currentFilename, param }
}

export default connect(mapStateToProps, action)(ParamForm)