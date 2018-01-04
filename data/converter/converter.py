from pyproj import Proj, transform
import json

inProj = Proj(init='epsg:3414')
outProj = Proj(init='epsg:4326')

with open('result.json') as file:
    d = json.load(file)
    graph = d["graph"]
    nodes = graph["nodes"]
    # edges = graph["edges"]
    for index in range (len(nodes)):
        position = nodes[index]["meta-data"]
        x1, y1 = position["x"], position["y"]
        x2, y2 = transform(inProj, outProj, x1, y1)
        nodes[index]["meta-data"]["lat"] = y2
        nodes[index]["meta-data"]["lng"] = x2
    graph["nodes"] = nodes
    with open('result_converted.json', 'w') as result:
        json.dump(graph, result)
        result.close()
    file.close()
