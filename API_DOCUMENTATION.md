# Leaderboard API Documentation

The Leaderboard mod now includes a built-in HTTP API server that allows external websites and applications to query leaderboard data.

## Configuration

The API server starts automatically when the Minecraft server starts on **port 8080**.

To change the port, modify the port number in `Leaderboards.java`:
```java
LeaderboardApiServer.start(event.getServer(), 8080); // Change 8080 to your desired port
```

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
      "id": "leaderboards:player_kills",
      "name": "Player Kills"
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

The API includes CORS headers, allowing web browsers to make requests from any origin:
- `Access-Control-Allow-Origin: *`
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
    console.log('Deaths leaderboard:', data);
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

## Available Leaderboard IDs

- `leaderboards:deaths` - Player deaths
- `leaderboards:mob_kills` - Mob kills
- `leaderboards:player_kills` - Player kills
- `leaderboards:time_played` - Time played (formatted)
- `leaderboards:jumps` - Jump count
- `leaderboards:distance_walked` - Distance walked (formatted)
- `leaderboards:distance_sprinted` - Distance sprinted (formatted)
- `leaderboards:deaths_per_hour` - Deaths per hour ratio
- `leaderboards:last_seen` - Last seen time
- `leaderboards:quest_completions` - Quest completions (if FTB Quests is installed)
- `leaderboards:quest_completion_percentage` - Quest completion percentage (if FTB Quests is installed)

## Error Responses

The API returns appropriate HTTP status codes:

- `200 OK` - Success
- `400 Bad Request` - Invalid leaderboard ID format
- `404 Not Found` - Leaderboard not found
- `405 Method Not Allowed` - Invalid HTTP method (only GET is supported)

**Error Response Format:**
```json
{
  "error": "Error message description"
}
```

## Security Considerations

⚠️ **Important:** This API server has no authentication. Only run it on trusted networks or implement additional security measures (firewall rules, reverse proxy with auth, etc.) if exposing it to the internet.

## Firewall Configuration

If you want to access the API from outside your local network, you need to:
1. Forward port 8080 on your router to your server
2. Configure your firewall to allow incoming connections on port 8080

For local network access only, no port forwarding is needed.
