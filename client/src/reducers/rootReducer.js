import { combineReducers } from 'redux'

import location from './locationReducer'
import node from './nodeReducer'
import edge from './edgeReducer'
import file from './fileReducer'
import uav from './uavReducer'
import ui from './uiReducer'

export default combineReducers({
  location,
  node,
  edge,
  file,
  uav,
  ui
})
