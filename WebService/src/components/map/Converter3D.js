const DEFAULT_HEIGHT = 164
const DEFAULT_BASE_HEIGHT = 165

export const convertNode = node => {
  return {
    type: 'Feature',
    properties: {
      height: node.height || DEFAULT_HEIGHT,
      base_height: node.base_height || DEFAULT_BASE_HEIGHT,
      color: '#00FFFF',
      opacity: 1
    },
    geometry: {
      coordinates: squarePoint(node.position, node.length || 50),
      type: 'Polygon'
    },
    id: 'cirlce' + node.id
  }
}

export const convertEdge = ({ source, target }) => {
  var point1 = [source.position.lng, source.position.lat]
  var point2 = [target.position.lng, target.position.lat]
  var point3 = destinationPoint(90, point2, 10)
  var point4 = destinationPoint(90, point1, 10)
  return {
    type: 'Feature',
    properties: {
      height: DEFAULT_HEIGHT,
      base_height: DEFAULT_BASE_HEIGHT,
      color: '#FFFFFF',
      opacity: 1
    },
    geometry: {
      coordinates: [[point1, point2, point3, point4, point1]],
      type: 'Polygon'
    },
    id: 'edge-' + source.id + '-' + target.id
  }
}

export const generateLayer = source => ({
  id: source,
  type: 'fill-extrusion',
  source: source,
  paint: {
    'fill-extrusion-color': {
      // Get the fill-extrusion-color from the source 'color' property.
      property: 'color',
      type: 'identity'
    },
    'fill-extrusion-height': {
      // Get fill-extrusion-height from the source 'height' property.
      property: 'height',
      type: 'identity'
    },
    'fill-extrusion-base': {
      // Get fill-extrusion-base from the source 'base_height' property.
      property: 'base_height',
      type: 'identity'
    },
    // Make extrusions slightly opaque for see through indoor walls.
    'fill-extrusion-opacity': 1
  }
})

export const generateSource = features => ({
  type: 'geojson',
  data: generateData(features)
})

export const generateData = features => ({
  type: 'FeatureCollection',
  features
})

function squarePoint ({ lng, lat }, distance = 5) {
  return [
    [
      destinationPoint(45, [lng, lat], distance),
      destinationPoint(45 * 3, [lng, lat], distance),
      destinationPoint(45 * 5, [lng, lat], distance),
      destinationPoint(45 * 7, [lng, lat], distance),
      destinationPoint(45, [lng, lat], distance)
    ]
  ]
}

function destinationPoint (bearing, point, distance = 25) {
  var radius = 6371e3

  var δ = Number(distance) / radius // angular distance in radians
  var θ = Number(bearing) * Math.PI / 180

  var φ1 = Number(point[1]) * Math.PI / 180
  var λ1 = Number(point[0]) * Math.PI / 180

  var sinφ1 = Math.sin(φ1),
    cosφ1 = Math.cos(φ1)
  var sinδ = Math.sin(δ),
    cosδ = Math.cos(δ)
  var sinθ = Math.sin(θ),
    cosθ = Math.cos(θ)

  var sinφ2 = sinφ1 * cosδ + cosφ1 * sinδ * cosθ
  var φ2 = Math.asin(sinφ2)
  var y = sinθ * sinδ * cosφ1
  var x = cosδ - sinφ1 * sinφ2
  var λ2 = λ1 + Math.atan2(y, x)

  var lng = (λ2 * 180 / Math.PI + 540) % 360 - 180
  var lat = φ2 * 180 / Math.PI

  return [lng, lat]
}
