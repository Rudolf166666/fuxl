package ruxl.api.payload.request;

import javax.validation.constraints.NotEmpty;

public  class RefreshRequest {
    @NotEmpty
    private  String refresh_token;

    public RefreshRequest() {
    }

    public RefreshRequest(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }
}
