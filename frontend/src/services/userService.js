import axios from 'axios';
import {GET_USERS} from './types';


export const getUsers = () => async dispatch => {
    const res = await axios.get("/api/users/all")
    dispatch({
        type: GET_USERS,
        payload: res.data
    })
};
