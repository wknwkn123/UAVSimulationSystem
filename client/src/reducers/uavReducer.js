import * as actionTypes from '../actions/actionTypes'

const DEFAULT_STATE = {
  data: [],
  param: {
    data: {},
    type: 'stop'
  }
}

export default function (state = DEFAULT_STATE, action) {
  switch (action.type) {
    case actionTypes.ON_UPDATE_UAV_LIST:
      return { ...state, data: action.payload.data }
    case actionTypes.ON_SEND_PARAM:
      return { ...state, param: { ...action.payload } }
    default:
      return state
  }
}
