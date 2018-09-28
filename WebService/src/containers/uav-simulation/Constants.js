Object.byString = function (o, s) {
  s = s.replace(/\[(\w+)\]/g, '.$1') // convert indexes to properties
  s = s.replace(/^\./, '') // strip a leading dot
  var a = s.split('.')
  for (var i = 0, n = a.length; i < n; ++i) {
    var k = a[i]
    if (k in o) {
      o = o[k]
    } else {
      return
    }
  }
  return o
}

export const UAV_KEY = {
  lat: 'position.lat',
  lng: 'position.lng',
  alert: 'alert',
  progress: 'progress',
  battery: 'battery',
  revenue: 'revenue'
}

export const URL = 'ws://10.25.200.80:9001/'

export const PLANAR_GRAPH = 'PLANARGRAPH'
export const RANDOM = 'RANDOM'

export const START_SIMULATION_JSON = {
  simulationStart: 'start',
  simulationParameter: {
    airspaceType: PLANAR_GRAPH,
    flightScheduleType: RANDOM
  }
}

export const STOP_SIMULATION = {
  simulationStart: 'stop'
}

export const CONTINUE_SIMULATION = {
  simulationStart: 'continue'
}
