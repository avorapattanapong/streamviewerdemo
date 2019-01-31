import React, { Component } from 'react';
import '../css/App.css';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import ArrowBackIcon from '@material-ui/icons/ArrowBack'

class AuthenticatedLayout extends Component {
    render() {
        const { children } = this.props;
        return (
            <div className="AuthenticatedLayout">
                <AppBar classes={{root:"App-bar"}} position="static">
                    <Toolbar>
                        <Typography variant="h6" color="inherit">
                            Stream Viewer
                        </Typography>
                    </Toolbar>
                </AppBar>
                {children}
            </div>
        );
    }
}

export default AuthenticatedLayout;
