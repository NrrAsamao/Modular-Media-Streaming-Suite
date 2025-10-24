class CompositePlaylist {
    private final String name;
    private final java.util.List<Object> items = new java.util.ArrayList<>();
    
    public CompositePlaylist(String name) {
        this.name = name;
    }
    
    public void addMedia(String mediaId) {
        items.add(mediaId);
    }
    
    public void addPlaylist(CompositePlaylist playlist) {
        items.add(playlist);
    }
    
    public java.util.List<String> getAllMediaIds() {
        java.util.List<String> allIds = new java.util.ArrayList<>();
        for (Object item : items) {
            if (item instanceof String) {
                allIds.add((String) item);
            } else if (item instanceof CompositePlaylist) {
                allIds.addAll(((CompositePlaylist) item).getAllMediaIds());
            }
        }
        return allIds;
    }
    
    public java.util.List<Object> getItems() {
        return items;
    }
    
    public String getName() {
        return name;
    }
    
    public int getTotalMediaCount() {
        return getAllMediaIds().size();
    }
}
