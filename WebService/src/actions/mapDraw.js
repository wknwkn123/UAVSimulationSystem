import * as actionTypes from './actionTypes'
import { clientToserver } from './graphAdapter'
import { generateGraph } from './network'

export const onAddNode = node => ({
  type: actionTypes.ON_ADD_NODE,
  payload: { node }
})

export const onRemoveNode = (node, index) => ({
  type: actionTypes.ON_REMOVE_NODE,
  payload: { node, index }
})

export const onEditNode = (previousNode, node, index) => ({
  type: actionTypes.ON_EDIT_NODE,
  payload: { previousNode, node, index }
})

export const onAddEdge = edge => ({
  type: actionTypes.ON_ADD_EDGE,
  payload: { edge }
})

export const onRemoveEdge = (edge, index) => ({
  type: actionTypes.ON_REMOVE_EDGE,
  payload: { edge, index }
})

export const onRemoveNodeAndEdgeBulk = (nodeIndex, edgeIndexArray) => dispatch => {
  dispatch(onRemoveEdgeBulk(edgeIndexArray))
  dispatch(onRemoveNode(null, nodeIndex))
}

export const onRemoveEdgeBulk = edgeIndexes => ({
  type: actionTypes.ON_REMOVE_EDGE_BULK,
  payload: { edgeIndexes }
})

export const onEditEdge = (edge, index) => ({
  type: actionTypes.ON_EDIT_EDGE,
  payload: { edge, index }
})

export const onGenerateEdge = node => ({
  type: actionTypes.ON_API_CALL,
  metadata: generateGraph(clientToserver({ node, edge: [] })),
  onSuccess: ({ graph }) => {
    const { nodes, edges } = serverToClient(graph)
    return {
      type: actionTypes.ON_LOAD_DATA,
      payload: { nodes, edges }
    }
  }
})
