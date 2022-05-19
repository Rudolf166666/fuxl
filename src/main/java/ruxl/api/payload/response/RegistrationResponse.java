package ruxl.api.payload.response;

import org.springframework.util.SerializationUtils;
import ruxl.api.models.User;

public  class RegistrationResponse {
    private  String access_token;
    private  String refresh_token;
    private  User user;

    public RegistrationResponse() {
    }

    public RegistrationResponse(String access_token, String refresh_token, User user) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public User getUser() {
        return user;
    }
}
