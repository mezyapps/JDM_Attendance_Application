package com.mezyapps.jmdinfotech.model;

public class InOutResponse {
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private String response;


        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        private String tag;
        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getLogin_time() {
            return login_time;
        }

        public void setLogin_time(String login_time) {
            this.login_time = login_time;
        }

        public String getLogout_time() {
            return logout_time;
        }

        public void setLogout_time(String logout_time) {
            this.logout_time = logout_time;
        }

        private String success;
        private String message;
        private String login_time;
        private String logout_time;


    }
