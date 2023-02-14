import React, {Component} from 'react'
import {getProject, updateProject} from '../../services/projectService';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import classnames from 'classnames';
import '../../App.css';
import {getUsers} from "../../services/userService";


class UpdateProject extends Component {

    constructor() {
        super()

        this.state = {
            id: "",
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

    componentWillReceiveProps(nextProps) {

        if (nextProps.errors) {
            this.setState({errors: nextProps.errors});
        }

        const {
            id,
            projectName,
            projectIdentifier,
            description,
            start_date,
            end_date,
            projectUsers,
            projectLeader,
        } = nextProps.project

        this.setState({
            id,
            projectName,
            projectIdentifier,
            description,
            start_date,
            end_date,
            projectUsers,
            projectLeader,
        });
    }


    componentDidMount() {
        const {id} = this.props.match.params;
        this.props.getProject(id, this.props.history);
        this.props.getUsers();
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    handleChange = (e) => {
        let value = Array.from(e.target.selectedOptions, option => option.value);
        this.setState({projectUsers: value});
    }

    onSubmit(e) {
        e.preventDefault()

        const updateProject = {
            id: this.state.id,
            projectName: this.state.projectName,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            start_date: this.state.start_date,
            end_date: this.state.end_date,
            projectUsers: this.state.projectUsers,
            projectLeader: this.state.projectLeader
        };

        this.props.updateProject(this.state.id, updateProject, this.props.history);
    }

    render() {

        const {errors} = this.state;
        const {users} = this.props.user;

        return (
            <div className="project">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <div className="card shadow">
                                <div className="card-header rounded forms ">
                                    <h4 className=" text-center">Update Project Details </h4>
                                </div>
                                <div className="card-body">
                                    <form onSubmit={this.onSubmit}>
                                        <div className="form-group">
                                            <label>Project Name:</label>
                                            <input type="text" className={classnames("form-control", {
                                                "is-invalid": errors.projectName
                                            })} name="projectName" value={this.state.projectName}
                                                   onChange={this.onChange}/>
                                            {
                                                errors.projectName && (
                                                    <div className="invalid-feedback">{errors.projectName}</div>
                                                )
                                            }
                                        </div>
                                        <div className="form-group">
                                            <label>Project ID:</label>
                                            <input type="text" className="form-control "
                                                   name="projectIdentifier"
                                                   value={this.state.projectIdentifier} onChange={this.onChange}
                                                   disabled/>
                                        </div>
                                        <div className="form-group">
                                            <label>Project Description:</label>
                                            <textarea className={classnames("form-control", {
                                                "is-invalid": errors.description
                                            })} name="description"
                                                      value={this.state.description} onChange={this.onChange}/>
                                            {errors.description && (
                                                <div className="invalid-feedback">{errors.description}</div>
                                            )}
                                        </div>
                                        <label>Estimated End Date:</label>
                                        <div className="form-group">
                                            <input type="date" className="form-control " name="dueDate"
                                                   value={this.state.end_date}
                                                   onChange={this.onChange}/>

                                        </div>

                                        <div className="form-group">
                                            <label>Add Members:</label>
                                            <select multiple={true} name="projectUsers" id="projectUsers"
                                                    value={this.state.projectUsers}
                                                    className="  form-control "
                                                    onChange={this.handleChange}>
                                                {users.map((term) =>
                                                    <option value={term.username}>{term.username}</option>
                                                )}
                                            </select>

                                        </div>

                                        <input type="submit" className="btn btn-primary btn-block mt-2"/>
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

UpdateProject.propTypes = {
    getProject: PropTypes.func.isRequired,
    updateProject: PropTypes.func.isRequired,
    getUsers: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired,
    project: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    project: state.project.project,
    user: state.user,
    errors: state.errors
});

export default connect(mapStateToProps, {getProject, updateProject, getUsers})(UpdateProject);