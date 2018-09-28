import * as actionTypes from '../actions/actionTypes'
import { showSpinner, hideSpinner } from '../actions/ui'

export const api = ({ dispatch }) => next => action => {
  if (action.type === actionTypes.ON_API_CALL) {
    const { method, url, body } = action.metadata
    const { onSuccess, onError = () => ({ type: 'api_call_error' }) } = action
    // console.log(method, url, body)
    dispatch(showSpinner())
    fetch(url, { method, body })
      .then(response => response.json())
      .then(data => {
        console.log(data)
        dispatch(onSuccess(data))
      })
      .catch(error => {
        console.log(error)
        dispatch(onError(error))
      })
    dispatch(hideSpinner())
  }
  return next(action)
}
