class SubtitleDecorator extends RendererDecorator {
    public SubtitleDecorator(Renderer wrapped) {
        super(wrapped);
    }

    @Override
    public void render(Media media) {
        System.out.println("[SubtitleDecorator] Initializing subtitle track for " + media.getId());
        wrapped.render(media);
        System.out.println("[SubtitleDecorator] Displaying final subtitle credits for " + media.getId());
    }
}
