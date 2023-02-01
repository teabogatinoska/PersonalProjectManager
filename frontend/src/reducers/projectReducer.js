import {GET_PROJECTS, GET_PROJECT, DELETE_PROJECT, GET_PROJECT_USERS, GET_PROJECT_USER} from '../services/types';

const initialState = {
    projects: [],
    project: {},
    project_users: [],
    project_user: {},
};

export default function (state = initialState, action) {
    switch (action.type) {
        case GET_PROJECTS:
            return {
                ...state,
                projects: action.payload
            };

        case GET_PROJECT:
            return {
                ...state,
                project: action.payload
            };

        case DELETE_PROJECT:
            return {
                ...state,
                projects: state.projects.filter(
                    project => project.projectIdentifier !== action.payload
                )
            };
        case GET_PROJECT_USERS:
            return {
                ...state,
                project_users: action.payload
            };

        case GET_PROJECT_USER:
            return {
                ...state,
                project_user: action.payload
            };

        default:
            return state;
    }
}