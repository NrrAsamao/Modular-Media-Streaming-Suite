class Playlist {
    private final String name;
    private final java.util.List<String> items = new java.util.ArrayList<>();

    public Playlist(String name) { 
        this.name = name; 
    }

    public void add(String mediaId) { 
        items.add(mediaId); 
    }

    public java.util.List<String> getItems() { 
        return items; 
    }

    public String getName() { 
        return name; 
    }
}
