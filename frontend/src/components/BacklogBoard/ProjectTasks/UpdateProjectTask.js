import React, {Component} from 'react'
import {connect} from 'react-redux';
import classnames from 'classnames';
import {getProjectTask, updateProjectTask} from '../../../services/backlogService';
import PropTypes from 'prop-types';
import {Link} from 'react-router-dom';
import {getUsers} from '../../../services/userService';


class UpdateProjectTask extends Component {

    constructor() {
        super()

        this.state = {
            summary: "",
            taskDescription: "",
            status: "",
            priority: 0,
            user: "",
            projectIdentifier: "",
            dueDate: "",
            errors: {}
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        const {backlog_id, pt_id} = this.props.match.params;
        this.props.getProjectTask(backlog_id, pt_id, this.props.history);
        this.props.getUsers();

    }

    componentWillReceiveProps(nextProps) {

        if (nextProps.errors) {
            this.setState({errors: nextProps.errors});
        }

        const {
            id,
            projectSequence,
            summary,
            taskDescription,
            status,
            priority,
            user,
            projectIdentifier,
            dueDate,
            created_At
        } = nextProps.project_task;

        this.setState({
            id,
            projectSequence,
            summary,
            taskDescription,
            status,
            priority,
            user,
            projectIdentifier,
            dueDate,
            created_At
        });

    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value})
    }

    onSubmit(e) {
        e.preventDefault();

        const UpdateProjectTask = {
            id: this.state.id,
            projectSequence: this.state.projectSequence,
            summary: this.state.summary,
            taskDescription: this.state.taskDescription,
            status: this.state.status,
            priority: this.state.priority,
            user: this.state.user,
            projectIdentifier: this.state.projectIdentifier,
            dueDate: this.state.dueDate,
            created_At: this.state.created_At,
            updated_At: this.state.updated_At
        }



        this.props.updateProjectTask(this.state.projectIdentifier, this.state.projectSequence, UpdateProjectTask, this.props.history);

    }


    render() {

        const {errors} = this.state;
        const {users} = this.props.user;


        return (
            <div className="addProjectTask">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <div className="card shadow">
                                <div className="card-header rounded forms">
                                    <h5 className="text-center">Update Task Details -- Task
                                        ID:{""} {this.state.projectSequence} {""}</h5>
                                    <hr/>
                                </div>
                                <div className="card-body">
                                    <form onSubmit={this.onSubmit}>
                                        <div className="form-group">
                                            <label>Task Name:</label>
                                            <input type="text" required className={classnames("form-control", {
                                                "is-invalid": errors.summary
                                            })} name="summary"
                                                   value={this.state.summary} onChange={this.onChange}/>
                                            {
                                                errors.summary && (
                                                    <div className="invalid-feeback">{errors.summary}</div>
                                                )
                                            }
                                        </div>
                                        <div className="form-group">
                                            <label>Task Description:</label>
                                            <textarea className={classnames("form-control", {
                                                "is-invalid": errors.taskDescription
                                            })} name="taskDescription" required
                                                      value={this.state.taskDescription} onChange={this.onChange}/>
                                        </div>
                                        <label>Due Date:</label>
                                        <div className="form-group">
                                            <input type="text" className="form-control" name="dueDate" required
                                                   value={this.state.dueDate}
                                                   onChange={this.onChange}/>
                                        </div>
                                        <div className="form-group">
                                            <label>Task Priority:</label>
                                            <select className="form-control" name="priority" required
                                                    value={this.state.priority} onChange={this.onChange}>
                                                <option value={0}>Select Priority</option>
                                                <option value={1}>High</option>
                                                <option value={2}>Medium</option>
                                                <option value={3}>Low</option>
                                            </select>
                                        </div>

                                        <div className="form-group">
                                            <label>Task Status:</label>
                                            <select className="form-control" name="status" required
                                                    value={this.state.status} onChange={this.onChange}>
                                                <option value="">Select Status</option>
                                                <option value="TO_DO">TO DO</option>
                                                <option value="IN_PROGRESS">IN PROGRESS</option>
                                                <option value="DONE">DONE</option>
                                            </select>
                                        </div>
                                        <div className="form-group">
                                            <label>Assign to:</label>
                                            <select name="user" className="form-control" required
                                                    onChange={this.onChange}>
                                                <option value="">Select User</option>
                                                {users.map((term) =>
                                                    <option value={term.username}>{term.username}</option>
                                                )}
                                            </select>
                                        </div>
                                        <input type="submit" className="btn btn-primary  submitBtn "/>
                                        <Link to={`/projectBoard/${this.state.projectIdentifier}`}
                                              className="btn btn-info  backBtn">
                                            Back to Project Board
                                        </Link>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

UpdateProjectTask.propTypes = {
    getProjectTask: PropTypes.func.isRequired,
    project_task: PropTypes.object.isRequired,
    updateProjectTask: PropTypes.func.isRequired,
    getUsers: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired,
    errors: PropTypes.string.isRequired
};

const mapStateToProps = state => ({
    project_task: state.backlog.project_task,
    user: state.user,
    errors: state.errors
});


export default connect(mapStateToProps, {getProjectTask, updateProjectTask, getUsers})(UpdateProjectTask);