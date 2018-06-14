import UAV from './UAV'

class MessageParser {

    parseCoor(coor) {
        return { x: parseFloat(coor[0]), y: parseFloat(coor[1]), z: parseFloat(coor[2]) }
    }

    constructor(props) {
        // console.log(props)

        const { coor } = props.coordinate
        const { id, direction, startPoint, endPoint } = props
        this.id = id
        this.direction = direction
        this.startPoint = startPoint
        this.endPoint = endPoint

        var { x, y, z } = this.parseCoor(coor)
        this.x = x
        this.y = y
        this.z = z
    }

    get UAV() {
        const { id, direction, startPoint, endPoint } = this
        const { x, y, z } = this
        return new UAV({
            id, direction,
            startPoint, endPoint,
            x, y, z
        })
    }
}

export default MessageParser