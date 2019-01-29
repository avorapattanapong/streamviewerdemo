import React from "react";
import ReactDOM from "react-dom";
import Paragraph from "./components/paragraph";
import Welcome from "./welcome";
import Stream from "./stream";

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page: "welcome",
            streamerName: ""
        };
        this.handleChangeStreamerName = this.handleChangeStreamerName.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleBack = this.handleBack.bind(this);
    }

    handleChangeStreamerName(event) {
        this.setState({
            streamerName: event.target.value
        });
    }

    handleSubmit() {
        this.setState({
            page:"stream"
        });
    }

    handleBack() {
        this.setState({
            page:"welcome"
        })
    }

    render() {
        let view =
            <Welcome
                streamerName={this.state.streamerName}
                name={user.name}
                handleSubmit={this.handleSubmit}
                handleChangeStreamerName={this.handleChangeStreamerName}
            />;
        if(this.state.page == "stream"){
            view =
                <Stream
                    streamerName={this.state.streamerName}
                    handleBack={this.handleBack}
                    contextPath={contextPath}
                    csrfToken={csrfToken}
                    csrfParameterName={csrfParameterName}
                />
        }
        return(
            <div>{view}</div>
        )
    }
}
let app = document.getElementById("app");

ReactDOM.render(
        <App/>, app);