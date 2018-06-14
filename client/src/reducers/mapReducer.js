import * as actionTypes from '../actions/actionTypes'

import MapboxStyle from '../models/MapboxStyle'

const DEFAULT_CENTER = {
    SG_CENTER: {
        lat: 1.290270,
        lng: 103.851959
    },
    NYC_CENTER: {
        lat: 40.7135,
        lng: -74.0066
    }
}

const DEFAULT_STATE = {
    center: DEFAULT_CENTER.SG_CENTER,
    zoomLevel: 12,
    brightness: 100,
    mode3D: false,
    pitch: 0,
    bearing: 0,
    mapStyle: new MapboxStyle(),
    graph: null
}

export default function (state = DEFAULT_STATE, action) {
    switch (action.type) {
        case actionTypes.CHANGE_MAP_POSITION:
            return {
                ...state,
                center: action.center,
                zoomLevel: action.zoomLevel,
                pitch: action.pitch,
                bearing: action.bearing
            }
        case actionTypes.CHANGE_MAP_BRIGHTNESS:
            return { ...state, brightness: action.brightness }
        case actionTypes.RESET_MAP_PITCH:
            return { ...state, pitch: 0 }
        case actionTypes.RESET_MAP_BEARING:
            return { ...state, bearing: 0 }
        case actionTypes.CHANGE_CENTER:
            return {
                ...state,
                center: action.center,
            }
        case actionTypes.ON_LOAD_GRAPH:
            return { ...state, graph: action.graph }
        case actionTypes.CHANGE_MAP_TERRAIN:
            return { ...state, mapStyle: action.style }
        case actionTypes.ADD_3D_LAYER:
            return { ...state, mode3D: !state.mode3D }
        default:
            return state;
    }
}