import * as actionTypes from '../actions/actionTypes'

const DEFAULT_STATE = {
  filename: null,
  filelist: []
}

export default function (state = DEFAULT_STATE, action) {
  switch (action.type) {
    case actionTypes.ON_OPEN_FILE:
    case actionTypes.ON_SAVE_FILE:
    case actionTypes.ON_SAVE_AS_FILE:
      return { ...state, filename: action.payload.filename }
    case actionTypes.ON_CREATE_NEW:
      return { ...DEFAULT_STATE }
    case actionTypes.ON_LOAD_FILE_LIST:
      return { ...state, filelist: action.payload.filelist }
    default:
      return state
  }
}
