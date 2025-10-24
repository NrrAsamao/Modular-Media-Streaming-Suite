class LocalFileSource implements MediaSource {
    @Override
    public Media open(String mediaId) {
        return new Media() {
            @Override
            public String getId() {
                return mediaId;
            }

            @Override
            public String getUri() {
                return "file:///local/" + mediaId + ".mp4";
            }

            @Override
            public String toString() {
                return getUri();
            }
        };
    }
}
