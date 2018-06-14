import React, { Component } from 'react'
import { generateLayer, generateSource, generateData, convertEdge, convertNode } from './Converter3D'
import { NODE_LAYER, EDGE_LAYER, BUILDING_3D_LAYER } from './Constants'

/**
 * addLayer3D function is to add 3D layer to the map include the building layer and
 * node + edge default 3D layer
 * @param map
 * @param node
 * @param edge 
 */
export const addLayer3D = ({ map, node, edge }) => {
  // console.log('Map3D', map, node, edge)
  // Insert the layer beneath any symbol layer.
  var layers = map.getStyle().layers
  var labelLayerId
  for (var i = 0; i < layers.length; i++) {
    if (layers[i].type === 'symbol' && layers[i].layout['text-field']) {
      labelLayerId = layers[i].id
      break
    }
  }
  if (!map.getLayer(BUILDING_3D_LAYER)) {
    map.addLayer(
      {
        id: BUILDING_3D_LAYER,
        source: 'composite',
        'source-layer': 'building',
        filter: ['==', 'extrude', 'true'],
        type: 'fill-extrusion',
        minzoom: 15,
        paint: {
          'fill-extrusion-color': '#aaa',
          'fill-extrusion-height': ['interpolate', ['linear'], ['zoom'], 15, 0, 15.05, ['get', 'height']],
          'fill-extrusion-base': ['interpolate', ['linear'], ['zoom'], 15, 0, 15.05, ['get', 'min_height']],
          'fill-extrusion-opacity': 0.6
        }
      },
      labelLayerId
    )
  }

  const nodeData = node.map(x => convertNode(x))
  const edgeData = edge.map(({ source, target }) => {
    const sourceNode = node.find(k => k.id === source)
    const targetNode = node.find(k => k.id === target)
    return convertEdge({ source: sourceNode, target: targetNode })
  })
  // console.log(nodeData, edgeData)
  const LAYERS = [{ layer: NODE_LAYER, data: nodeData }, { layer: EDGE_LAYER, data: edgeData }]
  LAYERS.forEach(({ layer, data }) => {
    if (map.getSource(layer)) {
      map.getSource(layer).setData(generateData(data))
    } else {
      map.addSource(layer, generateSource(data))
    }
    if (!map.getLayer(layer)) {
      map.addLayer(generateLayer(layer))
    }
  })
}

export default addLayer3D
   