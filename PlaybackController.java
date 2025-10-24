class PlaybackController {
    private final MediaSource source;
    private Renderer renderer;

    public PlaybackController(MediaSource source, Renderer renderer) {
        this.source = source;
        this.renderer = renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void play(String mediaId) {
        Media m = source.open(mediaId);
        if (m == null) {
            System.out.println("[PlaybackController] Media not found: " + mediaId);
            return;
        }
        System.out.println("[PlaybackController] Starting playback for: " + m.getUri());
        renderer.render(m);
    }
}
