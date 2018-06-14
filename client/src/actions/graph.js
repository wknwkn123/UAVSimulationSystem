import * as actionTypes from './actionTypes'

export const onLoadGraph = ({ graph }) => (
    { type: actionTypes.ON_LOAD_GRAPH, graph }
)