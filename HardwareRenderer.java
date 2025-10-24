class HardwareRenderer implements Renderer {
    @Override
    public void render(Media media) {
        System.out.println("[HardwareRenderer] Using hardware decode for: " + media.getUri());
        for (int i = 1; i <= 3; i++) {
            System.out.println("[HardwareRenderer] hw-frame " + i + " for " + media.getId());
            try { 
                Thread.sleep(80); 
            } catch (InterruptedException e) { 
                Thread.currentThread().interrupt(); 
            }
        }
        System.out.println("[HardwareRenderer] Playback finished for " + media.getId());
    }

    @Override
    public boolean supportsHardwareAcceleration() {
        return true;
    }
}
