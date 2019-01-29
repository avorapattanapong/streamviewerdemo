import React from "react";

export default class Welcome extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            streamerName: ""
        };
    }

    handleChangeStreamerName(event) {
        this.setState({
            streamerName: event.target.value
        });
    }

    render() {
        return (
            <div>
                <h1>Welcome back, {this.props.name}</h1>
                <label>
                    Enter your favorite streamer name:
                    <input type="text" value={this.state.streamerName} onChange={this.handleChangeStreamerName} />
                </label>
                <button onClick={()=>{console.log("submit")}}>Submit</button>
            </div>
        )
    }
}