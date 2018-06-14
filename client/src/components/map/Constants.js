import { point_icon, polygon_icon, line_icon } from '../../assets/img/mapbox-icon'

const FIXED_URL = 'mapbox://styles/mapbox/'
const V_9 = '-v9'

export const BASIC = FIXED_URL + 'basic' + V_9
export const STREET = FIXED_URL + 'street' + V_9
export const BRIGHT = FIXED_URL + 'bright' + V_9
export const LIGHT = FIXED_URL + 'light' + V_9
export const DARK = FIXED_URL + 'dark' + V_9
export const SATELLITE = FIXED_URL + 'satellite' + V_9

export const DRAW_POINT = 'draw_point'
export const DRAW_LINE = 'draw_line'
export const LIST_DRAW_MENU = [
  { title: 'Point', name: DRAW_POINT, img: point_icon },
  { title: 'Line', name: DRAW_LINE, img: line_icon }
]

export const LIST_MENU = [
  // { iconName: 'user circle', title: 'Account', name: 'account' },
  { iconName: 'eye', title: '3D', name: '3D', key: 'mode3D' },
  { iconName: 'setting', title: 'Setting', name: 'setting' },
  { iconName: 'compass', title: 'Reset Pitch', name: 'reset_pitch' },
  { iconName: 'compass', title: 'Reset Bearing', name: 'reset_bearing' }
]

export const BUILDING_3D_LAYER = '3d-buildings'
export const ROOM_EXTRUSION_LAYER = 'room-extrusion'
export const NODE_LAYER = 'node' + ROOM_EXTRUSION_LAYER
export const EDGE_LAYER = 'edge' + ROOM_EXTRUSION_LAYER

const MAPBOX_STYLE_LIST = [BASIC, BRIGHT, LIGHT, DARK, SATELLITE]
export const MAPBOX_STYLE_OPTIONS = MAPBOX_STYLE_LIST.map(value => ({
  key: value,
  value,
  text: value.replace(FIXED_URL, '')
}))
