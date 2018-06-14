import Point from './Point'

const ALERT = 'alert'
const PROGRESS = 'progress'
const BATTERY = 'battery'
const REVENUE = 'revenue'
const COLLISION_RISK = 'collision_risk'

class UAV extends Point {
    constructor(props) {
        super(props)

        const { alert, progress, battery, revenue, direction } = props
        this.alert = alert ? alert : false
        this.progress = progress ? progress : '100'
        this.battery = battery ? battery : '100%'
        this.revenue = revenue ? revenue : 0
        this.direction = direction ? direction : null
        this.collision_risk = 0
    }

    compareTo = ({ comp, prop }) => {
        if (typeof this[prop] === 'string') {
            var strA = this[prop].toLowerCase(),
                strB = comp[prop].toLowerCase()
            if (strA < strB)
                return -1
            if (strA > strB)
                return 1
            return 0
        }
        return this[prop] - comp[prop]
    }

    get infoProps() {
        return [
            ...super.infoProps,
            PROGRESS,
            BATTERY,
            REVENUE,
            COLLISION_RISK
        ]
    }

    get geoJSON() {
        return {
            ...super.geoJSON,
            properties: {
                ...super.geoJSON.properties,
                obj: this
            }
        }
    }

    static separateUAVContent(content) {
        var alerted = content.reduce(function (allContent, temp) {
            if (temp.alert)
                allContent.push(temp)
            return allContent
        }, [])

        var nonAlerted = content.reduce(function (allContent, temp) {
            if (!temp.alert)
                allContent.push(temp)
            return allContent
        }, [])

        return { alerted, nonAlerted }
    }

    static separatedGeoJSON(content) {
        const { alerted, nonAlerted } = UAV.separateUAVContent(content)
        const alertedUAV_GeoJSON = {
            "type": "FeatureCollection",
            "features": alerted.map(uav => (uav.geoJSON))
        }
        const nonAlertedUAV_GeoJSON = {
            "type": "FeatureCollection",
            "features": nonAlerted.map(uav => (uav.geoJSON))
        }
        return { alertedUAV_GeoJSON, nonAlertedUAV_GeoJSON }
    }
}

export default UAV