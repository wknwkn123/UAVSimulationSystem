import proj4 from 'proj4'

export const serverToClient = ({ nodes, edges }) => {
  /**
   * Graph structure
   *
   * - nodes
   *  --> id
   *  --> meta-data --> lat, lng
   * - edges
   *  --> source (id)
   *  --> target (id)
   */
  console.log(nodes, edges)
  var nodeState = nodes.map(node => {
    var id = node['id']
    var { lat, lng } = node['meta-data']
    return { id, position: { lat, lng } }
  })
  return { nodes: nodeState, edges }
}

const getNodeXY = ({ lat, lng }) => {
  proj4.defs(
    'EPSG:3414',
    '+proj=tmerc +lat_0=1.366666666666667 +lon_0=103.8333333333333 +k=1 +x_0=28001.642 +y_0=38744.572 +ellps=WGS84 +units=m +no_defs'
  )
  var proj = proj4('EPSG:4326', 'EPSG:3414', [parseFloat(lng), parseFloat(lat)])
  return { x: proj[0], y: proj[1] }
}

export const clientToserver = ({ node, edge }) => {
  const nodeConverter = ({ id, lat, lng, x, y }) => ({
    'meta-data': {
      address: 'address',
      lat,
      lng,
      ...getNodeXY({ lat, lng })
    },
    id,
    type: 'transferable'
  })
  const edgeConverter = ({ target, source }) => ({
    target,
    source,
    'meta-data': {
      weight: 1423.99438201139
    }
  })
  var nodes = node.map(({ id, position }) =>
    nodeConverter({
      id,
      lat: position.lat,
      lng: position.lng
    })
  )
  console.log(nodes)
  var edges = edge.map(edge => edgeConverter(edge))
  const graph = {
    graph: {
      directed: false,
      metadata: {
        'coordinate-system': 'EPSG:3414'
      },
      nodes,
      edges
    }
  }
  console.log(graph)
  return graph
}
