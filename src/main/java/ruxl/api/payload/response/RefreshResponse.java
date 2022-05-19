package ruxl.api.payload.response;

public class RefreshResponse {
    private  String refresh_token;
    private  String access_token;

    public RefreshResponse() {
    }

    public RefreshResponse(String refresh_token, String access_token) {
        this.refresh_token = refresh_token;
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }
}
