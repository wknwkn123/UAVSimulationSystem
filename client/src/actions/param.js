import * as actionTypes from './actionTypes'

export const onSubmitParam = (data) => (
    { type: actionTypes.ON_SUBMIT_PARAM, data }
)

export const onSentParam = () => (
    { type: actionTypes.ON_SENT_PARAM }
)

export const onStopSimulation = () => (
    { type: actionTypes.ON_STOP_PARAM }
)

export const onTooglePauseParam = () => (
    { type: actionTypes.ON_TOOGLE_PAUSE_PARAM }
)

export const onWSConnected = () => (
    { type: actionTypes.ON_CONNECT_WS }
)

export const onWSDisconnected = () => (
    { type: actionTypes.ON_CLOSE_WS }
)