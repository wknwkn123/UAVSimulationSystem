import sys
import json
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib import collections  as mc

def xfrange(start, stop, step):
    i = 0
    while start + i * step < stop:
        yield start + i * step
        i += 1

class point():
    def __init__(self, id, x, y, type, address):
        self.id = id
        self.x = x
        self.y = y
        self.type = type
        self.address = address

    def __iter__(self):
        for i in (x,y):
            yield i

FILE = sys.argv[1]
df = pd.read_json(FILE)
df.head()

nodes = df["graph"]["nodes"]
edges = df["graph"]["edges"]
points_x = [i["meta-data"]['x'] for i in nodes]
points_y = [i["meta-data"]['y'] for i in nodes]

points = [point(i["id"], i["meta-data"]['x'], i["meta-data"]['y'], i['type'], i["meta-data"]['address']) 
        for i in nodes]

points_map = {i.id:i for i in points}


edges_draw = [ 
    [
        (points_map[i['source']].x, points_map[i['source']].y), 
        (points_map[i['target']].x, points_map[i['target']].y)
    ] 
    for i in edges]


fig, ax = plt.subplots()
lc = mc.LineCollection(edges_draw, linewidths=1)
ax.add_collection(lc)
plt.plot(points_x, points_y, 'ro')
plt.show()
