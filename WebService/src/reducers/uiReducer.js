import * as actionTypes from '../actions/actionTypes'

const DEFAULT_STATE = {
  showSpinner: false,
  modal: {
    editor: { show: false, data: null, index: null },
    file: { show: false, data: null },
    delete_confirmation: { show: false, data: null, index: null }
  }
}

export default function (state = DEFAULT_STATE, action) {
  switch (action.type) {
    case actionTypes.SHOW_SPINNER:
      return { ...state, showSpinner: true }
    case actionTypes.HIDE_SPINNER:
      return { ...state, showSpinner: false }
    case actionTypes.ON_EDIT_MODAL_STATE:
      return { ...state, modal: { ...state.modal, ...action.payload.state } }
    default:
      return state
  }
}
