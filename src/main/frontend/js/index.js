console.log("Hello");

import React from "react";
import ReactDOM from "react-dom";

class App extends React.Component {
    render() {
        return <p>Testing Working {this.props.message}</p>;

    }
}

let app = document.getElementById("app");

ReactDOM.render(<App message="thisisworking"/>, app);