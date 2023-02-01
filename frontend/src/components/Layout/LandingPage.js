import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import '../../App.css';
import landingLogo from '../../img/img2.png';


class Landing extends Component {

    componentDidMount() {
        if (this.props.security.validToken) {
            this.props.history.push("/dashboard");
        }
    }


    render() {
        return (
            <div className="landing"><br/><br/><br/>
                <div className="light-overlay landing-inner text-dark">
                    <div className="container">
                        <div className="row"><br/>
                            <div className="col-md-6 text-left">
                                <h1 className="display-3 mb-4 mainHeading ">ProjectHub</h1><br/>
                                <p className="lead">
                                    Create your account now or log in to explore the app
                                </p><br/>
                                <Link className="btn btn-lg btn-primary mr-2 shadow rounded" to="/register">
                                    Sign Up
                                </Link>
                                <Link className="btn btn-lg btn-secondary mr-2 shadow rounded" to="/login">
                                    Login
                                </Link>
                            </div>
                            <div className="col-md-6 text-right landing-img ">
                                <img src={landingLogo} alt="landing"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>



        )
    }
}

Landing.propTypes = {
    security: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    security: state.security
})

export default connect(mapStateToProps)(Landing);