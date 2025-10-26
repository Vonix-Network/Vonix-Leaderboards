# Leaderboards

A comprehensive Minecraft mod for tracking and displaying player statistics through in-game leaderboards and a web API.

## Features

### 🎮 In-Game Leaderboards
- View player rankings for various statistics directly in-game
- Multiple leaderboard categories including:
  - Deaths
  - Mob Kills
  - Player Kills
  - Time Played
  - Jumps
  - Distance Walked/Sprinted
  - Deaths Per Hour
  - Last Seen
  - FTB Quest Completions (if FTB Quests is installed)

### 🌐 Web API
- RESTful HTTP API for external access to leaderboard data
- JSON responses for easy integration with websites and applications
- CORS support for browser-based applications
- Configurable port and security settings

### ⚙️ Fully Configurable
- Enable/disable API server
- Configure API port
- Control CORS settings
- Specify allowed origins for cross-origin requests

## Installation

1. Download the mod JAR file from the releases page
2. Place the JAR in your Minecraft server's `mods` folder
3. Start the server to generate the configuration file
4. Configure the mod (optional) in `config/leaderboards-server.toml`
5. Restart the server if you changed any config settings

## Requirements

- Minecraft 1.21.1
- NeoForge
- Java 21+

### Optional Dependencies
- **FTB Quests** - Adds quest completion leaderboards
- **FTB Library** - Required for FTB Quests integration
- **FTB Teams** - Required for FTB Quests integration
- **Architectury API** - Required for FTB Quests integration

## Configuration

The configuration file is located at `config/leaderboards-server.toml` and is generated on first run.

### API Settings

```toml
["API Settings"]
    # Enable the HTTP API server for external access to leaderboard data
    enableApi = true
    
    # Port for the API server to listen on
    # Range: 1024 to 65535
    apiPort = 8080
    
    # Enable CORS (Cross-Origin Resource Sharing) headers
    corsEnabled = true
    
    # Allowed origins for CORS. Use '*' for all origins or specify domains separated by commas
    allowedOrigins = "*"
```

### Configuration Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `enableApi` | boolean | `true` | Enable/disable the API server |
| `apiPort` | integer | `8080` | Port number (1024-65535) |
| `corsEnabled` | boolean | `true` | Enable CORS headers |
| `allowedOrigins` | string | `"*"` | Allowed origins (e.g., `"https://example.com,https://other.com"`) |

## Using the API

### Quick Start

Once the server is running with the API enabled, you can access leaderboard data:

```bash
# List all available leaderboards
curl http://localhost:8080/api/leaderboards

# Get a specific leaderboard
curl http://localhost:8080/api/leaderboard/leaderboards:deaths

# Get player statistics
curl http://localhost:8080/api/player/Steve
```

### API Endpoints

#### GET `/api/leaderboards`
Returns a list of all available leaderboards.

**Example Response:**
```json
{
  "leaderboards": [
    {
      "id": "leaderboards:deaths",
      "name": "Deaths"
    },
    {
      "id": "leaderboards:mob_kills",
      "name": "Mob Kills"
    }
  ]
}
```

#### GET `/api/leaderboard/{id}`
Returns full leaderboard data with player rankings.

**Example Response:**
```json
{
  "id": "leaderboards:deaths",
  "name": "Deaths",
  "entries": [
    {
      "rank": 1,
      "playerName": "Player1",
      "playerUuid": "12345678-1234-1234-1234-123456789abc",
      "value": "42"
    }
  ]
}
```

#### GET `/api/player/{player_name}`
Returns all leaderboard statistics for a specific player, including their rank in each leaderboard.

**Example Response:**
```json
{
  "playerName": "Player1",
  "playerUuid": "12345678-1234-1234-1234-123456789abc",
  "online": true,
  "leaderboards": [
    {
      "leaderboardId": "leaderboards:deaths",
      "leaderboardName": "Deaths",
      "rank": 1,
      "totalPlayers": 50,
      "value": "42"
    }
  ]
}
```

### Available Leaderboard IDs

- `leaderboards:deaths`
- `leaderboards:mob_kills`
- `leaderboards:player_kills`
- `leaderboards:time_played`
- `leaderboards:jumps`
- `leaderboards:distance_walked`
- `leaderboards:distance_sprinted`
- `leaderboards:deaths_per_hour`
- `leaderboards:last_seen`
- `leaderboards:quest_completions` (requires FTB Quests)
- `leaderboards:quest_completion_percentage` (requires FTB Quests)

For detailed API documentation, see [API_DOCUMENTATION.md](API_DOCUMENTATION.md).

## Example Website

An example HTML/JavaScript website is included in `example_website.html`. This demonstrates how to:
- Connect to the API
- List available leaderboards
- Display leaderboard data in a beautiful, responsive interface

To use it:
1. Ensure the Minecraft server is running with the API enabled
2. Open `example_website.html` in a web browser
3. Update the API URL if your server is not on localhost:8080

## Security Considerations

⚠️ **Important Security Notes:**

- The API has **no authentication** by default
- Only expose it on trusted networks
- Use firewall rules to restrict access
- Consider using a reverse proxy (nginx, Apache) with authentication if exposing to the internet
- Set `allowedOrigins` to specific domains instead of `"*"` for production use

### Production Deployment Recommendations

1. **Use a reverse proxy** (nginx/Apache) with HTTPS and authentication
2. **Restrict origins:** Set `allowedOrigins` to your domain(s)
3. **Firewall:** Only allow access from specific IPs if possible
4. **VPN:** Consider requiring VPN access for the API
5. **Monitor:** Watch your logs for suspicious activity

## Building from Source

See [DEVELOPER.md](DEVELOPER.md) for detailed build instructions.

Quick build:
```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`.

## Troubleshooting

### API Server Not Starting
- Check the server logs for error messages
- Verify the port is not already in use
- Ensure `enableApi` is set to `true` in the config
- Check firewall settings if accessing remotely

### CORS Errors in Browser
- Ensure `corsEnabled` is `true` in the config
- Check `allowedOrigins` includes your domain
- Try setting `allowedOrigins = "*"` for testing (not recommended for production)

### Empty Leaderboard Data
- Ensure players have generated statistics (play on the server)
- Check that stat files exist in `world/stats/`
- Verify the leaderboard ID is correct

## Support & Contributing

- **Issues:** Report bugs on the GitHub issues page
- **Pull Requests:** Contributions are welcome!
- **Documentation:** Help improve docs by submitting PRs

## License

This project is licensed under the terms specified in the license file.

## Credits

- **Contributors** - See the contributors page on GitHub

## Changelog

### Version 1.0.0
- Initial release
- In-game leaderboard display
- HTTP API server
- Configuration support
- CORS support
- FTB Quests integration
