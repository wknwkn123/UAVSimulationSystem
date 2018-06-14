import * as actionTypes from './actionTypes'

/**
 * onReceiveUAVList function is to receive bulk uav list then store it
 * to the client's state
 * @param {*} uav 
 */
export const onReceiveUAVList = uav => ({
  type: actionTypes.ON_UPDATE_UAV_LIST,
  payload: { data: uav }
})

/**
 * onSendParam function is to send param to the WS
 * @param {*} type 
 * @param {*} data 
 */
export const onSendParam = (type, data) => ({
  type: actionTypes.ON_SEND_PARAM,
  payload: { type, data }
})
