import React from "react";
import ReactDOM from "react-dom";
import Paragraph from "./components/paragraph";
import Welcome from "./welcome";
import Stream from "./stream";
import Snackbar from '@material-ui/core/Snackbar';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page: "welcome",
            streamerName: "",
            open: false
        };
        this.handleOpen = this.handleOpen.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleChangeStreamerName = this.handleChangeStreamerName.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleBack = this.handleBack.bind(this);
    }

    handleOpen() {
        this.setState({
            open: true
        })
    }
    handleClose() {
        this.setState({
            open: false
        })
    }

    handleChangeStreamerName(event) {
        this.setState({
            streamerName: event.target.value
        });
    }

    handleSubmit() {
        this.setState({
            page:"stream"
        });
    }

    handleBack() {
        this.setState({
            page:"welcome"
        })
    }

    render() {
        let view =
            <Welcome
                streamerName={this.state.streamerName}
                name={user.name}
                handleSubmit={this.handleSubmit}
                handleChangeStreamerName={this.handleChangeStreamerName}
            />;
        if(this.state.page == "stream"){
            view =
                <Stream
                    handleSnackBarOpen={this.handleOpen}
                    handleSnackBarClose={this.handleClose}
                    streamerName={this.state.streamerName}
                    handleBack={this.handleBack}
                    contextPath={contextPath}
                    csrfToken={csrfToken}
                    csrfParameterName={csrfParameterName}
                />
        }
        return(
            <div className="App">
                {view}
                <div>
                    <Snackbar
                        anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'left',
                      }}
                        open={this.state.open}
                        onClose={this.handleClose}
                        ContentProps={{
                        'aria-describedby': 'message-id',
                      }}
                        message={<span id="message-id">Loading...</span>}
                        action={[
                        <IconButton
                          key="close"
                          aria-label="Close"
                          color="inherit"
                          className={"snackbar-close"}
                          onClick={this.handleClose}
                        >
                          <CloseIcon />
                        </IconButton>,
                      ]}
                        />
                </div>
            </div>
        )
    }
}
let app = document.getElementById("app");

ReactDOM.render(
        <App/>, app);