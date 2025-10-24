abstract class RendererDecorator implements Renderer {
    protected final Renderer wrapped;

    protected RendererDecorator(Renderer wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean supportsHardwareAcceleration() {
        return wrapped.supportsHardwareAcceleration();
    }

    @Override
    public abstract void render(Media media);
}
