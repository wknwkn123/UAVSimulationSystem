import React, { Component } from 'react'

import { Label } from 'semantic-ui-react'

const popUpStyle = () => (
    {
        position: 'absolute',
        top: -5,
        right: -30
    }
)

class SliderLabeled extends Component {

    getValue = () => {
        return this.state.value
    }

    constructor(props){
        super(props)
        const { min, max, defaultValue, onChange, labeled } = this.props
        var minVal = min ? min : 0
        var maxVal = max ? max : 100
        var defVal = defaultValue ? defaultValue : 0
        this.state = {
            onChange: false, 
            defaultValue,
            min: minVal,
            max: maxVal,
            labeled,
            onChangeFunc: onChange,
            value: defaultValue
        }
    }

    render() {
        const { min, max, value, defaultValue, onChangeFunc, labeled } = this.state
        return (
            <div id="slider" style={{ display: 'flex', position: 'relative', width: '90%', margin: 'auto' }}>
                <input type='range'
                    ref={x => this.slider = x}
                    style={{ width: '90%', margin: 'auto' }}
                    min={min} max={max}
                    onMouseEnter={() => this.setState({ onChange: true })}
                    onMouseLeave={() => this.setState({ onChange: false })}
                    step={1}
                    defaultValue={defaultValue}
                    onChange={(e) => {
                        if(onChangeFunc){
                            onChangeFunc(e.target.value)
                        }
                        this.setState({ onChange: true })
                        this.setState({ value: e.target.value })
                    }} />
                {this.state.onChange && !labeled &&
                    <Label
                        size='tiny'
                        pointing='left'
                        style={popUpStyle()}
                        content={this.getValue()} />
                }
                {labeled && <Label
                    style={{ padding: 3, width: 30, textAlign: 'center' }}
                    size='tiny'
                    content={this.getValue()} />}
            </div>
        )
    }
}

export default SliderLabeled