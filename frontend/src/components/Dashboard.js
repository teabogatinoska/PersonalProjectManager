import React, {Component} from 'react';
import ReactPaginate from 'react-paginate';
import ProjectItem from './Project/ProjectItem';
import CreateProjectButton from './Project/CreateProjectButton';
import {connect} from 'react-redux';
import {getProjects} from '../services/projectService';
import PropTypes from 'prop-types';
import axios from "axios";
import '../App.css';
import '../index.css'


class Dashboard extends Component {


    componentDidMount() {

        this.receivedData();
    }

    constructor(props) {
        super(props);
        this.state = {
            offset: 0,
            data: [],
            perPage: 3,
            currentPage: 0
            // projects: [],
            // selectedProject: ""
        };
        this.handlePageClick = this.handlePageClick.bind(this);
        //this.onTermSubmit = this.onTermSubmit.bind(this);
    }

    receivedData() {
        axios
            .get(`/api/project/all`)
            .then(res => {

                const data = res.data;
                const slice = data.slice(this.state.offset, this.state.offset + this.state.perPage)
                const postData = slice.map(pd => <ProjectItem key={pd.id} project={pd}/>)
                const list = [];
                for (let i = 0; i < postData.length; i++) {
                    list.push(postData[i].props.project);
                }

                this.state.projects = list;

                this.setState({

                    pageCount: Math.ceil(data.length / this.state.perPage),

                    postData
                })
            });
    }

    /*    onTermSubmit(e) {
            const lowerCase = e.target.value.toLowerCase();
            console.log("LOWERCASE: ", lowerCase)
            const names = [];
            const projects = [];
            let selected = null;

            console.log("Projects: ", this.state.projects);

            for (let p = 0; p < this.state.projects.length; p++) {
                names.push(this.state.projects[p].projectName.toLowerCase());
                projects.push(this.state.projects[p]);
            }

            console.log("PROJECTS: ", projects);
            if (names.includes(lowerCase)) {
            console.log("VLEGOV")
                for (let i = 0; i < projects.length; i++) {
                    if (projects[i].projectName.toLowerCase() === lowerCase) {
                        selected = projects[i];


                        console.log("selected: ", selected);
                    }
                }
            }
            // this.setState({selectedProject: selected})
            this.state.selectedProject = selected;
            this.state.postData = selected;
            console.log("state: ", this.state.selectedProject);
            console.log("Post data: ", this.state.postData);
        }*/

    handlePageClick = (e) => {
        const selectedPage = e.selected;
        const offset = selectedPage * this.state.perPage;
        this.setState({
            currentPage: selectedPage,
            offset: offset
        }, () => {
            this.receivedData()
        });

    };

    render() {

        return (

            <div className="projects">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12">

                            <h1 className="display-6 text-center">My Projects</h1>
                            <hr/>
                            <br/>


                            <CreateProjectButton/>


                            {/*<div className="col-md-6">*/}
                            {/*        <form onSubmit={this.onTermSubmit}>*/}
                            {/*        <input type="text" placeholder="Search.." className="form-control searchBar"*/}
                            {/*               onChange={this.onTermSubmit}/>*/}
                            {/*        <button type="submit">Submit</button>*/}
                            {/*    /!*<div className="row">*!/*/}
                            {/*    /!*    <ProjectItem  project={this.state.selectedProject}/>*!/*/}
                            {/*    /!*</div>*!/*/}
                            {/*            {this.state.selectedProject}*/}
                            {/*        </form>*/}

                            {/*</div>*/}


                            <br/>
                            <br/>
                            <div className="row">
                                {this.state.postData}
                            </div>
                            <ReactPaginate
                                previousLabel={"prev"}
                                nextLabel={"next"}
                                breakLabel={"..."}
                                breakClassName={"break-me"}
                                pageCount={this.state.pageCount}
                                marginPagesDisplayed={2}
                                pageRangeDisplayed={3}
                                onPageChange={this.handlePageClick}
                                containerClassName={"pagination"}
                                subContainerClassName={"pages pagination"}
                                activeClassName={"active"}/>


                        </div>
                    </div>
                </div>

            </div>
        )
    }
}

Dashboard.propTypes = {
    project: PropTypes.object.isRequired,
    getProjects: PropTypes.func.isRequired
}


const mapStateToProps = state => ({
    project: state.project
})


export default connect(mapStateToProps, {getProjects})(Dashboard);