# Quick Start Guide

Get the Leaderboards mod up and running in 5 minutes.

## 📥 Installation

### Step 1: Download

Download the latest JAR file from the releases page.

### Step 2: Install

Place the JAR file in your server's `mods` folder:
```
your-server/
└── mods/
    └── leaderboards-1.21.1-NeoForge-1.0.0.jar
```

### Step 3: Start Server

Start your Minecraft server. The mod will automatically:
- Register all leaderboards
- Generate the configuration file
- Start the API server (if enabled)

Look for this message in the console:
```
[Leaderboards] Leaderboards registered
[Leaderboards] Leaderboard API Server started successfully on port 8080 (CORS: enabled)
```

### Step 4: You're Done!

The mod is now active and ready to use.

## 🎮 Using In-Game

### Open Leaderboards

Use the `/leaderboards` command or press the configured keybind.

### Browse Leaderboards

1. **Leaderboard Selection Screen** opens
2. Browse through available leaderboards
3. Click on any leaderboard to view
4. See player rankings and your position

### View Vanilla Stats

Dedicated screen for browsing all 60+ vanilla Minecraft statistics.

## 🌐 Using the API

### Quick Test

Open your browser and visit:
```
http://localhost:8080/api/leaderboards
```

You should see JSON data with all available leaderboards.

### API Basics

Three simple endpoints:

1. **List leaderboards**:
   ```bash
   curl http://localhost:8080/api/leaderboards
   ```

2. **Get specific leaderboard**:
   ```bash
   curl http://localhost:8080/api/leaderboard/leaderboards:deaths
   ```

3. **Get player stats**:
   ```bash
   curl http://localhost:8080/api/player/Steve
   ```

### Example: Simple HTML Page

Create `index.html`:
```html
<!DOCTYPE html>
<html>
<head>
    <title>Server Leaderboards</title>
</head>
<body>
    <h1>Top Deaths</h1>
    <div id="leaderboard"></div>
    
    <script>
        fetch('http://localhost:8080/api/leaderboard/leaderboards:deaths')
            .then(r => r.json())
            .then(data => {
                let html = '<ol>';
                data.entries.forEach(entry => {
                    html += `<li>${entry.playerName}: ${entry.value}</li>`;
                });
                html += '</ol>';
                document.getElementById('leaderboard').innerHTML = html;
            });
    </script>
</body>
</html>
```

Open in your browser - done!

## ⚙️ Basic Configuration

Configuration file: `config/leaderboards-server.toml`

### Disable API

```toml
["API Settings"]
    enableApi = false
```

### Change Port

```toml
["API Settings"]
    enableApi = true
    apiPort = 9090
```

### Restrict CORS

```toml
["API Settings"]
    enableApi = true
    corsEnabled = true
    allowedOrigins = "https://yoursite.com"
```

**Remember**: Restart server after changing configuration.

## 🔍 Verify Everything Works

### Check API Status

```bash
# Test leaderboards endpoint
curl http://localhost:8080/api/leaderboards

# Should return JSON like:
# {"leaderboards":[{"id":"leaderboards:deaths","name":"Deaths"},...]}
```

### Check In-Game

1. Join the server
2. Run `/leaderboards`
3. GUI should open with list of leaderboards

### Check Logs

Look for these messages in server console:
```
[Leaderboards] Leaderboards registered
[Leaderboards] Leaderboard API Server started successfully on port 8080
```

## 🚀 Next Steps

### For Server Owners

1. **Customize configuration** - See [Configuration Guide](CONFIGURATION.md)
2. **Review security** - See [API Security](API.md#security-considerations)
3. **Set up firewall** - If exposing API externally

### For Web Developers

1. **Explore all endpoints** - See [API Documentation](API.md)
2. **Check examples** - See [example_website.html](../example_website.html)
3. **Build integration** - Use the JSON API

### For Players

1. **Check rankings** - Use `/leaderboards` command
2. **Track your stats** - See where you rank
3. **Compete** - Try to reach the top!

## 🎯 Common First-Time Tasks

### Task 1: View Top Players

```bash
curl http://localhost:8080/api/leaderboard/leaderboards:time_played
```

### Task 2: Check Your Rank

Replace `YourName` with your username:
```bash
curl http://localhost:8080/api/player/YourName
```

### Task 3: List All Available Stats

```bash
curl http://localhost:8080/api/leaderboards | json_pp
```

(Use `json_pp` or `jq` for pretty formatting)

### Task 4: Create Simple Discord Bot

```python
import discord
import requests

client = discord.Client()

@client.event
async def on_message(message):
    if message.content.startswith('!deaths'):
        r = requests.get('http://localhost:8080/api/leaderboard/leaderboards:deaths')
        data = r.json()
        
        response = f"**{data['name']} Leaderboard**\n"
        for entry in data['entries'][:5]:  # Top 5
            response += f"{entry['rank']}. {entry['playerName']}: {entry['value']}\n"
        
        await message.channel.send(response)

client.run('YOUR_BOT_TOKEN')
```

### Task 5: Add to Website

See the included [example_website.html](../example_website.html) for a complete working example.

## ❓ Troubleshooting

### Problem: API returns 404

**Solution**: Check the leaderboard ID is correct. List all IDs:
```bash
curl http://localhost:8080/api/leaderboards
```

### Problem: Can't connect to API

**Solutions**:
1. Verify server is running
2. Check `enableApi = true` in config
3. Verify port number matches config
4. Check firewall settings

### Problem: Empty leaderboards

**Reason**: Players need to play and generate statistics first. Stats are tracked as players perform actions.

### Problem: CORS errors in browser

**Solution**: Ensure `corsEnabled = true` in config and restart server.

## 📚 Learn More

- **[Complete Features List](FEATURES.md)** - All 70+ leaderboards
- **[Configuration Guide](CONFIGURATION.md)** - Detailed configuration options
- **[API Reference](API.md)** - Complete API documentation
- **[Leaderboards List](LEADERBOARDS_LIST.md)** - Every available statistic

## 💡 Pro Tips

1. **Use case-insensitive player names** - The API handles it automatically
2. **Check player online status** - The `/api/player/{name}` endpoint shows if they're online
3. **Format values** - Time, distance, and damage are pre-formatted for display
4. **Filter inactive players** - Only players with valid stats appear in leaderboards
5. **No authentication needed** - Simple to integrate, but consider security for production

## 🎉 You're All Set!

The Leaderboards mod is now fully operational. Enjoy tracking player statistics and building awesome integrations!

**Need help?** Check the [FAQ](FAQ.md) or review the full documentation.
