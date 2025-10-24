class WatermarkingDecorator extends RendererDecorator {
    private final String watermarkText;
    private final String position;
    
    public WatermarkingDecorator(Renderer wrapped, String watermarkText, String position) {
        super(wrapped);
        this.watermarkText = watermarkText;
        this.position = position;
    }
    
    public WatermarkingDecorator(Renderer wrapped, String watermarkText) {
        this(wrapped, watermarkText, "bottom-right");
    }

    @Override
    public void render(Media media) {
        System.out.println("[WatermarkingDecorator] Adding watermark '" + watermarkText + "' at " + position + " for " + media.getId());
        
        wrapped.render(media);
        
        System.out.println("[WatermarkingDecorator] Watermark processing complete for " + media.getId());
    }
}
