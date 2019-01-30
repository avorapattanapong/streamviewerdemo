import React from "react";
import $ from "jquery";

export default class Stream extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading:true,
            videoList: []
        }
    }

    componentDidMount() {
        let self = this;
        let streamerName = this.props.streamerName;
        let csrfParameterName = this.props.csrfParameterName;
        let csrfToken = this.props.csrfToken;
        let embed = new Twitch.Embed("twitch-embed", {
            width: 854,
            height: 480,
            channel: streamerName
        });
        embed.addEventListener(Twitch.Embed.VIDEO_READY, function() {
            self.setState({
                isLoading: false
            });

            let data = {
                streamerName: streamerName
            };

            data[(csrfParameterName)] = csrfToken;
            $.ajax({
                url: "/getVideoIds",
                data: data,
                method: "post",
                success: function(response) {
                    self.setState({videoList:response})
                },
                error: function(){
                    console.log("failed")
                }
            });

            data[(csrfParameterName)] = csrfToken;
            $.ajax({
                url: "/subscribe",
                data: data,
                method: "post",
                success: function() {
                    console.log("success")
                },
                error: function(){
                    console.log("failed")
                }
            });
        });
    }

    render() {
        let loadingScreen = <div>Loading</div>;
        if(!this.state.isLoading) {
            loadingScreen = null;
        }
        let videoListView = this.state.videoList.map(id =>{
            return(<iframe
                src={"https://player.twitch.tv/?autoplay=false&video=" + id}
                height="300"
                width="400"
                frameborder="0"
                scrolling="no"
                allowfullscreen="true">
            </iframe>);
        });
        return (
            <div>
                {loadingScreen}
                <div id="twitch-embed"></div>
                <div>
                {videoListView.map(view => view)}
                </div>
            </div>
        )
    }
}