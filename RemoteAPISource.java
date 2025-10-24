class RemoteAPISource implements MediaSource {
    private final String apiBaseUrl;
    
    public RemoteAPISource(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }
    
    @Override
    public Media open(String mediaId) {
        return new Media() {
            @Override
            public String getId() {
                return mediaId;
            }

            @Override
            public String getUri() {
                return apiBaseUrl + "/media/" + mediaId + "/stream";
            }

            @Override
            public String toString() {
                return getUri();
            }
        };
    }
}
