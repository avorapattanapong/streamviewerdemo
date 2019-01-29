import React from "react";

export default class Stream extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        new Twitch.Embed("twitch-embed", {
            width: 854,
            height: 480,
            channel: "monstercat"
        });
    }

    render() {
        return (
            <div id="twitch-embed"></div>
        )
    }
}