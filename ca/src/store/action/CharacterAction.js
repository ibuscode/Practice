import axios from "axios"

const api = 'https://rickandmortyapi.com/api/character'

export const getAllData = (page) => {
    return async (dispatch) => {
                console.log("Fetching page:", page) 
        const response = await axios.get(api + `?page=${page}`)
                console.log("Response:", response.data) 

        let action = {
            type: 'GET_ALL',
            payload: response.data
        }
        dispatch(action)
    }
}