import React, { Component } from 'react'
import PropTypes from 'prop-types'

import { Dropdown } from 'semantic-ui-react'
import { SELECT_FILE } from './Constant'

import * as storage from '../../actions/storage'

class FileDropdown extends Component {

    getValue = () => {
        return this.dropdown.state.value
    }

    state = {
        options:
            storage.getIndexFile() === null ? [] : storage.getIndexFile().map((value) => (
                { key: value, text: value, value }
            ))
    }

    handleAddition = (e, { value }) => {
        this.setState({
            options: [{ text: value, value }, ...this.state.options],
        })
    }

    render() {
        const { placeholder, onChange, allowAdditions, options } = this.props
        if (options) {
            var opt = this.props.options.map((opt) => (
                { key: opt, text: opt, value: opt }
            ))
        }
        return (
            <Dropdown
                style={{ fontSize: '0.8em' }}
                ref={(dropdown) => this.dropdown = dropdown}
                fluid
                scrolling
                search
                selection
                closeOnChange
                onChange={(event, { value }) => {
                    if(onChange)
                        onChange(value)
                }}
                allowAdditions={allowAdditions}
                onAddItem={this.handleAddition}
                options={options ? opt : this.state.options}
                placeholder={placeholder ? placeholder : SELECT_FILE} />
        )
    }
}

export default FileDropdown