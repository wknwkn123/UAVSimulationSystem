// EDITOR
export const NODE = 'node'
export const EDGE = 'edge'
export const STATS = 'stats'
export const OPEN = 'open_file'
export const SAVE = 'save_file'
export const SAVE_AS = 'save_as_file'
export const CREATE_NEW = 'create_new_file'
export const STATS_ACCORDION = { key: STATS, icon: 'area graph', title: 'Specification' }
export const NODE_ACCORDION = { key: NODE, icon: 'marker', title: 'Waypoint' }
export const EDGE_ACCORDION = { key: EDGE, icon: 'fork', title: 'Flight Segment' }
export const ACCORDION_LIST = [STATS_ACCORDION, NODE_ACCORDION, EDGE_ACCORDION]
export const HEADER_LIST = [
  { name: CREATE_NEW, color: 'green', iconName: 'add' },
  { name: OPEN, color: 'blue', iconName: 'open folder' },
  { name: SAVE, color: 'blue', iconName: 'save' },
  { name: SAVE_AS, color: 'blue', iconName: 'copy' }
]
