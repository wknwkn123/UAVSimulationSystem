const KEY_OPTIONS = 'Options'

// ----------------------------
// NODE------------------------
// ----------------------------
const KEY_NODE_ID = 'ID'
const KEY_NODE_POSITION = 'Position'

// ----------------------------
// EDGE------------------------
// ----------------------------
const KEY_SOURCE = 'Source ID'
const KEY_TARGET = 'Target ID'

// ----------------------------
// UAV------------------------
// ----------------------------
const KEY_UAV_ALERT = 'Alert'
const KEY_UAV_BATTERY = 'Battery'
const KEY_UAV_PROGRESS = 'Progress'
const KEY_UAV_COLLISION_RISK = 'Collision Risk'
const KEY_UAV_REVENUE = 'Revenue'

export const NODE_KEY = [KEY_NODE_ID, KEY_NODE_POSITION, KEY_OPTIONS]
export const EDGE_KEY = [KEY_SOURCE, KEY_TARGET, KEY_OPTIONS]
export const UAV_KEY = [
  KEY_NODE_ID,
  KEY_NODE_POSITION,
  KEY_UAV_BATTERY,
  KEY_UAV_PROGRESS,
  KEY_UAV_COLLISION_RISK,
  KEY_UAV_REVENUE
]
export const UAV_WIDTH_KEY = UAV_KEY.reduce((acc, value) => {
  if (value === KEY_UAV_PROGRESS) {
    acc[value] = 4
  } else {
    acc[value] = 2
  }
  return acc
}, {})

export const COMPACT_TABLE_CELL = { padding: 0, margin: 0, fontSize: '0.875em', height: 15 }
