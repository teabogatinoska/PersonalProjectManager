import React, {Component} from 'react'
import ProjectTask from './ProjectTasks/ProjectTask';
import '../../App.css';

class Backlog extends Component {
    render() {

        const {project_tasks_prop} = this.props;
        const tasks = project_tasks_prop.map(project_task => (
            <ProjectTask key={project_task.id} project_task={project_task}/>
        ));

        let todoItems = [];
        let inProgressItems = [];
        let doneItems = [];

        for (let i = 0; i < tasks.length; i++) {
            if (tasks[i].props.project_task.status === "TO_DO") {
                todoItems.push(tasks[i])
            }
            if (tasks[i].props.project_task.status === "IN_PROGRESS") {
                inProgressItems.push(tasks[i])
            }
            if (tasks[i].props.project_task.status === "DONE") {
                doneItems.push(tasks[i])
            }
        }

        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-4">
                        <div className="card shadow p-2 mb-3 rounded text-center">
                            <div className="card-header rounded text-white toDo">
                                <h3>To Do</h3>
                            </div>
                        </div>

                        {todoItems}

                    </div>
                    <div className="col-md-4">
                        <div className="card shadow p-2 mb-3 rounded text-center">
                            <div className="card-header rounded text-white inProgress">
                                <h3>In Progress</h3>
                            </div>
                        </div>
                        {inProgressItems}
                    </div>
                    <div className="col-md-4">
                        <div className="card shadow p-2 mb-3 rounded text-center">
                            <div className="card-header rounded bg-success text-white done">
                                <h3>Done</h3>
                            </div>
                        </div>
                        {doneItems}
                    </div>
                </div>
            </div>

        )
    }
}

export default Backlog;