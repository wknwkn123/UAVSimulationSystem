import Point from './Point'
import Edge from './Edge'
import GeoJSON_3D from './GeoJSON_3D'
class Graph {
    constructor(props) {
        const { directed, metadata, nodes, edges } = props.graph

        this.directed = directed
        this.metadata = metadata

        this.nodes = nodes.map(node => {
            const { x, y } = node["meta-data"]
            const { id } = node
            return new Point({ id, x, y })
        })

        this.hashNode = {}
        this.nodes.forEach(node => {
            this.hashNode[node.id] = node
        })

        this.edges = edges.map(edge => {
            const { source, target } = edge
            var node1 = this.hashNode[source]
            var node2 = this.hashNode[target]
            return new Edge({ id: source + target, node1, node2 })
        })
    }

    get geoJSON() {
        const nodesGeoJSON = {
            "type": "FeatureCollection",
            "features": this.nodes.map(node => (node.geoJSON))
        }
        const edgesGeoJSON = {
            "type": "FeatureCollection",
            "features": this.edges.map(edge => (edge.geoJSON))
        }
        return { nodesGeoJSON, edgesGeoJSON }
    }

    get geoJSON_3D() {
        const nodesGeoJSON_3D = {
            "type": "FeatureCollection",
            "features": this.nodes.map(node => (GeoJSON_3D.node_JSON_3D(node)))
        }
        const edgesGeoJSON_3D = {
            "type": "FeatureCollection",
            "features": this.edges.map(edge => (GeoJSON_3D.polyline_JSON_3D(edge)))
        }
        return { nodesGeoJSON_3D, edgesGeoJSON_3D }
    }
    
}

export default Graph