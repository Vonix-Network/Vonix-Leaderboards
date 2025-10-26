# Documentation Index

Complete documentation for the Leaderboards mod.

## 📚 Documentation Files

### User Documentation

- **[Features](FEATURES.md)** - Complete list of all mod features and capabilities
  - 70+ leaderboards
  - In-game GUI features
  - REST API capabilities
  - Integration options

- **[Configuration](CONFIGURATION.md)** - Configuration guide
  - Config file reference
  - Configuration scenarios
  - Security best practices
  - Troubleshooting

- **[API Reference](API.md)** - REST API documentation
  - Endpoint reference
  - Request/response examples
  - Usage examples (JavaScript, Python, cURL)
  - Integration guide

### Developer Documentation

- **[Development Guide](DEVELOPMENT.md)** - Developer documentation
  - Development environment setup
  - Building the project
  - Project structure
  - Adding new leaderboards
  - Adding new API endpoints
  - Testing procedures
  - Contributing guidelines

### Additional Resources

- **[Leaderboards List](LEADERBOARDS_LIST.md)** - Complete list of all 70+ leaderboards
- **[Quick Start](QUICK_START.md)** - Get started in 5 minutes
- **[FAQ](FAQ.md)** - Frequently asked questions

## 🚀 Quick Links

### For Server Administrators

1. **Installation** - See main [README](../README.md#installation)
2. **Configuration** - See [Configuration Guide](CONFIGURATION.md)
3. **API Setup** - See [API Reference](API.md#configuration)

### For Developers

1. **Build Instructions** - See [Development Guide](DEVELOPMENT.md#building-the-project)
2. **Adding Leaderboards** - See [Development Guide](DEVELOPMENT.md#adding-new-leaderboards)
3. **API Development** - See [Development Guide](DEVELOPMENT.md#api-architecture)

### For Web Developers

1. **API Endpoints** - See [API Reference](API.md#endpoints)
2. **Usage Examples** - See [API Reference](API.md#usage-examples)
3. **Example Website** - See [example_website.html](../example_website.html)

## 📖 Getting Started

### New Users

1. Read the main [README](../README.md)
2. Check out [Features](FEATURES.md) to see what's available
3. Follow the [Quick Start](QUICK_START.md) guide

### Server Owners

1. Install the mod (see main README)
2. Review [Configuration](CONFIGURATION.md)
3. Set up [API access](API.md) if needed

### Mod Developers

1. Set up your [development environment](DEVELOPMENT.md#development-environment-setup)
2. Review the [project structure](DEVELOPMENT.md#project-structure)
3. Learn how to [add new leaderboards](DEVELOPMENT.md#adding-new-leaderboards)

### Web Developers

1. Enable the API in [configuration](CONFIGURATION.md)
2. Review [API endpoints](API.md#endpoints)
3. Check out the [example website](../example_website.html)

## 🎯 Common Tasks

### Viewing Leaderboards In-Game

- Press the configured keybind or use `/leaderboards` command
- Browse available leaderboards
- View player rankings

### Accessing Leaderboards via API

```bash
# List all leaderboards
curl http://localhost:8080/api/leaderboards

# Get specific leaderboard
curl http://localhost:8080/api/leaderboard/leaderboards:deaths

# Get player stats
curl http://localhost:8080/api/player/PlayerName
```

### Configuring the API

1. Edit `config/leaderboards-server.toml`
2. Set your preferences (port, CORS, etc.)
3. Restart the server
4. Verify in server logs

### Building from Source

```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

## 🔧 Support

### Common Issues

- **API not responding** - Check [Configuration Troubleshooting](CONFIGURATION.md#troubleshooting)
- **Build errors** - See [Development Guide](DEVELOPMENT.md#troubleshooting-development-issues)
- **CORS errors** - Review [API Configuration](API.md#cors-support)

### Getting Help

1. Check the [FAQ](FAQ.md)
2. Review relevant documentation section
3. Search existing GitHub issues
4. Create a new GitHub issue if problem persists

## 📊 Feature Overview

### Leaderboards
- **9 Core Stats** - Essential player statistics
- **60+ Vanilla Stats** - Comprehensive Minecraft statistics
- **2 Quest Stats** - FTB Quests integration (optional)

### API Features
- **3 Endpoints** - List, leaderboard, and player stats
- **JSON Responses** - Standard, easy-to-parse format
- **CORS Support** - Web browser integration
- **Configurable** - Flexible settings for all use cases

### In-Game Features
- **GUI Browser** - User-friendly interface
- **Real-time Updates** - Live stat tracking
- **Formatted Values** - Human-readable statistics
- **Persistent Data** - Stats saved across restarts

## 🛠️ Technical Details

### Requirements
- Minecraft 1.21.1
- NeoForge
- Java 21+

### Dependencies
- FTB Library (required)
- Architectury API (required)
- FTB Quests (optional)

### Performance
- Minimal server overhead
- On-demand stat loading
- Thread-pooled API requests
- Efficient caching

## 📝 Contributing

We welcome contributions! See:
- [Development Guide](DEVELOPMENT.md#contributing)
- [Code Style](DEVELOPMENT.md#code-style)
- [Pull Request Process](DEVELOPMENT.md#pull-request-process)

## 📜 License

See the main [README](../README.md#license) for license information.

## 🙏 Credits

See the main [README](../README.md#credits) for credits and acknowledgments.

---

**Documentation Version**: 1.0.0  
**Last Updated**: 2025-10-26  
**Mod Version**: 1.0.0
