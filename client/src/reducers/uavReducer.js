import * as actionTypes from '../actions/actionTypes'

import UAV from '../models/UAV'

const CONTENT_TEST = [
    new UAV({ lat: 1.290270, lng: 103.851959, alert: true, id: "a", progress: '50', battery: '10%', revenue: 60 }),
    new UAV({ lat: 1.209200, lng: 103.851959, alert: false, id: "d", progress: '60', battery: '40%', revenue: 90 }),
    new UAV({ lat: 1.219300, lng: 103.851960, alert: false, id: "c", progress: '40', battery: '50%', revenue: 10 }),
    new UAV({ lat: 1.229400, lng: 103.851961, alert: true, id: "z", progress: '90', battery: '30%', revenue: 30 }),
    new UAV({ lat: 1.239500, lng: 103.851962, alert: false, id: "f", progress: '60', battery: '40%', revenue: 90 }),
    new UAV({ lat: 1.249600, lng: 103.851963, alert: false, id: "h", progress: '40', battery: '50%', revenue: 10 }),
    new UAV({ lat: 1.259700, lng: 103.851964, alert: true, id: "i", progress: '50', battery: '10%', revenue: 60 }),
    new UAV({ lat: 1.269800, lng: 103.851965, alert: false, id: "g", progress: '60', battery: '40%', revenue: 90 }),
    new UAV({ lat: 1.279900, lng: 103.851966, alert: false, id: "j", progress: '40', battery: '50%', revenue: 10 }),
]

const DEFAULT_STATE = {
    info: CONTENT_TEST,
    clickedUAV: null
}

export default function (state = DEFAULT_STATE, action) {
    switch (action.type) {
        case actionTypes.ON_CLICK_UAV:
            return { ...state, clickedUAV: action.uav }
        case actionTypes.ADD_UAV:
            var index = state.info.findIndex(k => k.id === action.uav.id)
            if (index !== -1)
                return {
                    ...state, info: [
                        ...state.info.slice(0, index),
                        ...state.info.slice(index + 1),
                        action.uav
                    ]
                }
            else
                return { ...state, info: [...state.info, action.uav] }
            return state
        case actionTypes.ON_STOP_PARAM:
            return { info: [], clickedUAV: null }
        default:
            return state;
    }
}