import Point from './Point'

class Edge {
    constructor(props) {
        const { id, node1, node2 } = props

        this.id = id
        this.node1 = node1
        this.node2 = node2
        this.midPoint = new Point({
            lat: (parseFloat(this.node1.lat) + parseFloat(this.node2.lat)) / 2,
            lng: (parseFloat(this.node1.lng) + parseFloat(this.node2.lng)) / 2
        })
    }

    get lngLat() {
        return [
            this.node1.lngLat,
            this.node2.lngLat
        ]
    }

    get source() {
        return this.node1.id
    }

    get target() {
        return this.node2.id
    }

    get geoJSON() {
        return {
            "type": "Feature",
            "geometry": {
                "type": "LineString",
                "coordinates": this.lngLat
            },
            "properties": {
                "title": this.id
            }
        }
    }

}

export default Edge