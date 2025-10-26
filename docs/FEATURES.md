# Features

Comprehensive list of all features provided by the Leaderboards mod.

## 🏆 Leaderboard Types

### Core Statistics (9 Leaderboards)
- **Deaths** - Total player deaths
- **Mob Kills** - Total mobs killed
- **Player Kills** - Total players killed in PvP
- **Time Played** - Total time spent on the server (formatted as hours:minutes:seconds)
- **Jumps** - Total number of jumps
- **Distance Walked** - Total distance walked (in meters/kilometers)
- **Distance Sprinted** - Total distance sprinted (in meters/kilometers)
- **Deaths Per Hour** - Calculated ratio of deaths to hours played
- **Last Seen** - Time since player was last online

### Vanilla Statistics (60+ Leaderboards)

#### Mining & Crafting
- **Total Blocks Mined** - Combined count of all blocks mined
- **Total Items Crafted** - Combined count of all items crafted
- **Total Items Used** - Combined count of all items used
- **Total Items Broken** - Combined count of all items broken (tools/armor)
- **Total Items Picked Up** - Combined count of all items picked up

#### Combat & Damage
- **Damage Dealt** - Total damage dealt to entities
- **Damage Taken** - Total damage received
- **Damage Blocked By Shield** - Damage prevented by shields
- **Damage Absorbed** - Damage absorbed by armor
- **Damage Resisted** - Damage resisted by protection
- **Damage Dealt Absorbed** - Damage you dealt that was absorbed
- **Damage Dealt Resisted** - Damage you dealt that was resisted

#### Movement & Travel
- **Fly Distance** - Distance traveled while flying
- **Swim Distance** - Distance swam in water
- **Horse Distance** - Distance traveled on horse
- **Boat Distance** - Distance traveled in boat
- **Elytra Distance** - Distance flown with elytra
- **Minecart Distance** - Distance traveled in minecart
- **Pig Distance** - Distance traveled on pig
- **Strider Distance** - Distance traveled on strider
- **Walk Under Water Distance** - Distance walked underwater
- **Walk On Water Distance** - Distance walked on water (frost walker)
- **Climb Distance** - Distance climbed (ladders/vines)
- **Fall Distance** - Distance fallen
- **Crouch Distance** - Distance crouched

#### Activities
- **Animals Bred** - Total animals bred
- **Fish Caught** - Total fish caught
- **Crouch Time** - Total time spent crouching
- **Time Since Death** - Time since last death
- **Time Since Rest** - Time since last sleep
- **Villager Trades** - Number of villager trades
- **Talked To Villager** - Times talked to villagers
- **Raids Won** - Number of raids won
- **Raids Triggered** - Number of raids triggered
- **Target Hit** - Times target block was hit
- **Bells Rung** - Times bells were rung
- **Items Dropped** - Total items dropped
- **Enchantments Done** - Total enchantments performed
- **Times Slept** - Number of times slept in bed
- **Total World Time** - Total time in world (including offline)
- **Cake Slices Eaten** - Cake slices consumed

#### Container Interactions
- **Chests Opened** - Times chests were opened
- **Ender Chests Opened** - Times ender chests were opened
- **Shulker Boxes Opened** - Times shulker boxes were opened
- **Barrels Opened** - Times barrels were opened

#### Workstation Interactions
- **Furnace Interactions** - Times furnaces were used
- **Crafting Table Interactions** - Times crafting tables were used
- **Blast Furnace Interactions** - Times blast furnaces were used
- **Smoker Interactions** - Times smokers were used
- **Anvil Interactions** - Times anvils were used
- **Grindstone Interactions** - Times grindstones were used
- **Smithing Table Interactions** - Times smithing tables were used
- **Beacon Interactions** - Times beacons were used
- **Brewing Stand Interactions** - Times brewing stands were used
- **Lectern Interactions** - Times lecterns were used

### FTB Quests Integration (Optional - 2 Leaderboards)
- **Quest Completions** - Total quests completed
- **Quest Completion Percentage** - Percentage of all quests completed

## 🎮 In-Game Features

### GUI/Interface
- **Leaderboard Selection Screen** - Browse and select from all available leaderboards
- **Detailed Leaderboard View** - View top players with rankings and values
- **Vanilla Stats Browser** - Dedicated screen for browsing vanilla statistics
- **Real-time Updates** - Leaderboards update as players progress
- **Formatted Values** - Automatic formatting for time, distance, and damage values

### Commands
- `/leaderboards` - Open the leaderboard selection GUI
- Keybind support for quick access

### Data Tracking
- **Persistent Storage** - Player data saved across server restarts
- **Last Seen Tracking** - Track when players were last online
- **Online Status** - Real-time tracking of online players

## 🌐 REST API Features

### Endpoints
- **List All Leaderboards** - Get complete list of available leaderboards
- **Get Leaderboard** - Retrieve full leaderboard with player rankings
- **Get Player Stats** - Retrieve all statistics for a specific player

### API Capabilities
- **JSON Responses** - Standard JSON format for easy parsing
- **CORS Support** - Configurable cross-origin resource sharing
- **Player Search** - Case-insensitive player name lookup
- **Rank Information** - Player rank and total player count
- **Online Status** - Check if player is currently online
- **Formatted Values** - Pre-formatted values (time, distance, etc.)
- **Error Handling** - Proper HTTP status codes and error messages

### API Configuration
- **Enable/Disable** - Turn API on or off via config
- **Custom Port** - Configure API port (default: 8080)
- **CORS Control** - Enable/disable CORS headers
- **Origin Restriction** - Specify allowed domains for CORS

## ⚙️ Configuration Options

### Server Configuration (`config/leaderboards-server.toml`)

```toml
["API Settings"]
    # Enable or disable the HTTP API server
    enableApi = true
    
    # Port for the API server to listen on (range: 1024-65535)
    apiPort = 8080
    
    # Enable CORS headers for browser access
    corsEnabled = true
    
    # Allowed origins for CORS requests (* for all, or comma-separated list)
    allowedOrigins = "*"
```

### Configuration Features
- **Server-side only** - Single configuration file
- **Auto-generation** - Config file created on first server start
- **Hot-reload** - Changes take effect on server restart
- **Validation** - Port range validation (1024-65535)
- **Comments** - Well-documented config options

## 🔌 Integration Features

### Mod Compatibility
- **FTB Quests** - Optional integration for quest tracking
- **Architectury API** - Cross-platform compatibility layer
- **FTB Library** - UI library integration

### External Integration
- **Web Dashboards** - Display leaderboards on websites
- **Discord Bots** - Query player stats via API
- **Mobile Apps** - Access leaderboards from mobile devices
- **Third-party Tools** - Any HTTP client can access the API
- **Example Website** - Included HTML example (`example_website.html`)

## 🛡️ Security Features

### API Security
- **Configurable Access** - Enable/disable API entirely
- **CORS Control** - Restrict access by domain
- **Port Configuration** - Run on non-standard ports
- **No Data Modification** - Read-only API (GET requests only)
- **Error Sanitization** - Safe error messages without exposing internals

### Data Privacy
- **UUID Tracking** - Player identification by UUID
- **No Sensitive Data** - Only game statistics exposed
- **Server-side Processing** - All calculations done server-side

## 📊 Data Presentation

### Value Formatting
- **Time Values** - Formatted as `HH:MM:SS`
- **Distance Values** - Formatted as meters/kilometers
- **Damage Values** - Formatted with decimal points
- **Large Numbers** - Readable number formatting
- **Percentage Values** - Formatted with % symbol

### Sorting & Ranking
- **Automatic Sorting** - Players sorted by stat value
- **Rank Calculation** - Positions calculated dynamically
- **Ascending/Descending** - Appropriate sort order per stat
- **Tie Handling** - Consistent rank assignment for equal values

## 🔄 Performance Features

### Optimization
- **On-demand Loading** - Stats loaded only when needed
- **Efficient Caching** - Game profile cache utilization
- **Thread Pool** - API requests handled by thread pool (4 threads)
- **Memory Management** - Automatic garbage collection
- **Minimal Overhead** - Low impact on server performance

### Scalability
- **Large Player Count** - Handles hundreds of players
- **Multiple Leaderboards** - 70+ leaderboards with minimal impact
- **Concurrent Requests** - Multiple API requests handled simultaneously
- **File-based Storage** - Uses Minecraft's native stats storage

## 📝 Developer Features

### Extensibility
- **Custom Leaderboards** - Easy to add new leaderboards
- **API Extension** - Simple to add new endpoints
- **Modular Design** - Clean separation of concerns
- **Well Documented** - Comprehensive developer documentation

### Code Quality
- **Type Safety** - Strongly typed Java code
- **Error Handling** - Comprehensive exception handling
- **Logging** - SLF4J logging throughout
- **Code Comments** - Inline documentation

## 🎯 Use Cases

### Server Administration
- Track player activity and engagement
- Monitor player progression
- Identify top performers
- Analyze player behavior patterns

### Player Engagement
- Competitive rankings
- Achievement tracking
- Community challenges
- Friendly competition

### Web Integration
- Server statistics websites
- Player profile pages
- Live leaderboard displays
- Mobile companion apps

### Content Creation
- Stream overlays with live stats
- YouTube video statistics
- Tournament tracking
- Event leaderboards

## 🚀 Future Extensibility

The mod is designed for easy extension with:
- Custom stat tracking
- Additional API endpoints
- New leaderboard types
- Integration with other mods
- Advanced filtering and sorting
- Historical data tracking
- Achievements system
- Team/faction leaderboards
