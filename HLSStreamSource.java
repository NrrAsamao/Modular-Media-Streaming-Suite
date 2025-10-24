class HLSStreamSource implements MediaSource {
    private final String baseUrl;
    
    public HLSStreamSource(String baseUrl) {
        this.baseUrl = baseUrl;
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
                return baseUrl + "/" + mediaId + ".m3u8";
            }

            @Override
            public String toString() {
                return getUri();
            }
        };
    }
}
