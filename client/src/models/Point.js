import Encoder from './Encoder'

const ID = 'id'
const LAT = 'lat'
const LNG = 'lng'

class Point {
    constructor(props) {
        const { id, lat, lng, x, y, z } = props

        if (lat && lng) {
            this.lat = lat
            this.lng = lng
            var encoded = Encoder.XY(this)
            this.x = encoded.x
            this.y = encoded.y
        } else if (x && y) {
            this.x = x
            this.y = y
            var encoded = Encoder.LngLat(this)
            this.lat = encoded.lat
            this.lng = encoded.lng
        }
        this.id = id
        this.z = z
    }

    set lngLat(coor) {
        this.lat = coor[1]
        this.lng = coor[0]
        var { x, y } = Encoder.XY(this)
        this.x = x
        this.y = y
    }

    get lngLat() { return [this.lng, this.lat] }

    get xy() { return [this.x, this.y] }

    static squarePoint(point, distance = 5) {
        return [[
            Point.destinationPoint(45, point.lngLat, distance),
            Point.destinationPoint(45 * 3, point.lngLat, distance),
            Point.destinationPoint(45 * 5, point.lngLat, distance),
            Point.destinationPoint(45 * 7, point.lngLat, distance),
            Point.destinationPoint(45, point.lngLat, distance),
        ]]
    }

    static destinationPoint(bearing, point, distance = 25) {
        var radius = 6371e3

        var δ = Number(distance) / radius; // angular distance in radians
        var θ = Number(bearing) * Math.PI / 180;

        var φ1 = Number(point[1]) * Math.PI / 180;
        var λ1 = Number(point[0]) * Math.PI / 180;

        var sinφ1 = Math.sin(φ1), cosφ1 = Math.cos(φ1);
        var sinδ = Math.sin(δ), cosδ = Math.cos(δ);
        var sinθ = Math.sin(θ), cosθ = Math.cos(θ);

        var sinφ2 = sinφ1 * cosδ + cosφ1 * sinδ * cosθ;
        var φ2 = Math.asin(sinφ2);
        var y = sinθ * sinδ * cosφ1;
        var x = cosδ - sinφ1 * sinφ2;
        var λ2 = λ1 + Math.atan2(y, x);

        var lng = (λ2 * 180 / Math.PI + 540) % 360 - 180
        var lat = φ2 * 180 / Math.PI
        
        return [lng, lat]
    }

    get infoProps() {
        return [ID, LAT, LNG]
    }

    get geoJSON() {
        return {
            "type": "Feature",
            "geometry": {
                "type": "Point",
                "coordinates": this.lngLat
            },
            "properties": {
                "title": this.id,
                "obj": this
            }
        }
    }
}

export default Point