import * as actionTypes from './actionTypes'

export const showSpinner = () => ({ type: actionTypes.SHOW_SPINNER })

export const hideSpinner = () => ({ type: actionTypes.HIDE_SPINNER })

export const onEditModalState = state => ({ type: actionTypes.ON_EDIT_MODAL_STATE, payload: { state } })
