# REST API Documentation

The Leaderboards mod includes a built-in HTTP REST API server for external access to leaderboard data.

## Configuration

Configure the API in `config/leaderboards-server.toml`:

```toml
["API Settings"]
    # Enable/disable the API server
    enableApi = true
    
    # Port for the API server (1024-65535)
    apiPort = 8080
    
    # Enable CORS headers
    corsEnabled = true
    
    # Allowed origins for CORS (* for all, or comma-separated domains)
    allowedOrigins = "*"
```

### Configuration Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `enableApi` | boolean | `true` | Enable or disable the entire API server |
| `apiPort` | integer | `8080` | Port for the API server (1024-65535) |
| `corsEnabled` | boolean | `true` | Enable CORS headers for browser access |
| `allowedOrigins` | string | `"*"` | Allowed origins for CORS (use `*` for all) |

## Endpoints

### 1. List All Leaderboards

**GET** `/api/leaderboards`

Returns a list of all available leaderboards.

**Response Example:**
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
    },
    {
      "id": "leaderboards:time_played",
      "name": "Time Played"
    }
  ]
}
```

### 2. Get Specific Leaderboard

**GET** `/api/leaderboard/{leaderboard_id}`

Returns the full leaderboard with player rankings.

**Parameters:**
- `leaderboard_id` - The leaderboard ID (e.g., `leaderboards:deaths`)

**Response Example:**
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
    },
    {
      "rank": 2,
      "playerName": "Player2",
      "playerUuid": "87654321-4321-4321-4321-cba987654321",
      "value": "35"
    }
  ]
}
```

### 3. Get Player Statistics

**GET** `/api/player/{player_name}`

Returns all leaderboard statistics for a specific player, including their rank in each leaderboard.

**Parameters:**
- `player_name` - The player's username (case-insensitive)

**Response Example:**
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
    },
    {
      "leaderboardId": "leaderboards:mob_kills",
      "leaderboardName": "Mob Kills",
      "rank": 3,
      "totalPlayers": 48,
      "value": "1234"
    }
  ]
}
```

**Notes:**
- Only leaderboards where the player has valid data are included
- `online` indicates if the player is currently on the server
- `rank` shows the player's position (1 = first place)
- `totalPlayers` shows how many players are ranked in that leaderboard

## CORS Support

The API includes CORS headers when enabled in config:
- `Access-Control-Allow-Origin: *` (or configured domains)
- `Access-Control-Allow-Methods: GET, OPTIONS`
- `Access-Control-Allow-Headers: Content-Type`

## Usage Examples

### JavaScript/Fetch

```javascript
// Get all leaderboards
fetch('http://localhost:8080/api/leaderboards')
  .then(response => response.json())
  .then(data => {
    console.log('Available leaderboards:', data.leaderboards);
  });

// Get specific leaderboard
fetch('http://localhost:8080/api/leaderboard/leaderboards:deaths')
  .then(response => response.json())
  .then(data => {
    data.entries.forEach(entry => {
      console.log(`${entry.rank}. ${entry.playerName}: ${entry.value}`);
    });
  });

// Get player statistics
fetch('http://localhost:8080/api/player/Steve')
  .then(response => response.json())
  .then(data => {
    console.log(`Player: ${data.playerName} (${data.online ? 'Online' : 'Offline'})`);
    data.leaderboards.forEach(lb => {
      console.log(`${lb.leaderboardName}: Rank ${lb.rank}/${lb.totalPlayers} - ${lb.value}`);
    });
  });
```

### Python

```python
import requests

# Get all leaderboards
response = requests.get('http://localhost:8080/api/leaderboards')
leaderboards = response.json()
print(leaderboards)

# Get specific leaderboard
response = requests.get('http://localhost:8080/api/leaderboard/leaderboards:deaths')
leaderboard = response.json()
for entry in leaderboard['entries']:
    print(f"{entry['rank']}. {entry['playerName']}: {entry['value']}")

# Get player statistics
response = requests.get('http://localhost:8080/api/player/Steve')
player = response.json()
print(f"Player: {player['playerName']} ({'Online' if player['online'] else 'Offline'})")
for lb in player['leaderboards']:
    print(f"{lb['leaderboardName']}: Rank {lb['rank']}/{lb['totalPlayers']} - {lb['value']}")
```

### cURL

```bash
# Get all leaderboards
curl http://localhost:8080/api/leaderboards

# Get specific leaderboard
curl http://localhost:8080/api/leaderboard/leaderboards:deaths

# Get player statistics
curl http://localhost:8080/api/player/Steve
```

## Error Responses

The API returns appropriate HTTP status codes:

- `200 OK` - Success
- `400 Bad Request` - Invalid parameters or format
- `404 Not Found` - Resource not found (player or leaderboard)
- `405 Method Not Allowed` - Invalid HTTP method (only GET is supported)
- `500 Internal Server Error` - Server error

**Error Response Format:**
```json
{
  "error": "Error message description"
}
```

## Security Considerations

⚠️ **Important Security Notes:**

1. **No Authentication** - The API has no built-in authentication
2. **Network Exposure** - Only expose on trusted networks
3. **Production Usage** - Implement additional security:
   - Firewall rules to restrict access
   - Reverse proxy with authentication
   - HTTPS/TLS encryption
   - Rate limiting

### Production Configuration Example

```toml
["API Settings"]
    enableApi = true
    apiPort = 8080
    corsEnabled = true
    # Restrict to specific domains
    allowedOrigins = "https://yoursite.com,https://stats.yoursite.com"
```

### Firewall Configuration

If exposing the API externally:
1. Forward port 8080 on your router to your server
2. Configure firewall to allow incoming connections on port 8080
3. Consider using a reverse proxy (nginx, Apache) with SSL/TLS

For local network access only, no port forwarding is needed.

## Integration Example

See `example_website.html` in the root directory for a complete web integration example.
