import * as actionTypes from './actionTypes'

export const shouldChangeRegion = ({ map }) => (
    {
        type: actionTypes.CHANGE_MAP_POSITION,
        zoomLevel: map.getZoom(),
        center: map.getCenter(),
        bearing: map.getBearing(),
        pitch: map.getPitch()
    }
)

export const shouldResetBearing = () => (
    { type: actionTypes.RESET_MAP_BEARING }
)

export const shouldResetPitch = () => (
    { type: actionTypes.RESET_MAP_PITCH }
)

export const shouldChangeCenter = (center) => (
    {
        type: actionTypes.CHANGE_CENTER,
        center,
    }
)

export const onChangeMapTerrain = ({ style }) => (
    { type: actionTypes.CHANGE_MAP_TERRAIN, style }
)

export const onChangeMapBrightness = (brightness) => (
    { type: actionTypes.CHANGE_MAP_BRIGHTNESS, brightness }
)

export const toogle3DMode = () => (
    { type: actionTypes.ADD_3D_LAYER }
)