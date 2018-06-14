
import Point from './Point'

import { COLOR } from '../components/common/Constant'

const DEFAULT_HEIGHT = 164
const DEFAULT_BASE_HEIGHT = 165

class GeoJSON_3D {
    static node_JSON_3D(point) {
        return {
            "type": "Feature",
            "properties": {
                "height": DEFAULT_HEIGHT,
                "base_height": DEFAULT_BASE_HEIGHT,
                "color": COLOR.CYAN
            },
            "geometry": {
                "coordinates": Point.squarePoint(point, 50),
                "type": "Polygon"
            },
            "id": "cirlce" + point.id
        }
    }

    static drone_JSON_3D(point) {
        return {
            "type": "Feature",
            "properties": {
                "height": DEFAULT_HEIGHT - 10,
                "base_height": DEFAULT_BASE_HEIGHT + 10,
                "color": COLOR.UAV
            },
            "geometry": {
                "coordinates": Point.squarePoint(point, 15),
                "type": "Polygon"
            },
            "id": "cirlce" + point.id
        }
    }

    static polyline_JSON_3D(edge) {
        var point1 = edge.node1.lngLat
        var point2 = edge.node2.lngLat
        var point3 = Point.destinationPoint(90, point2, 10)
        var point4 = Point.destinationPoint(90, point1, 10)
        return {
            "type": "Feature",
            "properties": {
                "height": DEFAULT_HEIGHT,
                "base_height": DEFAULT_BASE_HEIGHT,
                "color": COLOR.WHITE
            },
            "geometry": {
                "coordinates": [[
                    point1,
                    point2,
                    point3,
                    point4,
                    point1
                ]],
                "type": "Polygon"
            },
            "id": "edge" + edge.node1.id + "-" + edge.node2.id
        }
    }
}

export default GeoJSON_3D