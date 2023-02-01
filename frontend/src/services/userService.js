import axios from 'axios';
import {GET_USERS} from './types';

/*export const getUserProfile = (user_id, history) => async dispatch => {
    try {
        const res = await axios.get(`/api/profile/${user_id}`);
        dispatch({
            type: GET_USER_PROFILE,
            payload: res.data
        })
    } catch (err) {
        history.push("/dashboard");
    }

};

 */

export const getUsers = () => async dispatch => {
    const res = await axios.get("/api/users/all")
    dispatch({
        type: GET_USERS,
        payload: res.data
    })
};
