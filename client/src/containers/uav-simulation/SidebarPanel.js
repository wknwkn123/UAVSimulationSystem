import React, { Component } from 'react'

import { Sidebar, Container, Icon, Button } from 'semantic-ui-react'
import ParamContainer from './ParamContainer'

import './style.scss'
class SidebarPanel extends Component {
  state = {
    show: true
  }

  renderSidebarTooglerButton = () => {
    const { show } = this.state
    const icon = show ? 'angle double left' : 'angle double right'
    return (
      <Button
        className={'sidebar-button ' + (show || 'hide')}
        onClick={() => this.setState({ show: !show })}
        icon={icon}
      />
    )
  }

  render () {
    const { show } = this.state
    return (
      <div className={'sidebar-panel ' + (show || 'hide')}>
        {this.renderSidebarTooglerButton()}
        <ParamContainer />
      </div>
    )
  }
}

export default SidebarPanel
