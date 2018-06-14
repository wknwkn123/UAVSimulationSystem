export const URL = 'ws://localhost:8000'

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
