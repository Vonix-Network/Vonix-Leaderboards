# Frequently Asked Questions (FAQ)

Common questions and answers about the Leaderboards mod.

## General Questions

### Q: What versions of Minecraft does this support?

**A**: Currently supports Minecraft 1.21.1 with NeoForge. Check the releases page for compatibility information.

### Q: Does this work on client/single-player?

**A**: The mod is designed for servers. Some features may work in single-player, but it's optimized for multiplayer environments.

### Q: How many leaderboards are there?

**A**: 70+ leaderboards:
- 9 core statistics
- 60+ vanilla Minecraft statistics
- 2 FTB Quests statistics (if FTB Quests is installed)

See the [Complete Leaderboards List](LEADERBOARDS_LIST.md) for details.

### Q: Does this affect server performance?

**A**: Minimal impact. The mod:
- Loads stats on-demand
- Uses efficient caching
- Runs API requests in a thread pool
- Has low memory overhead

## Installation & Setup

### Q: What mods are required?

**A**: Required mods:
- NeoForge
- FTB Library
- Architectury API

Optional:
- FTB Quests (for quest-related leaderboards)

### Q: Where is the config file?

**A**: `config/leaderboards-server.toml` (auto-generated on first server start)

### Q: Do I need to configure anything?

**A**: No, defaults work great! The API starts automatically on port 8080 with CORS enabled. Configure only if you need to change port, disable API, or restrict CORS.

### Q: How do I update the mod?

**A**:
1. Stop the server
2. Replace the JAR file in the `mods` folder
3. Start the server
4. Configuration migrates automatically

## API Questions

### Q: Is the API free to use?

**A**: Yes! The API is included with the mod at no additional cost.

### Q: Do I need an API key?

**A**: No, the API has no authentication by default. For security, consider:
- Restricting by origin (CORS)
- Using a reverse proxy with auth
- Enabling firewall rules
- Running on a non-standard port

### Q: What format does the API return?

**A**: JSON format. All responses are standard JSON that any programming language can parse.

### Q: Can I use the API from my website?

**A**: Yes! Enable CORS in the config:
```toml
corsEnabled = true
allowedOrigins = "https://yoursite.com"
```

See [API Documentation](API.md) for examples.

### Q: Why am I getting CORS errors?

**A**: Browser security blocks cross-origin requests. Fix by:
1. Ensuring `corsEnabled = true` in config
2. Adding your domain to `allowedOrigins`
3. Restarting the server

### Q: Can I access the API from outside my network?

**A**: Yes, but you need to:
1. Forward port 8080 on your router
2. Configure firewall to allow connections
3. Access via your public IP address

⚠️ **Security Warning**: Exposing the API to the internet without authentication is a security risk. Use a reverse proxy with authentication for production.

### Q: What's the API rate limit?

**A**: No built-in rate limiting. For production, implement rate limiting at the reverse proxy level (nginx, Apache).

### Q: Can the API modify player stats?

**A**: No, the API is read-only. It only supports GET requests to view statistics.

## Leaderboards & Statistics

### Q: Why is a leaderboard empty?

**A**: Leaderboards only show players who:
- Have played on the server
- Have a non-zero value for that stat
- Meet the leaderboard's validity criteria

Players must perform actions to generate statistics.

### Q: How often do leaderboards update?

**A**: Real-time! Statistics update as players perform actions. Leaderboards refresh when queried (no caching).

### Q: Can I see historical data?

**A**: Not currently. The mod shows current statistics only. Historical tracking may be added in future versions.

### Q: Why don't I see quest leaderboards?

**A**: Quest leaderboards require FTB Quests to be installed. If it's not installed, those leaderboards won't appear.

### Q: How is "Deaths Per Hour" calculated?

**A**: Formula: `deaths / (play_time / 72000)`  
Requires at least 1 hour of playtime to appear on the leaderboard.

### Q: What does "Last Seen" show?

**A**: Time since the player was last online, or "Online" if currently playing.

### Q: Can I customize which leaderboards are shown?

**A**: Not through config currently. You would need to modify the source code to add/remove leaderboards. See [Development Guide](DEVELOPMENT.md).

## Technical Questions

### Q: Where is player data stored?

**A**: Uses Minecraft's native statistics system at `world/stats/{uuid}.json`. No separate database needed.

### Q: Does it work with existing stats?

**A**: Yes! The mod reads existing Minecraft statistics, so historical data is immediately available.

### Q: Can I backup player statistics?

**A**: Yes, backup the `world/stats/` directory. This contains all player statistics.

### Q: What happens if the API port is already in use?

**A**: The API fails to start and logs an error. Change `apiPort` in the config to use a different port.

### Q: Can I run multiple instances on the same machine?

**A**: Yes, but each instance needs a different API port to avoid conflicts.

### Q: Does it support MySQL/PostgreSQL?

**A**: No, it uses Minecraft's file-based statistics system. No external database required.

## Configuration Questions

### Q: How do I disable the API?

**A**: Set `enableApi = false` in `config/leaderboards-server.toml` and restart.

### Q: How do I change the API port?

**A**: Edit `apiPort` in the config:
```toml
apiPort = 9090
```
Then restart the server.

### Q: How do I allow only specific websites?

**A**: Set specific origins in config:
```toml
allowedOrigins = "https://site1.com,https://site2.com"
```

### Q: What's the difference between `*` and specific origins?

**A**:
- `*` = Allow ALL websites (development/testing only)
- Specific URLs = Only those websites can access (production)

### Q: Do config changes take effect immediately?

**A**: No, you must restart the server for config changes to apply.

## Integration Questions

### Q: Can I use this with Discord bots?

**A**: Yes! The API works with any HTTP client. Example:
```python
import requests
data = requests.get('http://localhost:8080/api/leaderboards').json()
```

### Q: Can I embed leaderboards in my website?

**A**: Yes! See [example_website.html](../example_website.html) for a complete working example.

### Q: Does it work with mobile apps?

**A**: Yes, any app that can make HTTP requests can use the API.

### Q: Can I use this with OBS for streaming?

**A**: Yes! Use a browser source pointing to a webpage that fetches from the API.

### Q: Can I export data to Excel?

**A**: Yes! Fetch the JSON data and convert it. Many programming languages have CSV/Excel export libraries.

## Troubleshooting

### Q: API returns 404 error

**A**: The leaderboard ID is incorrect. List all IDs:
```bash
curl http://localhost:8080/api/leaderboards
```

### Q: API returns 500 error

**A**: Server error. Check server logs for details. Usually indicates:
- Missing player data files
- Corrupted statistics
- Internal server issue

### Q: "Player not found" error

**A**: The player hasn't joined the server yet. They must play to generate statistics.

### Q: Leaderboards not showing in-game

**A**: Ensure:
1. Command `/leaderboards` works
2. Mod is loaded (check server logs)
3. You have permission to use the command

### Q: Server won't start after installing

**A**: Check for:
- Correct Minecraft version
- Required dependencies installed
- No conflicting mods
- Check server logs for error messages

## Security Questions

### Q: Is the API secure?

**A**: The API is read-only with no authentication. For production:
- Use CORS restrictions
- Implement firewall rules
- Consider a reverse proxy with auth
- Use HTTPS/TLS
- Don't expose directly to the internet

### Q: Can players cheat/modify stats through the API?

**A**: No, the API is read-only. It cannot modify any statistics.

### Q: Should I use authentication?

**A**: For public internet access, yes. Use a reverse proxy (nginx, Apache) with authentication.

### Q: Is HTTPS/SSL required?

**A**: Not required, but strongly recommended for production, especially if exposing to the internet.

### Q: What data is exposed by the API?

**A**: Only game statistics:
- Player usernames
- Player UUIDs
- Game statistics (deaths, kills, distance, etc.)
- Online/offline status

No passwords, IPs, or sensitive data are exposed.

## Development Questions

### Q: Can I add custom leaderboards?

**A**: Yes! See the [Development Guide](DEVELOPMENT.md#adding-new-leaderboards) for instructions.

### Q: Can I add new API endpoints?

**A**: Yes! See the [Development Guide](DEVELOPMENT.md#adding-new-api-endpoints).

### Q: Is the source code available?

**A**: Check the GitHub repository for source code and contribution guidelines.

### Q: Can I contribute?

**A**: Yes! See [DEVELOPMENT.md](DEVELOPMENT.md#contributing) for contribution guidelines.

### Q: What language is it written in?

**A**: Java, using the NeoForge modding framework.

## Feature Requests

### Q: Can you add [specific feature]?

**A**: Open a GitHub issue with your feature request! We review all suggestions.

### Q: Will there be more leaderboards?

**A**: Possibly! Submit suggestions or contribute via pull request.

### Q: Can you add support for [other mod]?

**A**: Maybe! Open an issue to discuss integration possibilities.

### Q: Will there be a Fabric version?

**A**: Not currently planned, but contributions are welcome.

## Compatibility Questions

### Q: Does it work with Bukkit/Spigot/Paper?

**A**: No, this is a NeoForge mod. It requires NeoForge to run.

### Q: Can I use this with other stat mods?

**A**: Generally yes, but test for conflicts. Most mods are compatible.

### Q: Does it work with BungeeCord/Velocity?

**A**: Yes, install on each backend server. Each server tracks its own statistics.

### Q: Is it compatible with world management plugins?

**A**: Yes, uses Minecraft's native statistics which work across worlds.

## Support & Help

### Q: Where can I get help?

**A**: In order:
1. Check this FAQ
2. Review relevant documentation
3. Search existing GitHub issues
4. Create a new GitHub issue

### Q: How do I report a bug?

**A**: Open a GitHub issue with:
- Minecraft version
- Mod version
- Steps to reproduce
- Error messages/logs
- Expected vs actual behavior

### Q: How do I request a feature?

**A**: Open a GitHub issue tagged as "feature request" with a detailed description of the feature and use case.

### Q: Is there a Discord server?

**A**: Check the GitHub repository README for community links.

## Still Have Questions?

If your question isn't answered here:

1. Check the [Documentation Index](README.md)
2. Review specific documentation:
   - [Features](FEATURES.md)
   - [Configuration](CONFIGURATION.md)
   - [API Reference](API.md)
   - [Development Guide](DEVELOPMENT.md)
3. Search [existing GitHub issues](https://github.com/your-repo/issues)
4. Create a [new issue](https://github.com/your-repo/issues/new) if needed

---

**Last Updated**: 2025-10-26  
**Mod Version**: 1.0.0
