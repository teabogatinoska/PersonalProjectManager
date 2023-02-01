import {
    GET_BACKLOG,
    GET_PROJECT_TASK,
    DELETE_PROJECT_TASK,
    GET_PROJECT_TASK_USER,
    GET_PROJECT_TASK_USERS
} from "../services/types";

const initialState = {
    project_tasks: [],
    project_task: {},
    project_task_user: {},
    project_task_users: [],
}

export default function (state = initialState, action) {
    switch (action.type) {

        case GET_BACKLOG:
            return {
                ...state,
                project_tasks: action.payload
            }

        case GET_PROJECT_TASK:
            return {
                ...state,
                project_task: action.payload
            }

        case DELETE_PROJECT_TASK:
            return {
                ...state,
                project_tasks: state.project_tasks.filter(project_task => project_task.projectSequence !== action.payload)
            }
        case GET_PROJECT_TASK_USER:
            return {
                ...state,
                project_task_user: action.payload
            }
        case GET_PROJECT_TASK_USERS:
            return {
                ...state,
                project_task_users: action.payload
            }

        default:
            return state;
    }
}