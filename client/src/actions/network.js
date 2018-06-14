export const BASE_URL = 'http://0.0.0.0:8080/'

export const URL = {
  GRAPH: 'api/graph/',
  STORAGE: 'storage/'
}

export const GET = 'GET'
export const POST = 'POST'
export const PUT = 'PUT'

export const fetchAPI = ({ type, url, body }) => {
  return new Promise((resolve, reject) => {
    var x = new XMLHttpRequest()
    x.open(type, url)
    x.onload = () => resolve(JSON.parse(x.responseText))
    x.onerror = () => reject(x.statusText)
    x.send(body)
  })
}

/**
 * This function is to load the graph from the specified file
 */
export const loadGraphFromFile = filename => ({
  method: POST,
  url: BASE_URL + URL.GRAPH + URL.STORAGE,
  body: JSON.stringify({
    type: 'getfile',
    name: filename
  })
})

/**
 * This function is to generate edges from the given nodes
 */
export const generateGraph = ({ graph }) => ({
  method: POST,
  url: BASE_URL + URL.GRAPH,
  body: JSON.stringify({
    graph
  })
})

/**
 * This function is to save the graph to the specified file
 */
export const saveGraphToFile = (graph, name) => ({
  method: POST,
  url: BASE_URL + URL.GRAPH + URL.STORAGE,
  body: JSON.stringify({
    name,
    graph,
    type: 'savefile'
  })
})

/**
 * This function is to get all the file list from the server
 */
export const loadFileList = () => ({
  method: POST,
  url: BASE_URL + URL.GRAPH + URL.STORAGE,
  body: JSON.stringify({
    type: 'getlist'
  })
})
