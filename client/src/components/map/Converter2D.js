const Node2DConverter = (color, { id, position }) => ({
  type: 'Feature',
  geometry: {
    type: 'Point',
    coordinates: [position.lng, position.lat]
  },
  properties: { id, color }
})

const generateSource = ({ node, edge }, { activeNodeID, clickedNodeID } = {}) => {
  var nodeData, edgeData
  var activeNodes = []
  if (activeNodeID) activeNodes.push(activeNodeID)
  if (clickedNodeID) activeNodes.push(clickedNodeID)
  if (node.length > 0) {
    nodeData = generateNodeSource(node, activeNodes)
    edgeData = null
    if (edge.length > 0) {
      edgeData = generateEdgeSource(node, edge)
    }
  }
  return { nodeData, edgeData }
}

const generateNodeSource = (nodes, activeNodes, defaultColor = '#00FFFF', highlightColor = '#00BBFF') => {
  return {
    type: 'FeatureCollection',
    features: nodes.map(node => {
      var nodeColor = defaultColor
      if (activeNodes.includes(node.id)) return Node2DConverter(highlightColor, node)
      return Node2DConverter(nodeColor, node)
    })
  }
}

const generateEdgeSource = (nodes, edges) => {
  return {
    type: 'FeatureCollection',
    features: edges.map(({ source, target }) => {
      const sourceNode = nodes.find(k => k.id === source)
      const targetNode = nodes.find(k => k.id === target)
      return Edge2DConverter({ source: sourceNode, target: targetNode })
    })
  }
}

const Edge2DConverter = ({ source, target }) => ({
  type: 'Feature',
  geometry: {
    type: 'LineString',
    coordinates: [[source.position.lng, source.position.lat], [target.position.lng, target.position.lat]]
  }
})

export const generateUAVSource = (uav, activeUAV = null) => {
  var nonAlertedUAV = uav.reduce(function (allContent, temp) {
    if (!temp.alert) allContent.push(temp)
    return allContent
  }, [])

  var alertedUAV = uav.reduce(function (allContent, temp) {
    if (temp.alert) allContent.push(temp)
    return allContent
  }, [])

  var innerData = generateNodeSource(uav, [], '#ABDC64')
  var alertOuterData = generateNodeSource(nonAlertedUAV, [activeUAV], '#FFFF00', '#FFFF00')
  var normalOuterData = generateNodeSource(alertedUAV, [activeUAV], '#FF0000', '#FF0000')
  return {
    innerData,
    alertOuterData,
    normalOuterData
  }
}

export default generateSource
