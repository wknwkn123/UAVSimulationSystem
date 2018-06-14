export const UAV_PROP = {
    PROGRESS: 'progress',
    NAME: 'name',
    REVENUE: 'revenue',
    BATTERY: 'battery',
    POSITION: 'position',
    ALERT: 'alert'
}

export const ASCENDING = 'ascending'
export const DESCENDING = 'descending'

export const COLOR = {
    SALMON: "#FA8072",
    LIMEGREEN: "#32CD32",
    RED: "#FF0000",
    YELLOW: '#FFFF00',
    UAV: '#ABDC64',
    BLUE: '#0000FF',
    WHITE: '#FFFFFF',
    ORANGE: '#FFA500',
    LIGHT_YELLOW: '#FFFFCC',
    BLACK: '#000000',
    GREEN: '#00FF00',
    CYAN: '#00FFFF'
}

export const RADIUS = {
    BIG_CIRLCE: {
        'base': 1.75,
        'stops': [
            [12, 10],
            [14, 30],
            [15, 50],
            [16, 100],
        ]
    }
    , SMALL_CIRCLE: {
        'base': 1.75,
        'stops': [
            [12, 0],
            [14, 15],
            [15, 25],
            [16, 50],
        ]
    }
    , UAV_CIRCLE: {
        'base': 1.75,
        'stops': [
            [10, 2],
            [12, 4],
            [14, 5],
            [15, 6],
            [16, 7],
        ]
    }
}

export const SELECT_FILE = "Select file"