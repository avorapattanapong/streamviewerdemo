import React from "react";
import $ from "jquery";
import '../css/App.css';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import FormControl from '@material-ui/core/FormControl';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import AuthenticatedLayout from './AuthenticatedLayout.js'

export default class Stream extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            videoList: []
        }
    }

    componentDidMount() {
        this.props.handleSnackBarOpen();
        let self = this;
        let streamerName = this.props.streamerName;
        let csrfParameterName = this.props.csrfParameterName;
        let csrfToken = this.props.csrfToken;

        let embed = new Twitch.Embed("twitch-embed", {
            width: "100%",
            height: 550,
            channel: streamerName
        });
        embed.addEventListener(Twitch.Embed.VIDEO_READY, function() {
            let data = {
                streamerName: streamerName
            };
            data[(csrfParameterName)] = csrfToken;

            $.ajax({
                url: "/getVideoIds",
                data: data,
                method: "post",
                success: function(response) {
                    self.setState({
                        videoList: response
                    });
                    self.props.handleSnackBarClose();
                },
                error: function(){
                    console.log("failed")
                }
            });

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
        let videoListView = this.state.videoList.map(id =>{
            return(
                <Grid item xs={4}>
                    <Card className={"card-css"}>
                        <CardContent>
                            <iframe
                                src={"https://player.twitch.tv/?autoplay=false&video=" + id}
                                height="300"
                                width="100%"
                                frameborder="0"
                                scrolling="no"
                                allowfullscreen="true">
                            </iframe>
                        </CardContent>
                    </Card>
                </Grid>
            );
        });
        return (
            <AuthenticatedLayout title="Stream" handleBack={this.props.handleBack}>
                <main className="landing-main">
                    <Typography component="h2" variant="h2" gutterBottom>
                        Current Stream by {this.props.streamerName}
                    </Typography>
                    <Grid container spacing={24} classes={{container:"grid-bottom-margin"}}>
                        <Grid item xs={12}>
                            <div id="twitch-embed"></div>
                        </Grid>
                    </Grid>
                    <Typography component="h2" variant="h4" gutterBottom>
                        Recent Videos
                    </Typography>
                    <Grid container spacing={24}>
                        {videoListView}
                    </Grid>
                </main>
            </AuthenticatedLayout>
        )
    }
}