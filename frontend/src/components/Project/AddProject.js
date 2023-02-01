import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {createProject} from '../../services/projectService';
import classnames from 'classnames';
import {getUsers} from '../../services/userService';
import '../../App.css';


class AddProject extends Component {
    constructor() {
        super()

        this.state = {
            projectName: "",
            projectIdentifier: "",
            description: "",
            start_date: "",
            end_date: "",
            projectUsers: [],
            projectLeader: "",
            errors: {}
        }


        this.onChange = this.onChange.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

    }

    componentDidMount() {
        this.props.getUsers();
    }

    //life cycle hooks
    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({errors: nextProps.errors});
        }
    }

    onChange(e) {
        this.setState(
            {[e.target.name]: e.target.value.trim()}
        )
    }

    handleChange = (e) => {
        let value = Array.from(e.target.selectedOptions, option => option.value);
        this.setState({projectUsers: value});
    }

    onSubmit(e) {

        e.preventDefault();


        const newProject = {
            projectName: this.state.projectName,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            start_date: this.state.start_date,
            end_date: this.state.end_date,
            projectUsers: this.state.projectUsers,
            projectLeader: this.state.projectLeader
        }

        this.props.createProject(newProject, this.props.history)
    }

    render() {
        const {errors} = this.state;
        const {users} = this.props.user;

        return (
            <div>
                <div className="project">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8 m-auto">
                                <div className="card shadow">
                                    <div className="card-header rounded forms">
                                        <h1 className="display-5 text-center">Create New Project </h1>
                                    </div>
                                    <div className="card-body">
                                        <form onSubmit={this.onSubmit}>
                                            <label>Project Name</label>
                                            <div className="form-group">
                                                <input type="text"
                                                       className={classnames("form-control form-control-lg", {
                                                           "is-invalid": errors.projectName
                                                       })} name="projectName"
                                                       value={this.state.projectName}
                                                       onChange={this.onChange}/>
                                                {errors.projectName && (
                                                    <div className="invalid-feedback">{errors.projectName}</div>
                                                )}
                                            </div>

                                            <div className="form-group">
                                                <label>Project ID</label>
                                                <input type="text"
                                                       className={classnames("form-control form-control-lg", {
                                                           "is-invalid": errors.projectIdentifier
                                                       })} name="projectIdentifier"
                                                       value={this.state.projectIdentifier} onChange={this.onChange}/>
                                                {errors.projectIdentifier && (
                                                    <div className="invalid-feedback">{errors.projectIdentifier}</div>
                                                )}
                                            </div>

                                            <div className="form-group">
                                                <label>Project Description</label>
                                                <textarea className={classnames("form-control form-control-lg", {
                                                    "is-invalid": errors.description
                                                })} name="description"
                                                          value={this.state.description} onChange={this.onChange}/>
                                                {errors.description && (
                                                    <div className="invalid-feedback">{errors.description}</div>
                                                )}
                                            </div>
                                            <label>Estimated End Date</label>
                                            <div className="form-group">
                                                <input type="date" className="form-control form-control-lg"
                                                       name="end_date"
                                                       value={this.state.end_date} onChange={this.onChange}/>
                                            </div>

                                            <div className="form-group">
                                                <label>Assign to:</label>
                                                <select multiple={true} name="projectUsers" id="projectUsers" value={this.state.projectUsers}
                                                        className="  form-control form-control-lg" onChange={this.handleChange}>
                                                    {users.map((term) =>
                                                        <option value={term.username}>{term.username}</option>
                                                    )}
                                                </select>

                                            </div>

                                            <input type="submit" className=" btn btn-primary btn-block mt-4"/>

                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        )
    }
}

AddProject.propTypes = {
    createProject: PropTypes.func.isRequired,
    getUsers: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    user: state.user,
    errors: state.errors
})

export default connect(mapStateToProps, {createProject, getUsers})(AddProject);
