import sys
import json
import matplotlib.pyplot as plt
import pandas as pd


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

INPUT_FILE = ''
OUTPUT_FILE = ''
DISTANCE = 0
try:
    INPUT_FILE = sys.argv[1]
    OUTPUT_FILE = sys.argv[2]
    if INPUT_FILE == OUTPUT_FILE:
        input(
    '''Input file and output file are the same! 
    WARNING: Original data will be overriden!!
    Press any key to confirm'''
            )
    DISTANCE = float(sys.argv[3])
except Exception as e:
    print(
'''
Usage: [input file] [output file] [distance in m]
'''
    )
    quit()


df = pd.read_json(INPUT_FILE)

df.head()
nodes = df["graph"]["nodes"]
points_x = [i["meta-data"]['x'] for i in nodes]
points_y = [i["meta-data"]['y'] for i in nodes]

points = [point(i["id"], i["meta-data"]['x'], i["meta-data"]['y'], i['type'], i["meta-data"]['address']) for i in nodes]



sqrt3 = 1.732
L = DISTANCE
x0 = min(points_x)
y0 = min(points_y)
x1 = max(points_x)
y1 = max(points_y)

x0 = x0 - 0.5* L
x1 = x1 + 0.5* L
y0 = y0 - 0.5* L
y1 = y1 + 0.5* L

cores = set()
Xs = []
Ys = []
for x in xfrange(x0, x1, sqrt3*L):
    for y in xfrange(y0, y1, L):
        cores.add((x,y))
        Xs.append(x)
        Ys.append(y)

for x in xfrange(x0 + L/2*sqrt3 , x1, sqrt3*L):
    for y in xfrange(y0 + L/2, y1, L):
        cores.add((x,y))
        Xs.append(x)
        Ys.append(y)



reduced_point_set = set()
kept_core_points = set()

for cp in cores:
    distance_sqr = lambda c: (cp[0]-c.x)*(cp[0]-c.x) + (cp[1]-c.y)*(cp[1]-c.y)
    nearest_point = min(points, key = distance_sqr)
    if distance_sqr(nearest_point) < L*L/4:
        kept_core_points.add(cp)
        reduced_point_set.add(nearest_point)



ori = [
[i.x for i in reduced_point_set],
[i.y for i in reduced_point_set]
]

plt.plot(points_x, points_y, 'ro', color = 'green')   # all points
plt.plot(ori[0], ori[1], 'ro')                        # result
# plt.plot(ne[0], ne[1], 'ro', color = 'blue')    # new cores

plt.axis([11666, 43000, 28000, 49000])
plt.show()
a={'graph': {'directed': False, 'metadata': {'coordinate-system': 'EPSG:3414'},
    'nodes': [
    {
        "id": i.id,
        "type": i.type,
        "meta-data":{
            "address": i.address,
            "x": i.x,
            "y": i.y
        }
    } 
        for i in reduced_point_set
    ],
    "edges":[]
    }

}

with open(OUTPUT_FILE, 'w') as outfile:
    json.dump(a, outfile)
