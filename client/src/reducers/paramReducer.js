import * as actionTypes from '../actions/actionTypes'

const AIRSPACE = ['PLANARGRAPH']

const FLIGHT_SCHEDULE = ['RANDOM']

export const PARAMS = [
    { placeholder: "Airspace", content: 'open', value: 'PLANARGRAPH', key: "airspaceType", options: ['PLANARGRAPH', 12, 22, 32] },
    { placeholder: "Flight schedule", content: 'open', value: 'RANDOM', key: "flightScheduleType", options: ['RANDOM', 1, 2, 3] },
    { placeholder: "UAVs", content: 'slider', value: 15, minVal: 1, maxVal: 100, key: 'UAV' },
    { placeholder: "Avg speed", content: 'slider', value: 20, minVal: 1, maxVal: 50, key: 'averageSpeed' },
    { placeholder: "Sim rate", content: 'slider', value: 1, minVal: 1, maxVal: 10, key: 'simulationRate' },
]

const DEFAULT_STATE = {
    data: {},
    submit: false,
    stop: true,
    ws: false,
    pause: false,
}


export default function (state = DEFAULT_STATE, action) {
    switch (action.type) {
        case actionTypes.ON_SUBMIT_PARAM:
            return { ...state, data: action.data, stop: false }
        // case actionTypes.ON_SENT_PARAM:
        //     return { ...state, submit: false }
        case actionTypes.ON_STOP_PARAM:
            return { ...state, stop: true }
        case actionTypes.ON_TOOGLE_PAUSE_PARAM:
            return { ...state, pause: !state.pause }
        case actionTypes.ON_CONNECT_WS:
            return { ...state, ws: true }
        case actionTypes.ON_CLOSE_WS:
            return { ...state, ws: false }
        default:
            return state
    }
}