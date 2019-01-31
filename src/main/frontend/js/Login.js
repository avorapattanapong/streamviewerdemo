import React, { Component } from 'react';
import ReactDOM from "react-dom";
import logo from '../img/GlitchIcon_White_24px.png';
import '../css/App.css';
import Paper from '@material-ui/core/Paper';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

class LoginPanel extends Component {
    render() {
        return (
            <div className={"Login"}>
                <Paper classes={{root:"login-paper"}}>
                    <header className={"login-header"}>
                        <Typography component="h2" variant="h1" gutterBottom>
                            Stream Viewer
                        </Typography>
                    </header>
                    <main className={"login-main"}>
                        <Typography classes={{root:"login-description"}} variant="subtitle1" gutterBottom>Welcome to Stream Viewer, login below</Typography>
                        <Button href="/auth" classes={{root:"login-button"}} color="primary" variant="contained">
                            <img src={logo}/> <span style={{marginLeft:"1em"}}>Login with Twitch</span>
                        </Button>
                    </main>
                </Paper>
            </div>
        );
    }
}

export default LoginPanel;

let loginPanel = document.getElementById("login");

ReactDOM.render(
    <LoginPanel/>, loginPanel);