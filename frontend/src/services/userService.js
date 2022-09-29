import axios from "axios";
import authHeader from "./authHeader";

const API_URL = "http://localhost:8080/api/test/";

const getPublicContent = () => {
    return axios.get(API_URL + "all");
};

const getUserBoard = () => {
    return axios.get(API_URL + "user", { headers: authHeader() });
};

const getAdminBoard = () => {
    return axios.get(API_URL + "admin", { headers: authHeader() });
};

const userService = {
    getPublicContent,
    getUserBoard,
    getAdminBoard,
};

export default userService