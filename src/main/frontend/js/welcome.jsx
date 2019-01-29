import React from "react";

export default class Welcome extends React.Component {
    render() {
        return (
            <div>
                <h1>Welcome back, {this.props.name}</h1>
                <label>
                    Enter your favorite streamer name:
                    <input type="text" value={this.props.streamerName} onChange={this.props.handleChangeStreamerName} />
                </label>
                <button onClick={this.props.handleSubmit}>Submit</button>
            </div>
        )
    }
}