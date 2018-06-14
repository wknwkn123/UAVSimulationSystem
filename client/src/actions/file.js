import * as actionTypes from './actionTypes'
import { loadGraphFromFile, saveGraphToFile, loadFileList, generateGraph } from './network'
import { serverToClient, clientToserver } from './graphAdapter'

/**
 * loadGraphToState is common function to load graph retrieved from server to
 * client state which uses graphAdapter serverToClient method
 * @param {*} filename 
 */
const loadGraphToState = filename => ({ graph }) => {
  const { nodes, edges } = serverToClient(graph)
  return {
    type: actionTypes.ON_OPEN_FILE,
    payload: { nodes, edges, filename }
  }
}

/**
 * doOpenFile function is to open file from server's file then parse it
 * to the client's state
 * @param {*} param0 
 */
export const doOpenFile = ({ filename }) => ({
  type: actionTypes.ON_API_CALL,
  metadata: loadGraphFromFile(filename),
  onSuccess: loadGraphToState(filename)
})

/**
 * doSaveFile function is to save the graph data to the server's file
 * @param graph which contains the graph state from client
 * @param filename
 * @param {*} param0 
 */
export const doSaveFile = ({ graph, filename }) => ({
  type: actionTypes.ON_API_CALL,
  metadata: saveGraphToFile(clientToserver(graph), filename)
})

/**
 * doGetFileList function is to fetch all filename lists from server
 */
export const doGetFileList = () => ({
  type: actionTypes.ON_API_CALL,
  metadata: loadFileList(),
  onSuccess: filelist => ({
    type: actionTypes.ON_LOAD_FILE_LIST,
    payload: { filelist: filelist.names }
  })
  })

/**
 * doCreateNewFile function is to generate new file which resets all
 * reducer to default state
 */
export const doCreateNewFile = () => ({
  type: actionTypes.ON_CREATE_NEW
})

/**
 * doGenerateEdges function is to load generated graph from server
 * @param {*} param0 
 */
export const doGenerateEdges = ({ graph, filename }) => ({
  type: actionTypes.ON_API_CALL,
  metadata: generateGraph(clientToserver(graph)),
  onSuccess: loadGraphToState(filename)
})

/**
 * loadGraph function is to load graph data to node and edge Reducer
 * -- Used by uav-simulation-app
 * @param {*} param0 
 */
export const loadGraph = ({ graph }) => {
  const { nodes, edges } = serverToClient(graph)
  return {
    type: actionTypes.ON_OPEN_FILE,
    payload: { nodes, edges }
  }
}