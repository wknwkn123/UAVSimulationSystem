// import * as actionTypes from './actionTypes'

const index_file_name = "index"

const appendIndexFile = (filename) => (
    getIndexFile() ===  null ?
        [filename] : [...getIndexFile(), filename]
)

export const openFile = (filename) => {
    return localStorage.getItem(filename)
}

export const saveFile = ({ filename, data }) => {
    if (getIndexFile() === null || !getIndexFile().includes(filename))
        addIndexFile(filename)
    localStorage.setItem(filename, data)
}

export const addIndexFile = (filename) => {
    console.log(appendIndexFile(filename))
    localStorage.setItem(index_file_name, JSON.stringify(appendIndexFile(filename)))
    console.log(getIndexFile())
}

export const getIndexFile = () => {
    var item = localStorage.getItem(index_file_name)
    return JSON.parse(item)
}

export const resetIndexFile = () => {
    localStorage.setItem(index_file_name, JSON.stringify([]))
}