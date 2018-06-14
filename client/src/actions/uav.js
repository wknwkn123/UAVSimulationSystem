import * as actionTypes from './actionTypes'

export const onClickUAVInfo = (uav) => (
    {
        type: actionTypes.ON_CLICK_UAV,
        uav
    }
)

export const onAddUAV = (uav) => (
    { type: actionTypes.ADD_UAV, uav }
)