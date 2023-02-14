import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import {deleteProjectTask, getProjectTaskUser} from '../../../services/backlogService';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

class ProjectTask extends Component {

    onDeleteClick(backlog_id, pt_id) {
        this.props.deleteProjectTask(backlog_id, pt_id);
    }

    /*getUser(backlog_id, pt_id) {

        const {user} = this.props.getProjectTaskUser(backlog_id, pt_id, this.props.history);
        console.log("USER:", user);
        return user;
    }
*/
    render() {

        const {project_task} = this.props;
        console.log("PROJECT TASK: ", this.props);
        // this.getUser(project_task.projectIdentifier, project_task.projectSequence, this.props.history);


        let priorityString;
        let priorityClass;


        if (project_task.priority === 1) {
            priorityClass = "bg-danger text-light"
            priorityString = "HIGH"
        }
        if (project_task.priority === 2) {
            priorityClass = "bg-warning text-light";
            priorityString = "MEDIUM"
        }
        if (project_task.priority === 3) {
            priorityClass = "bg-info text-light";
            priorityString = "LOW"
        }
        return (
            <div className="card shadow p-2 mb-4 bg-light taskCard">
                <div className={`card-header text-primary ${priorityClass}`}>
                    ID: {project_task.projectSequence} -> Priority: {priorityString}
                </div>

                <div className="card-body">
                    <h5 className="card-title">{project_task.summary}</h5>
                    <p className="card-text text-truncate">
                        {project_task.taskDescription}
                    </p>
                    {/* <p className="card-text text-truncate">*/}
                    {/*    User:*/}
                    {/*</p>*/}
                    <Link to={`/updateProjectTask/${project_task.projectIdentifier}/${project_task.projectSequence}`}
                          className=" card-link btn btn-primary">
                        View / Update Task
                    </Link>
                    <button className="card-link btn btn-danger ml-4"
                            onClick={this.onDeleteClick.bind(this, project_task.projectIdentifier, project_task.projectSequence)}>
                        Delete
                    </button>
                </div>
            </div>

        )
    }
}

ProjectTask.propTypes = {
    deleteProjectTask: PropTypes.func.isRequired,
    getProjectTaskUser: PropTypes.func.isRequired,
    // project_task_user: PropTypes.object.isRequired

}

export default connect(null, {deleteProjectTask})(ProjectTask);