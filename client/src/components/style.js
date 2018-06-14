const NORMAL_FONT_SIZE = '0.875em'
const TINY_FONT_SIZE = '8px'
const SMALL_FONT_SIZE = '0.8em'
export const Root = {
    position: 'absolute',
    left: 0,
    top: 0,
    width: '100%',
    height: '100%',

    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center'
}

export const MainLeftContainer = {
    minWidth: 375,
    width: 400,
    height: '100%',

    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'space-between',
    alignItems: 'center'
}

export const MainContainer = {
    width: '100%',
    height: '100%',

    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
}

export const MapContainer = {
    flex: 3,
    position: 'relative',
    width: '100%',
    height: '100%',

    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'center',
}

export const MapFileTools = {
    width: 'auto',
    height: 'auto',

    position: 'absolute',
    top: 9,
    right: 9
}

export const MapSettingTools = {
    width: 'auto',
    height: 'auto',

    position: 'absolute',
    top: 150,
    right: 9
}

export const MapTable = {
    width: 'auto',
    height: 200,

    position: 'absolute',
    bottom: 0,
    margin: 'auto',
    zIndex: 2,
    opacity: .8,

    overflowY: 'hidden'
}

export const COMPACT = {
    MENU_ITEM: { padding: 5, width: 60 },
    TABLE: { width: 600, fontSize: NORMAL_FONT_SIZE, margin: 0, borderRadius: 0 },
    ROW: { padding: 0, margin: 0, fontSize: TINY_FONT_SIZE, height: 15 },
    PROGRESS: { fontSize: NORMAL_FONT_SIZE, margin: 'auto' },
    GRAPH: { display: 'flex', width: 'auto', fontSize: '0.8em', paddingBottom: 10 },
    DIVIDER: { width: '100%', padding: 2 },
    GRID_CONTENT: { padding: 1, fontSize: '0.875em' },
    GRID_CONTENT_TIME: { padding: 1, fontSize: '0.7em', color: 'gray' },
    GRID: { margin: 0 },
    HEADER: { margin: 8 }
}

export const GraphContainer = {
    position: 'absolute',
    top: 25,
    left: 25,
    backgroundColor: 'rgba(0,0,0,0.3)',
    pointerEvents: 'none',
    color: 'white',
    padding: 10
}

export const DialogContainer = {
    width: '95%',
    height: 400,

    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'center',
    padding: 5,
    marginTop: 0,
    marginBottom: 10,
}

export const DialogSegment = {
    height: '100%',

    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'center',
    padding: 5,
    marginTop: 0,
    overflowY: 'scroll'
}

export const PopUpContainer = {
    width: 95,
    fontSize: '0.875em',

    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    borderColor: 'black',
    borderWidth: 1,
    padding: 0,
}

export const PopUpInfo = {
    flex: 0.9,
    width: '100%',
    height: '100%',
    padding: 2,

    textAlign: 'left',
}

export const MenuPopUpContainer = {
    width: 320,
    height: 'auto',
    paddingBottom: 10,

    position: 'absolute',
    top: 25,
    right: 100,

    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'center',
    backgroundColor: 'white',
}

export const MenuPopUpHeader = {
    flex: 0.1,
    width: 'auto',
    height: 'auto',
    marginTop: 10,
}

export const MenuPopUpContent = {
    flex: 1,
    width: '95%',
    height: 'auto',
}

export const MenuPopUpFooter = {
    flex: 0.1,
    width: 'auto',
    height: 'auto',
}

export const ContentRow = {
    flex: 1,
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'flex-start',
    padding: 3,
    fontSize: SMALL_FONT_SIZE
}

export const ParamContainer = {
    width: '95%',
    height: 'auto',

    display: 'block',
    margin: 5
}

export const LeftPanelContainer = {
    width: '25em',
    height: '100%',

    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'center',
    overflow: 'auto',
    backgroundColor: 'white'
}