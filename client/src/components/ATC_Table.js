import React, { Component } from 'react';
import { connect } from 'react-redux';
import * as style from './style';

import { Container, Accordion, Icon } from 'semantic-ui-react';

import InfoTable from './common/InfoTable';
import { UAV_PROP } from './Constant';

import * as uav from '../actions/uav';
import * as map from '../actions/map';

class ATC_Table extends Component {
  render() {
    const { info, clickedUAV } = this.props.uav;
    if (info.length === 0) return null;
    return (
      <Container style={style.MapTable}>
        <InfoTable
          onDoubleClick={uav => () => {
            this.props.onClickUAVInfo(uav);
            this.props.shouldChangeCenter({ lat: uav.lat, lng: uav.lng });
          }}
          highlighted={clickedUAV}
          sortByAlert={true}
          content={info}
        />
      </Container>
    );
  }
}

const mapStateToProps = state => {
  const { uav } = state;
  return { uav };
};

export default connect(mapStateToProps, { ...uav, ...map })(ATC_Table);
