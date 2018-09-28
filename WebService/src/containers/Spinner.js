import React, { Component } from 'react'

import { connect } from 'react-redux'

import { Dimmer, Loader } from 'semantic-ui-react'

class Spinner extends Component {
  render () {
    const { showSpinner } = this.props.ui
    if (!showSpinner) return null
    return (
      <Dimmer page active>
        <Loader>Loading ... </Loader>
      </Dimmer>
    )
  }
}

const mapStateToProps = state => {
  const { ui } = state
  return { ui }
}

const action = {}
export default connect(mapStateToProps, action)(Spinner)
