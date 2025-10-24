class CachingMediaSource implements MediaSource {
    private final MediaSource wrapped;
    private final java.util.Map<String, Media> cache = new java.util.HashMap<>();
    private final int maxCacheSize;
    
    public CachingMediaSource(MediaSource wrapped, int maxCacheSize) {
        this.wrapped = wrapped;
        this.maxCacheSize = maxCacheSize;
    }
    
    public CachingMediaSource(MediaSource wrapped) {
        this(wrapped, 100);
    }

    @Override
    public Media open(String mediaId) {
        if (cache.containsKey(mediaId)) {
            System.out.println("[CachingMediaSource] Cache HIT for: " + mediaId);
            return cache.get(mediaId);
        }
        
        System.out.println("[CachingMediaSource] Cache MISS for: " + mediaId + " - fetching from source");
        Media media = wrapped.open(mediaId);
        
        if (media != null) {
            if (cache.size() >= maxCacheSize) {
                String oldestKey = cache.keySet().iterator().next();
                cache.remove(oldestKey);
                System.out.println("[CachingMediaSource] Cache full - removed: " + oldestKey);
            }
            cache.put(mediaId, media);
            System.out.println("[CachingMediaSource] Cached: " + mediaId);
        }
        
        return media;
    }
    
    public void clearCache() {
        cache.clear();
        System.out.println("[CachingMediaSource] Cache cleared");
    }
    
    public int getCacheSize() {
        return cache.size();
    }
    
    public boolean isCached(String mediaId) {
        return cache.containsKey(mediaId);
    }
}
