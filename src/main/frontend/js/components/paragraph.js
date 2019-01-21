import React from "react";

export default class Paragraph extends React.Component {
    render() {
        return <p>Testing Working {this.props.message}</p>;
    }
}
