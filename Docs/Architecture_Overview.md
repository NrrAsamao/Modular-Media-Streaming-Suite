# Architecture Overview - Modular Media Streaming System

## System Structure

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client Code   │───▶│PlaybackController│───▶│   MediaSource   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │                        │
                                ▼                        ▼
                       ┌─────────────────┐    ┌─────────────────┐
                       │    Renderer     │    │     Media      │
                       └─────────────────┘    └─────────────────┘
```

## Core Components

### 🎮 **PlaybackController** (Facade)
- **Single entry point** for all media operations
- Orchestrates media retrieval and rendering
- Hides system complexity from clients

### 📁 **MediaSource** (Strategy)
- **LocalFileSource**: Local file system access
- **RemoteAPISource**: HTTP API integration  
- **HLSStreamSource**: Live streaming support
- **CachingMediaSource**: Performance optimization

### 🎨 **Renderer** (Strategy + Decorator)
- **SoftwareRenderer**: CPU-based rendering
- **HardwareRenderer**: GPU-accelerated rendering
- **Decorators**: Feature enhancements (subtitles, watermarks, audio EQ)

### 📋 **Playlist Management** (Composite)
- **Playlist**: Basic media collections
- **CompositePlaylist**: Hierarchical structures

## Key Design Patterns

| Pattern | Purpose | Location |
|---------|---------|----------|
| **Facade** | Simplify interface | `PlaybackController` |
| **Decorator** | Add features dynamically | `RendererDecorator` hierarchy |
| **Composite** | Hierarchical organization | `CompositePlaylist` |
| **Adapter** | Add caching transparently | `CachingMediaSource` |
| **Strategy** | Algorithm switching | `MediaSource` & `Renderer` interfaces |

## Data Flow

```
1. Client → PlaybackController.play(mediaId)
2. Controller → MediaSource.open(mediaId) → Media
3. Controller → Renderer.render(media)
4. Media plays with all enhancements
```

## Benefits

- **🎯 Modular**: Each component has one responsibility
- **🔧 Extensible**: Easy to add new sources/renderers/features
- **⚡ Performant**: Caching and hardware acceleration
- **🧪 Testable**: Clear interfaces and dependency injection
- **🎨 Flexible**: Runtime composition of features

## Usage Example

```java
// Simple setup
PlaybackController controller = new PlaybackController(
    new CachingMediaSource(new RemoteAPISource("api.com")),
    new WatermarkingDecorator(new HardwareRenderer())
);
controller.play("video.mp4");
```

## Architecture Principles

- **Single Responsibility**: One job per component
- **Open/Closed**: Open for extension, closed for modification  
- **Dependency Inversion**: Depend on abstractions
- **Interface Segregation**: Small, focused interfaces
