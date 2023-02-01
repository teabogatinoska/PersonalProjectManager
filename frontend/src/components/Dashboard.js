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
            perPage: 6,
            currentPage: 0
        };
        this.handlePageClick = this
            .handlePageClick
            .bind(this);
    }

    receivedData() {
        axios
            .get(`/api/project/all`)
            .then(res => {

                const data = res.data;
                const slice = data.slice(this.state.offset, this.state.offset + this.state.perPage)
                const postData = slice.map(pd => <ProjectItem key={pd.id} project={pd}/>)


                this.setState({
                    pageCount: Math.ceil(data.length / this.state.perPage),

                    postData
                })
            });
    }

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
                                pageRangeDisplayed={6}
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