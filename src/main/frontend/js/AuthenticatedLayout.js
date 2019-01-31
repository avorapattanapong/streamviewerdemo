import React, { Component } from 'react';
import '../css/App.css';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import ArrowBackIcon from '@material-ui/icons/ArrowBack'
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';

class AuthenticatedLayout extends Component {
    render() {
        const { children } = this.props;
        return (
            <div className="AuthenticatedLayout">
                <AppBar classes={this.props.handleBack? {root:"App-bar"}:{root:"App-bar App-bar-padding"}} position="static">
                    <Toolbar>
                        {this.props.handleBack &&
                        <IconButton onClick={this.props.handleBack} className={"App-bar-back-icon"} color="inherit" aria-label="Menu">
                            <ArrowBackIcon />
                        </IconButton>
                        }
                        <Typography variant="h6" color="inherit">
                            {this.props.title}
                        </Typography>
                    </Toolbar>
                </AppBar>
                {children}
            </div>
        );
    }
}

export default AuthenticatedLayout;
