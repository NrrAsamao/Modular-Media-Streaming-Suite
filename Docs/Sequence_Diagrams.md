# Sequence Diagrams - Modular Media Streaming System

## Overview
This document contains sequence diagrams for the two core flows in the Modular Media Streaming System:
1. **Play Media Flow** - The basic media playback process
2. **Apply Decorator Stack Flow** - The process of applying multiple decorators to enhance rendering

---

## 1. Play Media Flow

This sequence diagram shows the complete flow when a user requests to play media through the system.

```mermaid
sequenceDiagram
    participant Client
    participant PC as PlaybackController
    participant MS as MediaSource
    participant R as Renderer
    participant M as Media

    Client->>PC: play(mediaId)
    
    Note over PC: Orchestrates playback process
    
    PC->>MS: open(mediaId)
    Note over MS: Retrieves media from source
    MS-->>PC: Media object
    
    PC->>R: render(media)
    Note over R: Processes and displays media
    
    R-->>PC: render complete
    PC-->>Client: playback started
    
    Note over Client, M: Media is now playing through the renderer
```

### Play Media Flow - Detailed with Caching

This enhanced version shows the flow when using a cached media source.

```mermaid
sequenceDiagram
    participant Client
    participant PC as PlaybackController
    participant CMS as CachingMediaSource
    participant RS as RemoteAPISource
    participant R as Renderer
    participant M as Media

    Client->>PC: play(mediaId)
    
    PC->>CMS: open(mediaId)
    
    alt Media is cached
        Note over CMS: Cache hit - return cached media
        CMS-->>PC: Media (from cache)
    else Media not cached
        Note over CMS: Cache miss - fetch from source
        CMS->>RS: open(mediaId)
        RS-->>CMS: Media object
        Note over CMS: Store in cache
        CMS-->>PC: Media object
    end
    
    PC->>R: render(media)
    R-->>PC: render complete
    PC-->>Client: playback started
```

---

## 2. Apply Decorator Stack Flow

This sequence diagram shows how multiple decorators are applied to create an enhanced renderer with multiple features.

```mermaid
sequenceDiagram
    participant Client
    participant PC as PlaybackController
    participant MS as MediaSource
    participant WD as WatermarkingDecorator
    participant AED as AudioEqualizerDecorator
    participant SD as SubtitleDecorator
    participant HR as HardwareRenderer
    participant M as Media

    Note over Client: Creating complex decorator stack
    Client->>HR: new HardwareRenderer()
    Client->>SD: new SubtitleDecorator(HR)
    Client->>AED: new AudioEqualizerDecorator(SD)
    Client->>WD: new WatermarkingDecorator(AED, "© 2024", "bottom-right")
    
    Note over Client: Decorator stack: WD -> AED -> SD -> HR
    
    Client->>PC: new PlaybackController(MS, WD)
    Client->>PC: play(mediaId)
    
    PC->>MS: open(mediaId)
    MS-->>PC: Media object
    
    PC->>WD: render(media)
    Note over WD: Apply watermark processing
    WD->>AED: render(media)
    Note over AED: Apply audio equalization
    AED->>SD: render(media)
    Note over SD: Apply subtitle processing
    SD->>HR: render(media)
    Note over HR: Hardware rendering with all enhancements
    HR-->>SD: render complete
    SD-->>AED: render complete
    AED-->>WD: render complete
    WD-->>PC: render complete
    
    PC-->>Client: enhanced playback started
```

### Decorator Stack Flow - Step by Step

This detailed version shows the internal processing of each decorator in the stack.

```mermaid
sequenceDiagram
    participant WD as WatermarkingDecorator
    participant AED as AudioEqualizerDecorator
    participant SD as SubtitleDecorator
    participant HR as HardwareRenderer
    participant M as Media

    Note over WD, HR: Decorator Chain Processing
    
    WD->>WD: preProcess(media)
    Note over WD: Prepare watermark data
    
    WD->>AED: render(media)
    AED->>AED: preProcess(media)
    Note over AED: Apply EQ settings
    
    AED->>SD: render(media)
    SD->>SD: preProcess(media)
    Note over SD: Load subtitle file
    
    SD->>HR: render(media)
    Note over HR: Core hardware rendering
    HR->>HR: initializeHardware()
    HR->>HR: renderVideo(media)
    HR->>HR: renderAudio(media)
    HR-->>SD: render complete
    
    SD->>SD: postProcess(media)
    Note over SD: Overlay subtitles
    SD-->>AED: render complete
    
    AED->>AED: postProcess(media)
    Note over AED: Apply audio effects
    AED-->>WD: render complete
    
    WD->>WD: postProcess(media)
    Note over WD: Overlay watermark
    WD-->>WD: render complete
```

---

## 3. Complete System Flow with All Components

This comprehensive sequence diagram shows the complete flow including playlist management and multiple media sources.

```mermaid
sequenceDiagram
    participant Client
    participant PC as PlaybackController
    participant CP as CompositePlaylist
    participant LFS as LocalFileSource
    participant RAS as RemoteAPISource
    participant CMS as CachingMediaSource
    participant WD as WatermarkingDecorator
    participant AED as AudioEqualizerDecorator
    participant HR as HardwareRenderer

    Note over Client: Setup phase
    Client->>CP: new CompositePlaylist("Music Library")
    Client->>CP: addMedia("song1")
    Client->>CP: addMedia("song2")
    
    Client->>LFS: new LocalFileSource()
    Client->>RAS: new RemoteAPISource("https://api.example.com")
    Client->>CMS: new CachingMediaSource(RAS, 100)
    
    Client->>HR: new HardwareRenderer()
    Client->>AED: new AudioEqualizerDecorator(HR)
    Client->>WD: new WatermarkingDecorator(AED, "© 2024", "bottom-right")
    
    Client->>PC: new PlaybackController(LFS, WD)
    
    Note over Client: Playback phase
    loop For each media in playlist
        Client->>CP: getAllMediaIds()
        CP-->>Client: [song1, song2, ...]
        
        Client->>PC: play(mediaId)
        
        alt Local media
            PC->>LFS: open(mediaId)
            LFS-->>PC: Media object
        else Remote media
            PC->>CMS: open(mediaId)
            alt Cache hit
                CMS-->>PC: Media (cached)
            else Cache miss
                CMS->>RAS: open(mediaId)
                RAS-->>CMS: Media object
                CMS-->>PC: Media object
            end
        end
        
        PC->>WD: render(media)
        WD->>AED: render(media)
        AED->>HR: render(media)
        HR-->>AED: complete
        AED-->>WD: complete
        WD-->>PC: complete
        
        PC-->>Client: playback complete
    end
```

---

## Key Interactions Explained

### Play Media Flow
1. **Client Request**: User calls `play(mediaId)` on PlaybackController
2. **Media Retrieval**: PlaybackController requests media from MediaSource
3. **Media Processing**: MediaSource returns Media object
4. **Rendering**: PlaybackController passes Media to Renderer
5. **Playback**: Renderer processes and displays the media

### Decorator Stack Flow
1. **Stack Creation**: Decorators are chained together (outer to inner)
2. **Request Propagation**: Render request flows through each decorator
3. **Feature Application**: Each decorator adds its specific functionality
4. **Core Rendering**: Base renderer performs actual media processing
5. **Response Propagation**: Completion flows back through the stack

### Benefits of This Architecture
- **Modularity**: Each component has a single responsibility
- **Extensibility**: New decorators can be added without modifying existing code
- **Flexibility**: Different combinations of features can be applied at runtime
- **Performance**: Caching and hardware acceleration optimize playback
- **Maintainability**: Clear separation of concerns makes the system easy to understand and modify

---

## Design Pattern Applications

### Decorator Pattern
- **Purpose**: Add features dynamically without modifying existing classes
- **Implementation**: Chain decorators around base renderer
- **Benefits**: Runtime feature composition, single responsibility

### Facade Pattern
- **Purpose**: Simplify complex subsystem interactions
- **Implementation**: PlaybackController hides complexity
- **Benefits**: Single entry point, decoupled client code

### Adapter Pattern
- **Purpose**: Add caching to existing media sources
- **Implementation**: CachingMediaSource wraps other sources
- **Benefits**: Transparent performance enhancement

### Composite Pattern
- **Purpose**: Handle hierarchical playlist structures
- **Implementation**: CompositePlaylist contains media and other playlists
- **Benefits**: Uniform treatment of individual and composite objects
