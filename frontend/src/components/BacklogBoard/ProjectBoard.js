import React, {Component} from 'react'
import {Link} from 'react-router-dom';
import Backlog from './Backlog';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import {getBacklog} from '../../services/backlogService';

class ProjectBoard extends Component {

    constructor() {
        super();
        this.state = {
            errors: {}
        };
    }

    componentDidMount() {
        const {id} = this.props.match.params;
        this.props.getBacklog(id);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({errors: nextProps.errors});
        }
    }

    render() {

        const {id} = this.props.match.params;
        const {project_tasks} = this.props.backlog;
        const {errors} = this.state;


        let BoardContent;

        const boardTasks = (errors, project_tasks) => {
            if (project_tasks.length < 1) {
                if (errors.projectNotFound) {
                    return (
                        <div className="alert alert-danger text-center" role="alert">
                            {errors.projectNotFound}
                        </div>
                    );
                } else if (errors.projectIdentifier) {
                    return (
                        <div className="alert alert-danger text-center" role="alert">
                            {errors.projectIdentifier}
                        </div>
                    );
                } else {
                    return (
                        <div className="alert alert-info text-center" role="alert">
                            No Project Tasks on this board
                        </div>
                    );
                }
            } else {
                return <Backlog project_tasks_prop={project_tasks}/>;
            }
        };

        BoardContent = boardTasks(errors, project_tasks);


        return (
            <div className="backlog">
                <div className="container">
                    <Link to={`/addProjectTask/${id}`} className="btn rounded btn-primary mb-3">
                        <i className="fas fa-plus-circle"> Create Project Task</i>
                    </Link>
                    <br/>
                    <hr/>
                    {BoardContent}
                </div>
            </div>

        )
    }
}

ProjectBoard.propTypes = {
    backlog: PropTypes.object.isRequired,
    getBacklog: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    backlog: state.backlog,
    errors: state.errors
})


export default connect(mapStateToProps, {getBacklog})(ProjectBoard);