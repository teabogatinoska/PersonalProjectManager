import React, {Component} from 'react';
import {createNewUser} from '../../services/SecurityService';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import classnames from 'classnames';
import '../../App.css'

class Register extends Component {

    constructor() {
        super();

        this.state = {
            username: "",
            firstName: "",
            lastName: "",
            password: "",
            confirmPassword: "",
            errors: {}
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        if (this.props.security.validToken) {
            this.props.history.push("/dashboard");
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({
                errors: nextProps.errors
            })
        }
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value})
    }

    onSubmit(e) {
        e.preventDefault();

        const newUser = {
            username: this.state.username,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            password: this.state.password,
            confirmPassword: this.state.confirmPassword
        }

        this.props.createNewUser(newUser, this.props.history);
    }

    render() {

        const {errors} = this.state;

        return (
            <div className="register">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <div className="card shadow">
                                <div className="card-header rounded forms">
                                    <h1 className="text-center userLogin">Sign Up</h1><br/>
                                </div>
                                <div className="card-body">
                                    <label>First Name</label>
                                    <form onSubmit={this.onSubmit}>
                                        <div className="form-group">
                                            <input type="text" className={classnames("form-control", {
                                                "is-invalid": errors.firstName
                                            })}name="firstName"
                                                   value={this.state.firstName} onChange={this.onChange}/>
                                            {
                                                errors.firstName && (
                                                    <div className="invalid-feedback">{errors.firstName}</div>
                                                )
                                            }
                                        </div>
                                        <div className="form-group">
                                            <label>Last Name</label>
                                            <input type="text" className={classnames("form-control", {
                                                "is-invalid": errors.lastName
                                            })} name="lastName"
                                                   value={this.state.lastName} onChange={this.onChange}/>
                                            {
                                                errors.lastName && (
                                                    <div className="invalid-feedback">{errors.lastName}</div>
                                                )
                                            }
                                        </div>
                                        <div className="form-group">
                                            <label>Email</label>
                                            <input type="text" className={classnames("form-control", {
                                                "is-invalid": errors.username
                                            })} name="username"
                                                   value={this.state.username} onChange={this.onChange}/>
                                            {
                                                errors.username && (
                                                    <div className="invalid-feedback">{errors.username}</div>
                                                )
                                            }
                                        </div>
                                        <div className="form-group">
                                            <label>Password</label>
                                            <input type="password"
                                                   className={classnames("form-control", {
                                                       "is-invalid": errors.password
                                                   })} name="password"
                                                   value={this.state.password}
                                                   onChange={this.onChange}/>
                                            {
                                                errors.password && (
                                                    <div className="invalid-feedback">{errors.password}</div>
                                                )
                                            }
                                        </div>
                                        <div className="form-group">
                                            <label>Confirm Password</label>
                                            <input type="password"
                                                   className={classnames("form-control", {
                                                       "is-invalid": errors.confirmPassword
                                                   })}value={this.state.confirmPassword}
                                                   onChange={this.onChange}
                                                   name="confirmPassword"/>
                                            {
                                                errors.confirmPassword && (
                                                    <div className="invalid-feedback">{errors.confirmPassword}</div>
                                                )
                                            }
                                        </div>
                                        <input type="submit" className="btn btn-info btn-block mt-4"/>
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

Register.propTypes = {
    createNewUser: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    security: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors,
    security: state.security
})

export default connect(mapStateToProps, {createNewUser})(Register);