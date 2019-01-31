import React, { Component } from 'react';
import './App.css';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';

class AuthenticatedLayout extends Component {
    render() {
        return (
            <div className="AuthenticatedLayout">
                <AppBar classes={{root:"App-bar"}} position="static">
                    <Toolbar>
                        <Typography variant="h6" color="inherit">
                            Stream Viewer
                        </Typography>
                    </Toolbar>
                </AppBar>
                {this.props.children}
            </div>
        );
    }
}

export default AuthenticatedLayout;
