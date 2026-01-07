package com.hungerbox.identity.constant;

public class ErrorMessageWarnConstant {

    public enum Warn {
        INCOMPLETE_PROFILE("User profile is missing some details"),
        SESSION_EXPIRING_SOON("Your session will expire in 5 minutes");

        private final String message;

        Warn(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum Error {
        USER_NOT_FOUND("The requested user does not exist"),
        INVALID_CREDENTIALS("Invalid username or password"),
        INTERNAL_SERVER_ERROR("An unexpected error occurred");

        private final String message;

        Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum Message {
        AUTH_REG_SUCCESS("User registered successfully"),
        AUTH_LOGIN_SUCCESS("User Login Successful"),
        VALID_TOKEN("Token is valid"),
        PASSWORD_RESET_LINK_SENT("A reset link has been sent to your email");

        private final String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}