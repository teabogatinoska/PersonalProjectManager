import React from 'react';
import './App.css';
import Dashboard from './components/Dashboard';
import Header from './components/Layout/Header';
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import AddProject from './components/Project/AddProject';
import { Provider } from 'react-redux';
import store from './store';
import UpdateProject from './components/Project/UpdateProject';
import ProjectBoard from './components/BacklogBoard/ProjectBoard';
import AddProjectTask from './components/BacklogBoard/ProjectTasks/AddProjectTask';
import UpdateProjectTask from './components/BacklogBoard/ProjectTasks/UpdateProjectTask';
import Landing from './components/Layout/LandingPage';
import Register from './components/User/Register';
import Login from './components/User/Login';
import jwt_decode from 'jwt-decode';
import setJWTToken from './security/setJWTToken';
import { SET_CURRENT_USER } from './services/types';
import { logout } from './services/SecurityService';
import SecuredRoute from './security/secureRoute';

const jwtToken = localStorage.jwtToken;

if (jwtToken) {
    setJWTToken(jwtToken);
    const decoded_jwtToken = jwt_decode(jwtToken);
    store.dispatch({
        type: SET_CURRENT_USER,
        payload: decoded_jwtToken
    });

    const currentTime = Date.now() / 1000;
    if (decoded_jwtToken.exp < currentTime) {
        store.dispatch(logout());
        window.location.href = "/";
    }
}


function App() {
    return (
        <Provider store={store}>
            <Router>
                <div className="App">
                    <Header />

                    {
                        //public routes
                    }

                    <Route exact path="/" component={Landing} />
                    <Route exact path="/register" component={Register} />
                    <Route exact path="/login" component={Login} />

                    {
                        //private routes
                    }

                    <Switch>
                        <SecuredRoute exact path="/dashboard" component={Dashboard} />
                        <SecuredRoute exact path="/addProject" component={AddProject} />
                        <SecuredRoute exact path="/updateProject/:id" component={UpdateProject} />
                        <SecuredRoute exact path="/projectBoard/:id" component={ProjectBoard} />
                        <SecuredRoute exact path="/addProjectTask/:id" component={AddProjectTask} />
                        <SecuredRoute exact path="/updateProjectTask/:backlog_id/:pt_id" component={UpdateProjectTask} />
                    </Switch>
                </div>
            </Router>
        </Provider>
    );
}

export default App;
