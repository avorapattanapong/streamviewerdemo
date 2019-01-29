import React from "react";
import ReactDOM from "react-dom";
import {BrowserRouter} from 'react-router-dom'
import { NavLink } from 'react-router-dom'
import { Route } from 'react-router-dom'
import Paragraph from "./components/paragraph";
import Welcome from "./welcome";
import Stream from "./stream";

class App extends React.Component {
    render() {
        console.log(user);
        return(
            <div id="nav">
                <NavLink exact to="/one">One</NavLink>
                <NavLink exact to="/two">Two</NavLink>
                <div className="content">
                    <Route exact path="/one" render={()=> <Welcome name={user.name} />} />
                    <Route exact path="/two" render={()=> <Stream/>} />
                </div>
            </div>
        )
    }
}
let app = document.getElementById("app");

ReactDOM.render(
    <BrowserRouter>
        <App/>
    </BrowserRouter>, app);