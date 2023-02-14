import React from 'react'
import {Link} from 'react-router-dom';
import '../../App.css';


const CreateProjectButton = function CreateProjectButton() {
    return (
        <React.Fragment>
            <Link to="/addProject" href="ProjectForm.html" className="btn btn-lg btn-info createProject">
                Create a Project
            </Link>
        </React.Fragment>
    )
}

export default CreateProjectButton;