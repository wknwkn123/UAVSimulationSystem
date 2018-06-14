import proj4 from 'proj4'

class Encoder {
    static XY({ lat, lng }) {
        proj4.defs("EPSG:3414",
            "+proj=tmerc +lat_0=1.366666666666667 +lon_0=103.8333333333333 +k=1 +x_0=28001.642 +y_0=38744.572 +ellps=WGS84 +units=m +no_defs"
        )
        var proj = proj4('EPSG:4326', 'EPSG:3414', [lng, lat])
        return {
            x: parseFloat(proj[0].toFixed(6)),
            y: parseFloat(proj[1].toFixed(6))
        }
    }

    static LngLat({ x, y }) {
        proj4.defs("EPSG:3414",
            "+proj=tmerc +lat_0=1.366666666666667 +lon_0=103.8333333333333 +k=1 +x_0=28001.642 +y_0=38744.572 +ellps=WGS84 +units=m +no_defs"
        )
        var proj = proj4('EPSG:3414', 'EPSG:4326', [x, y])
        return {
            lng: parseFloat(proj[0].toFixed(6)),
            lat: parseFloat(proj[1].toFixed(6))
        }
    }
}

export default Encoder