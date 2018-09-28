import * as actionTypes from '../actions/actionTypes'

const EDGE = [{ source: 'node0', target: 'node1' }]

const DEFAULT_STATE = {
  data: EDGE
}

export default function (state = DEFAULT_STATE, action) {
  switch (action.type) {
    case actionTypes.ON_OPEN_FILE:
      return { ...state, data: action.payload.edges }
    case actionTypes.ON_ADD_EDGE:
      return {
        ...state,
        data: [...state.data, action.payload.edge]
      }
    case actionTypes.ON_EDIT_EDGE:
      return {
        ...state,
        data: [
          ...state.data.slice(0, action.payload.index),
          action.payload.edge,
          ...state.data.slice(action.payload.index + 1)
        ]
      }
    case actionTypes.ON_REMOVE_EDGE:
      return {
        ...state,
        data: [...state.data.slice(0, action.payload.index), ...state.data.slice(action.payload.index + 1)]
      }
    case actionTypes.ON_REMOVE_EDGE_BULK:
      var arr = action.payload.edgeIndexes
      if (arr.length === 0) return state
      return {
        ...state,
        data: state.data.filter((data, index) => !arr.includes(index))
      }
    case actionTypes.ON_CREATE_NEW:
      return { ...DEFAULT_STATE }
    case actionTypes.ON_EDIT_NODE:
      let { id } = action.payload.previousNode
      let newID = action.payload.node.id
      let newEdgeData = state.data.reduce((acc, value) => {
        let { source, target } = value
        var newSource = source === id ? newID : source
        var newTarget = target === id ? newID : target
        acc.push({ source: newSource, target: newTarget })
        return acc
      }, [])
      return { ...state, data: newEdgeData }
    default:
      return state
  }
}
