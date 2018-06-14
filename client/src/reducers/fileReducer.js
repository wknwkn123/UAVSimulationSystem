import * as actionTypes from '../actions/actionTypes'

const DEFAULT_STATE = {
    filename: null,
    content: null,
    // 'test set it to null after testing',
    reset: false,
    currentFilename: null,
}

export default function (state = DEFAULT_STATE, action) {
    switch (action.type) {
        case actionTypes.CLOSE_OPEN_FILE_MODAL:
            return { ...state, filename: null }
        case actionTypes.CLOSE_SAVE_FILE_MODAL:
            return { ...state, filename: null }
        case actionTypes.ON_CHANGE_FILE_SELECTED:
            return { ...state, filename: action.filename }
        case actionTypes.OPEN_FILE_SIMULATION:
            return {
                ...state, currentFilename: state.filename,
                content: state.filename, filename: null
            }
        case actionTypes.SAVE_FILE_SIMULATION:
            return {
                ...state, currentFilename: state.filename,
                filename: null
            }
        default:
            return state
    }
}