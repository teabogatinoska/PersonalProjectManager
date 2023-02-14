import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';
import classnames from 'classnames';
import {addProjectTask} from '../../../services/backlogService';
import PropTypes from 'prop-types';
import {getUsers} from '../../../services/userService';

class AddProjectTask extends Component {

    constructor(props) {
        super(props)
        const {id} = this.props.match.params;


        this.state = {
            summary: "",
            taskDescription: "",
            status: "",
            priority: 0,
            user: "",
            projectIdentifier: id,
            dueDate: "",
            errors: {}
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

    }

    componentDidMount() {
        this.props.getUsers();
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({errors: nextProps.errors});
        }
    }

    onChange(e) {


        this.setState({[e.target.name]: e.target.value})
    }


    onSubmit(e) {
        e.preventDefault();

        const newTask = {
            summary: this.state.summary,
            taskDescription: this.state.taskDescription,
            status: this.state.status,
            priority: this.state.priority,
            user: this.state.user,
            dueDate: this.state.dueDate
        }

        this.props.addProjectTask(this.state.projectIdentifier, newTask, this.props.history);
    }

    render() {
        const {id} = this.props.match.params;
        const {errors} = this.state;
        const {users} = this.props.user;

        return (
            <div className="addProjectTask">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <div className="card shadow">
                                <div className="card-header rounded forms">
                                    <h4 className="text-center">Create New Task </h4>
                                </div>
                                <div className="card-body">
                                    <form onSubmit={this.onSubmit}>
                                        <div className="form-group">
                                            <label>Task Name:</label>
                                            <input type="text" className={classnames("form-control ", {
                                                "is-invalid": errors.summary
                                            })} name="summary"
                                                   value={this.state.summary}
                                                   required
                                                   onChange={this.onChange}/>
                                            {errors.summary && (
                                                <div className="invalid-feedback">{errors.summary}</div>
                                            )}
                                        </div>
                                        <div className="form-group">
                                            <label>Task Description:</label>
                                            <textarea className={classnames("form-control ", {
                                                "is-invalid": errors.taskDescription
                                            })} name="taskDescription" required
                                                      value={this.state.taskDescription} onChange={this.onChange}/>
                                        </div>
                                        <label>Due Date:</label>
                                        <div className="form-group">
                                            <input type="date" className="form-control" name="dueDate"
                                                   required value={this.state.dueDate} onChange={this.onChange}/>
                                        </div>
                                        <div className="form-group">
                                            <label>Task Priority:</label>
                                            <select className="form-control " name="priority"
                                                    value={this.state.priority} onChange={this.onChange}>
                                                <option value={0}>Select Priority</option>
                                                <option value={1}>High</option>
                                                <option value={2}>Medium</option>
                                                <option value={3}>Low</option>
                                            </select>
                                        </div>

                                        <div className="form-group">
                                            <label>Task Status:</label>
                                            <select className="form-control" name="status"
                                                    value={this.state.status} onChange={this.onChange}>
                                                <option value="">Select Status</option>
                                                <option value="TO_DO">TO DO</option>
                                                <option value="IN_PROGRESS">IN PROGRESS</option>
                                                <option value="DONE">DONE</option>
                                            </select>
                                        </div>
                                        <div className="form-group">
                                            <label>Assign to:</label>
                                            <select name="user" value={this.state.user}
                                                    className="form-control " onChange={this.onChange}>
                                                <option value="">Select User</option>
                                                {users.map((term) =>
                                                    <option value={term.username}>{term.username}</option>
                                                )}
                                            </select>
                                        </div>
                                        <input type="submit" className="btn btn-primary submitBtn "/>
                                        <Link to={`/projectBoard/${id}`} className="btn btn-info  backBtn">
                                            Back to Project Board
                                        </Link>
                                    </form>
                                    <br/>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        )
    }
}

AddProjectTask.propTypes = {
    addProjectTask: PropTypes.func.isRequired,
    getUsers: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired,
    errors: PropTypes.string.isRequired
}

const mapStateToProps = state => ({
    user: state.user,
    errors: state.errors
})

export default connect(mapStateToProps, {addProjectTask, getUsers})(AddProjectTask);