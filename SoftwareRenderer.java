class SoftwareRenderer implements Renderer {
    @Override
    public void render(Media media) {
        System.out.println("[SoftwareRenderer] Decoding (software) and presenting: " + media.getUri());
        for (int i = 1; i <= 3; i++) {
            System.out.println("[SoftwareRenderer] frame " + i + " for " + media.getId());
            try { 
                Thread.sleep(120); 
            } catch (InterruptedException e) { 
                Thread.currentThread().interrupt(); 
            }
        }
        System.out.println("[SoftwareRenderer] Playback finished for " + media.getId());
    }

    @Override
    public boolean supportsHardwareAcceleration() {
        return false;
    }
}
