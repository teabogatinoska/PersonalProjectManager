import React, {Component} from "react";
import {Link} from "react-router-dom";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {deleteProject} from "../../services/projectService";
import '../../App.css';

class ProjectItem extends Component {

    onDeleteClick = id => {
        this.props.deleteProject(id);
    };

    render() {

        const {project} = this.props;

        return (
            <div className="col-4">
                    <div className="card projectCard shadow-lg p-3 mb-5 bg-white rounded ">
                        <div className="card-body rounded projectBody">
                            <h5 className="card-title projectTitle">{project.projectIdentifier} - {project.projectName}</h5>
                            <p className="card-text projectText">{project.description}</p>

                        </div>
                        <ul className="list-group ">
                            <Link to={`/projectBoard/${project.projectIdentifier}`}>
                                <li className="list-group-item board ">
                                    <i className="fa fa-flag-checkered pr-1">View Project Board </i>
                                </li>
                            </Link>
                            <Link to={`/updateProject/${project.projectIdentifier}`}>
                                <li className="list-group-item update ">
                                    <i className="fa fa-edit pr-1"> Update Project Info</i>
                                </li>
                            </Link>
                            <li className="list-group-item delete" onClick={this.onDeleteClick.bind(
                                this,
                                project.projectIdentifier
                            )}>
                                <i className="fa fa-minus-circle pr-1"> Delete Project</i>
                            </li>
                        </ul>
                    </div>
            </div>

        )
    }
}

ProjectItem.propTypes = {
    deleteProject: PropTypes.func.isRequired
};


export default connect(null, {deleteProject})(ProjectItem);
