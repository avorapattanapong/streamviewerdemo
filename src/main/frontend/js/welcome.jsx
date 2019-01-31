import React from "react";
import '../css/App.css';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import AuthenticatedLayout from './AuthenticatedLayout.js'
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import FormControl from '@material-ui/core/FormControl';

export default class Welcome extends React.Component {
    render() {
        return (
        <AuthenticatedLayout>
            <main className="landing-main">
                <Typography component="h2" variant="h2" gutterBottom>
                    Welcome back, {this.props.name}
                </Typography>
                <Grid container spacing={24}>
                    <Grid item xs={12}>
                        <Paper style={{padding:"2em"}}>
                            <Typography variant="subtitle1" gutterBottom>Enter your favorite streamer name to view his/her channel</Typography>
                            <form className={"form-container"} noValidate autoComplete="off">
                                <FormControl component="fieldset">
                                    <TextField
                                        id="standard-name"
                                        label="Streamer Name"
                                        value={this.props.streamerName}
                                        onChange={this.props.handleChangeStreamerName}
                                        margin="normal"
                                        />
                                </FormControl>
                                <FormControl component="fieldset" className="form-control-button" >
                                    <Button onClick={this.props.handleSubmit} className="button-override" variant="contained" color="primary">
                                        Submit
                                    </Button>
                                </FormControl>
                            </form>
                        </Paper>
                    </Grid>
                </Grid>
            </main>
        </AuthenticatedLayout>
        )
    }
}