# Complete Leaderboards List

All 70+ leaderboards available in the mod and accessible via the REST API.

## Core Leaderboards (9 total)

| ID | Name | Description | Format |
|----|------|-------------|--------|
| `leaderboards:deaths` | Deaths | Total player deaths | Integer |
| `leaderboards:mob_kills` | Mob Kills | Total mobs killed | Integer |
| `leaderboards:player_kills` | Player Kills | Total players killed | Integer |
| `leaderboards:time_played` | Time Played | Total time on server | HH:MM:SS |
| `leaderboards:jumps` | Jumps | Total jumps performed | Integer |
| `leaderboards:distance_walked` | Distance Walked | Total walking distance | Meters/Km |
| `leaderboards:distance_sprinted` | Distance Sprinted | Total sprinting distance | Meters/Km |
| `leaderboards:deaths_per_hour` | Deaths Per Hour | Death rate calculation | Decimal |
| `leaderboards:last_seen` | Last Seen | Time since last online | Time/Online |

## Vanilla Statistics (60+ total)

### Mining & Crafting (5 leaderboards)

| ID | Name | Description |
|----|------|-------------|
| `leaderboards:total_blocks_mined` | Total Blocks Mined | All blocks mined combined |
| `leaderboards:total_items_crafted` | Total Items Crafted | All items crafted combined |
| `leaderboards:total_items_used` | Total Items Used | All items used combined |
| `leaderboards:total_items_broken` | Total Items Broken | Tools/armor broken |
| `leaderboards:total_items_picked_up` | Total Items Picked Up | Items collected from ground |

### Combat & Damage (7 leaderboards)

| ID | Name | Description | Format |
|----|------|-------------|--------|
| `leaderboards:damage_dealt` | Damage Dealt | Total damage to entities | Damage points |
| `leaderboards:damage_taken` | Damage Taken | Total damage received | Damage points |
| `leaderboards:damage_blocked_by_shield` | Damage Blocked | Damage prevented by shield | Damage points |
| `leaderboards:damage_absorbed` | Damage Absorbed | Damage absorbed by armor | Damage points |
| `leaderboards:damage_resisted` | Damage Resisted | Damage resisted | Damage points |
| `leaderboards:damage_dealt_absorbed` | Damage Dealt Absorbed | Your damage that was absorbed | Damage points |
| `leaderboards:damage_dealt_resisted` | Damage Dealt Resisted | Your damage that was resisted | Damage points |

### Movement & Travel (14 leaderboards)

| ID | Name | Description | Format |
|----|------|-------------|--------|
| `leaderboards:fly_distance` | Fly Distance | Distance flown (creative/spectator) | Meters/Km |
| `leaderboards:swim_distance` | Swim Distance | Distance swam in water | Meters/Km |
| `leaderboards:horse_distance` | Horse Distance | Distance on horse | Meters/Km |
| `leaderboards:boat_distance` | Boat Distance | Distance in boat | Meters/Km |
| `leaderboards:elytra_distance` | Elytra Distance | Distance with elytra | Meters/Km |
| `leaderboards:minecart_distance` | Minecart Distance | Distance in minecart | Meters/Km |
| `leaderboards:pig_distance` | Pig Distance | Distance on pig | Meters/Km |
| `leaderboards:strider_distance` | Strider Distance | Distance on strider | Meters/Km |
| `leaderboards:walk_under_water_distance` | Walk Underwater Distance | Distance walked underwater | Meters/Km |
| `leaderboards:walk_on_water_distance` | Walk On Water Distance | Distance on water (frost walker) | Meters/Km |
| `leaderboards:climb_distance` | Climb Distance | Distance climbed | Meters/Km |
| `leaderboards:fall_distance` | Fall Distance | Distance fallen | Meters/Km |
| `leaderboards:crouch_distance` | Crouch Distance | Distance crouched | Meters/Km |
| `leaderboards:crouch_time` | Crouch Time | Time spent crouching | HH:MM:SS |

### Activities & Interactions (16 leaderboards)

| ID | Name | Description |
|----|------|-------------|
| `leaderboards:animals_bred` | Animals Bred | Total animals bred |
| `leaderboards:fish_caught` | Fish Caught | Total fish caught |
| `leaderboards:time_since_death` | Time Since Death | Time since last death |
| `leaderboards:time_since_rest` | Time Since Rest | Time since last sleep |
| `leaderboards:villager_trades` | Villager Trades | Trades with villagers |
| `leaderboards:talked_to_villager` | Talked To Villager | Times talked to villagers |
| `leaderboards:raids_won` | Raids Won | Raids successfully completed |
| `leaderboards:raids_triggered` | Raids Triggered | Raids started |
| `leaderboards:target_hit` | Target Hit | Target blocks hit |
| `leaderboards:bells_rung` | Bells Rung | Bells rung |
| `leaderboards:items_dropped` | Items Dropped | Items dropped from inventory |
| `leaderboards:enchantments_done` | Enchantments Done | Items enchanted |
| `leaderboards:times_slept` | Times Slept | Times slept in bed |
| `leaderboards:total_world_time` | Total World Time | Total time in world |
| `leaderboards:cake_slices_eaten` | Cake Slices Eaten | Cake slices consumed |

### Container Interactions (4 leaderboards)

| ID | Name | Description |
|----|------|-------------|
| `leaderboards:chests_opened` | Chests Opened | Chests opened |
| `leaderboards:ender_chests_opened` | Ender Chests Opened | Ender chests opened |
| `leaderboards:shulker_boxes_opened` | Shulker Boxes Opened | Shulker boxes opened |
| `leaderboards:barrels_opened` | Barrels Opened | Barrels opened |

### Workstation Interactions (10 leaderboards)

| ID | Name | Description |
|----|------|-------------|
| `leaderboards:furnace_interactions` | Furnace Interactions | Times furnace used |
| `leaderboards:crafting_table_interactions` | Crafting Table Interactions | Times crafting table used |
| `leaderboards:blast_furnace_interactions` | Blast Furnace Interactions | Times blast furnace used |
| `leaderboards:smoker_interactions` | Smoker Interactions | Times smoker used |
| `leaderboards:anvil_interactions` | Anvil Interactions | Times anvil used |
| `leaderboards:grindstone_interactions` | Grindstone Interactions | Times grindstone used |
| `leaderboards:smithing_table_interactions` | Smithing Table Interactions | Times smithing table used |
| `leaderboards:beacon_interactions` | Beacon Interactions | Times beacon used |
| `leaderboards:brewing_stand_interactions` | Brewing Stand Interactions | Times brewing stand used |
| `leaderboards:lectern_interactions` | Lectern Interactions | Times lectern used |

## FTB Quests Integration (2 leaderboards)

**Note**: Only available when FTB Quests mod is installed.

| ID | Name | Description | Format |
|----|------|-------------|--------|
| `leaderboards:quest_completions` | Quest Completions | Total quests completed | Integer |
| `leaderboards:quest_completion_percentage` | Quest Completion % | Percentage of quests done | Percentage |

## API Access

All leaderboards are accessible via the REST API at `/api/leaderboards`.

### List All Leaderboards

```bash
curl http://localhost:8080/api/leaderboards
```

### Get Specific Leaderboard

```bash
curl http://localhost:8080/api/leaderboard/{id}
```

Example:
```bash
curl http://localhost:8080/api/leaderboard/leaderboards:deaths
```

### Get Player Stats Across All Leaderboards

```bash
curl http://localhost:8080/api/player/{player_name}
```

Example:
```bash
curl http://localhost:8080/api/player/Steve
```

## Value Formatting

The mod automatically formats values for better readability:

- **Time Values**: Displayed as `HH:MM:SS` (e.g., `12:34:56`)
- **Distance Values**: Converted to meters or kilometers (e.g., `1.5 km`, `250 m`)
- **Damage Values**: Displayed with decimal precision (e.g., `42.5`)
- **Percentage Values**: Shown with % symbol (e.g., `87.3%`)

## Sorting

Each leaderboard is sorted appropriately:

- **Descending** (most leaderboards): Higher values rank higher
  - Deaths, Kills, Distance, Time, etc.

- **Ascending** (some specific stats): Lower values rank higher
  - Deaths Per Hour (lower is better, with minimum threshold)
  - Last Seen (most recent = rank 1)

## Statistics by Category

### Summary

| Category | Count |
|----------|-------|
| Core Statistics | 9 |
| Mining & Crafting | 5 |
| Combat & Damage | 7 |
| Movement & Travel | 14 |
| Activities & Interactions | 16 |
| Container Interactions | 4 |
| Workstation Interactions | 10 |
| FTB Quests (optional) | 2 |
| **Total** | **67 (69 with FTB Quests)** |

### Player Participation

Not all players will appear in all leaderboards. A player only appears in a leaderboard if:
- They have a non-zero value for that statistic
- The leaderboard's validity check passes
- They have played on the server (stats file exists)

## Adding Custom Leaderboards

Developers can add custom leaderboards by editing `LeaderboardRegistry.java`. See the [Development Guide](DEVELOPMENT.md#adding-new-leaderboards) for details.

## Technical Details

### Data Source

All statistics are sourced from Minecraft's native statistics system (`ServerStatsCounter`), stored in `world/stats/*.json` files.

### Calculation

- Most statistics are direct values from Minecraft
- `deaths_per_hour` is calculated as: `deaths / (play_time / 72000)`
- `last_seen` uses custom tracking via `PlayerDataTracker`
- Quest stats use FTB Quests API

### Updates

- Statistics update in real-time as players perform actions
- Leaderboards refresh when queried
- No caching (always shows current data)

## See Also

- [Features Documentation](FEATURES.md) - All mod features
- [API Documentation](API.md) - REST API reference
- [Development Guide](DEVELOPMENT.md) - Adding custom leaderboards
