import React, { Component } from 'react'

import { Radio } from 'semantic-ui-react'

class RadioLis extends Component {

    getValue = () => (this.state.value)

    constructor(props) {
        super(props)
        this.state = {
            value: props.defaultValue
        }
    }

    handleChange = (e, { value }) => {
        // console.log(value)
        this.setState({ value })
    }

    render() {
        const { data } = this.props
        if (data === null) return null
        return data.map((obj) => {
            return <Radio
                style={{ fontSize: 'inherit' }}
                key={obj}
                label={obj}
                name={obj.toString()}
                value={obj}
                checked={this.state.value === obj}
                onChange={this.handleChange}
            />
        })
    }

}

export default RadioLis