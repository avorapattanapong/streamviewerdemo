package com.athikom.streamlabsdemo.streamlabsdemo.service;

public class TwitchApiException extends Exception {

    public TwitchApiException(String msg) {
        super(msg);
    }
}

class TwitchApiNotFoundException extends TwitchApiException {

    public TwitchApiNotFoundException(String msg) {
        super(msg);
    }
}

class TwitchApiBadRequestException extends TwitchApiException {

    public TwitchApiBadRequestException(String msg) {
        super(msg);
    }
}

class TwitchApiInternalServerErrorException extends TwitchApiException {

    public TwitchApiInternalServerErrorException(String msg) {
        super(msg);
    }
}