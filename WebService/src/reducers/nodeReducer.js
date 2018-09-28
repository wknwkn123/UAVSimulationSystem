import * as actionTypes from '../actions/actionTypes'

const NODE = [
  {
    id: 'node0',
    position: {
      lat: 1.29027,
      lng: 103.851959
    }
  },
  {
    id: 'node1',
    position: {
      lat: 1.39027,
      lng: 103.851959
    }
  }
]

const DEFAULT_STATE = {
  data: NODE,
  counter: 100
}

export default function (state = DEFAULT_STATE, action) {
  const { data } = state
  switch (action.type) {
    case actionTypes.ON_OPEN_FILE:
      return { ...state, data: action.payload.nodes }
    case actionTypes.ON_ADD_NODE:
      return {
        ...state,
        data: [...state.data, action.payload.node],
        counter: state.counter + 1
      }
    case actionTypes.ON_EDIT_NODE:
      return {
        ...state,
        data: [
          ...state.data.slice(0, action.payload.index),
          action.payload.node,
          ...state.data.slice(action.payload.index + 1)
        ]
      }
    case actionTypes.ON_REMOVE_NODE:
      return {
        ...state,
        data: [...state.data.slice(0, action.payload.index), ...state.data.slice(action.payload.index + 1)]
      }
    case actionTypes.ON_CREATE_NEW:
      return { ...DEFAULT_STATE }
    default:
      return state
  }
}
