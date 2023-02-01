import { combineReducers } from 'redux';
import errorReducer from './errorReducer';
import projectReducer from './projectReducer';
import backlogReducer from './backlogReducer';
import securityReducer from './securityReducer';
import userReducer from "./userReducer";

export default combineReducers({
    errors: errorReducer,
    project: projectReducer,
    backlog: backlogReducer,
    security: securityReducer,
    user: userReducer
})