const BASIC = 'basic'
const STREET = 'street'
const BRIGHT = 'bright'
const LIGHT = 'light'
const DARK = 'dark'
const SATELLITE = 'satellite'

class MapboxStyle {
    constructor(props) {
        this.style = props ? props : DARK
    }

    static allStyle() {
        return [
            BASIC,
            STREET,
            BRIGHT,
            LIGHT,
            DARK,
            SATELLITE
        ]
    }

    static getOptions() {
        return this.allStyle().map((style) => (
            { key: style, value: style, text: style }
        ))
    }


    styleParser = (style) => ('mapbox://styles/mapbox/' + style + '-v9')

    get value() {
        return this.styleParser(this.style)
    }

}

export default MapboxStyle