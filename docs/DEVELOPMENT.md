# Developer Documentation

This document provides detailed information for developers who want to build, modify, or contribute to the Leaderboards mod.

## Table of Contents

- [Development Environment Setup](#development-environment-setup)
- [Building the Project](#building-the-project)
- [Project Structure](#project-structure)
- [API Architecture](#api-architecture)
- [Adding New Leaderboards](#adding-new-leaderboards)
- [Testing](#testing)
- [Contributing](#contributing)

## Development Environment Setup

### Prerequisites

- **Java Development Kit (JDK) 21** or higher
- **Git** for version control
- **IDE** (IntelliJ IDEA recommended, Eclipse also works)

### Clone and Setup

```bash
# Clone the repository
git clone <repository-url>
cd Leaderboards

# On Windows
gradlew.bat setupDecompWorkspace
gradlew.bat idea  # For IntelliJ IDEA
# or
gradlew.bat eclipse  # For Eclipse

# On Linux/Mac
./gradlew setupDecompWorkspace
./gradlew idea
# or
./gradlew eclipse
```

### IDE Configuration

#### IntelliJ IDEA
1. Open the project root folder in IntelliJ
2. Gradle should auto-import the project
3. Wait for indexing to complete
4. Run configurations will be automatically created

#### Eclipse
1. Import as existing Gradle project
2. Select the project root folder
3. Wait for Gradle sync to complete

## Building the Project

### Standard Build

```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

Output: `build/libs/leaderboards-1.21.1-NeoForge-<version>.jar`

### Clean Build

```bash
# Windows
gradlew.bat clean build

# Linux/Mac
./gradlew clean build
```

### Running in Development

```bash
# Run client
gradlew.bat runClient

# Run server
gradlew.bat runServer

# Run with debugging
gradlew.bat runClient --debug-jvm
```

## Project Structure

```
Leaderboards/
├── src/
│   └── main/
│       ├── java/com/leclowndu93150/leaderboards/
│       │   ├── Leaderboards.java              # Main mod class
│       │   ├── LeaderboardRegistry.java       # Leaderboard registration
│       │   ├── VanillaStatsRegistry.java      # Vanilla stats leaderboards
│       │   ├── api/
│       │   │   └── LeaderboardApiServer.java  # HTTP API server
│       │   ├── client/
│       │   │   ├── LeaderboardsClient.java    # Client initialization
│       │   │   └── LeaderboardsClientEvents.java # Client event handlers
│       │   ├── config/
│       │   │   └── LeaderboardConfig.java     # Configuration definitions
│       │   ├── data/
│       │   │   ├── Leaderboard.java           # Leaderboard data model
│       │   │   ├── LeaderboardValue.java      # Leaderboard entry
│       │   │   ├── PlayerDataTracker.java     # Player data persistence
│       │   │   └── PlayerStatsWrapper.java    # Player stats wrapper
│       │   ├── gui/
│       │   │   ├── LeaderboardListScreen.java # Leaderboard selection GUI
│       │   │   ├── LeaderboardScreen.java     # Leaderboard display GUI
│       │   │   └── VanillaStatsListScreen.java # Vanilla stats GUI
│       │   ├── integration/
│       │   │   └── FTBQuestsIntegration.java  # FTB Quests integration
│       │   └── network/
│       │       ├── LeaderboardListResponsePacket.java
│       │       ├── LeaderboardResponsePacket.java
│       │       ├── RequestLeaderboardListPacket.java
│       │       └── RequestLeaderboardPacket.java
│       └── resources/
│           └── META-INF/
│               └── neoforge.mods.toml         # Mod metadata
├── build.gradle                               # Build configuration
├── gradle.properties                          # Gradle properties
├── README.md                                  # User documentation
├── API_DOCUMENTATION.md                       # API reference
├── DEVELOPER.md                               # This file
└── example_website.html                       # API usage example
```

## API Architecture

### HTTP Server Implementation

The API uses Java's built-in `com.sun.net.httpserver.HttpServer`:

```java
HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
server.createContext("/api/leaderboards", handler);
server.setExecutor(Executors.newFixedThreadPool(4));
server.start();
```

### Request Flow

1. **Client Request** → HTTP Server
2. **Server** → Handler Method (`handleListLeaderboards` or `handleGetLeaderboard`)
3. **Handler** → Data Collection (from `LeaderboardRegistry` and player stats)
4. **Handler** → JSON Serialization (using Gson)
5. **Handler** → HTTP Response with CORS headers
6. **Server** → Client Response

### Adding New API Endpoints

To add a new endpoint:

1. Create a handler method in `LeaderboardApiServer.java`:
```java
private void handleMyEndpoint(HttpExchange exchange) throws IOException {
    if (!"GET".equals(exchange.getRequestMethod())) {
        sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
        return;
    }
    
    // Your logic here
    String jsonResponse = GSON.toJson(myData);
    sendResponse(exchange, 200, jsonResponse);
}
```

2. Register the endpoint in the constructor:
```java
server.createContext("/api/my-endpoint", this::handleMyEndpoint);
```

## Adding New Leaderboards

### Simple Stat-Based Leaderboard

Add to `LeaderboardRegistry.register()`:

```java
LEADERBOARDS.put(
    ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "my_stat"),
    new Leaderboard.FromStat(
        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "my_stat"),
        Component.translatable("leaderboard.leaderboards.my_stat"),
        Stats.CUSTOM.get(Stats.MY_STAT),
        false  // false = descending, true = ascending
    )
);
```

### Custom Leaderboard with Logic

```java
LEADERBOARDS.put(
    ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "custom"),
    new Leaderboard(
        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "custom"),
        Component.translatable("leaderboard.leaderboards.custom"),
        player -> {
            // Custom value calculation
            int value = calculateCustomValue(player);
            return Component.literal(String.valueOf(value));
        },
        Comparator.comparingInt(this::calculateCustomValue).reversed(),
        player -> calculateCustomValue(player) > 0
    )
);
```

### Value Formatters

Pre-built formatters in `Leaderboard.FromStat`:

- `DEFAULT` - Simple integer display
- `TIME` - Formats ticks as hours:minutes:seconds
- `DISTANCE` - Formats centimeters as meters/kilometers
- `DAMAGE` - Formats damage values

Custom formatter example:
```java
IntFunction<Component> CUSTOM_FORMAT = value -> {
    if (value <= 0) return Component.literal("0");
    return Component.literal(String.format("%.2f", value / 100.0));
};
```

## Configuration System

### Adding New Config Options

1. Add to `LeaderboardConfig.java`:
```java
public static final ModConfigSpec.BooleanValue MY_OPTION;

static {
    BUILDER.push("My Section");
    
    MY_OPTION = BUILDER
        .comment("Description of my option")
        .define("myOption", true);
    
    BUILDER.pop();
    SPEC = BUILDER.build();
}
```

2. Use in your code:
```java
if (LeaderboardConfig.MY_OPTION.get()) {
    // Do something
}
```

## Testing

### Manual Testing

1. Build the mod
2. Copy to a test server's mods folder
3. Start the server
4. Test in-game commands and GUI
5. Test API endpoints with curl or browser

### API Testing

```bash
# Test leaderboards list
curl http://localhost:8080/api/leaderboards

# Test specific leaderboard
curl http://localhost:8080/api/leaderboard/leaderboards:deaths

# Test with specific port
curl http://localhost:8080/api/leaderboards

# Test CORS headers
curl -H "Origin: http://example.com" -I http://localhost:8080/api/leaderboards
```

### Integration Testing

Test with the example website:
1. Start the server with API enabled
2. Open `example_website.html` in a browser
3. Verify all leaderboards load correctly
4. Check browser console for errors

## Contributing

### Code Style

- Use 4 spaces for indentation
- Follow Java naming conventions
- Add comments for complex logic
- Keep methods focused and small
- Use descriptive variable names

### Pull Request Process

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Test thoroughly
5. Commit with descriptive messages
6. Push to your fork
7. Open a Pull Request

### Commit Message Format

```
type: short description

Longer description if needed

Fixes #issue-number
```

Types: `feat`, `fix`, `docs`, `refactor`, `test`, `chore`

## Common Development Tasks

### Updating Minecraft/NeoForge Version

1. Update versions in `gradle.properties`
2. Update `build.gradle` if needed
3. Run `gradlew clean build`
4. Fix any API changes

### Adding a Dependency

1. Add to `build.gradle` dependencies section:
```gradle
implementation "group:artifact:version"
```

2. Refresh Gradle project
3. Import and use the dependency

### Debugging

#### IntelliJ IDEA
1. Set breakpoints in your code
2. Run in debug mode (Debug button or Shift+F9)
3. Use debug configurations created by Gradle

#### Logging
```java
private static final Logger LOGGER = LogUtils.getLogger();

LOGGER.info("Information message");
LOGGER.warn("Warning message");
LOGGER.error("Error message", exception);
LOGGER.debug("Debug message");
```

## Performance Considerations

### API Server
- Uses thread pool (4 threads by default)
- Consider increasing for high-traffic servers
- Monitor memory usage with many concurrent requests

### Leaderboard Calculations
- Stats are loaded from disk only when needed
- Results are computed on-demand (not cached)
- Consider caching for high-traffic scenarios

### Memory Usage
- Player stats are loaded into memory temporarily
- Garbage collection handles cleanup
- Monitor heap usage on large servers

## Security Best Practices

### API Development
- Never expose sensitive server data
- Validate all inputs
- Use appropriate HTTP status codes
- Implement rate limiting for production
- Consider authentication for sensitive endpoints

### Configuration
- Validate port ranges
- Sanitize origin strings
- Provide secure defaults
- Document security implications

## Troubleshooting Development Issues

### Gradle Issues
```bash
# Clear Gradle cache
gradlew clean

# Refresh dependencies
gradlew --refresh-dependencies
```

### IDE Not Recognizing Classes
1. Reimport Gradle project
2. Invalidate caches and restart (IntelliJ)
3. Refresh dependencies

### Build Fails
- Check Java version (must be 21+)
- Ensure internet connection (for dependencies)
- Check `build.gradle` syntax
- Review error messages carefully

## Resources

- [NeoForge Documentation](https://docs.neoforged.net/)
- [Minecraft Forge Community](https://forums.neoforged.net/)
- [Gradle Documentation](https://docs.gradle.org/)
- [Java HttpServer Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/jdk.httpserver/com/sun/net/httpserver/HttpServer.html)

## Questions or Issues?

- Open an issue on GitHub
- Join the community Discord
- Check existing documentation
- Search for similar issues

---

**Happy coding! 🚀**
