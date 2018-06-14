import { combineReducers } from 'redux'

import map from './mapReducer'
import file from './fileReducer'
import uav from './uavReducer'
import param from './paramReducer'

export default combineReducers({
    map,
    uav,
    file,
    param
})