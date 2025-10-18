package com.leclowndu93150.leaderboards;

import com.leclowndu93150.leaderboards.data.Leaderboard;
import com.leclowndu93150.leaderboards.data.PlayerStatsWrapper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class VanillaStatsRegistry {
    public static final Map<ResourceLocation, Leaderboard> VANILLA_STATS = new LinkedHashMap<>();

    public static void register() {
        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_blocks_mined"),
                new Leaderboard(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_blocks_mined"),
                        Component.translatable("leaderboard.leaderboards.total_blocks_mined"),
                        (PlayerStatsWrapper player) -> {
                            int total = 0;
                            for (Stat<?> stat : Stats.BLOCK_MINED) {
                                total += player.getStats().getValue(stat);
                            }
                            return Component.literal(String.valueOf(total));
                        },
                        Comparator.comparingInt((PlayerStatsWrapper player) -> {
                            int total = 0;
                            for (Stat<?> stat : Stats.BLOCK_MINED) {
                                total += player.getStats().getValue(stat);
                            }
                            return total;
                        }).reversed(),
                        (PlayerStatsWrapper player) -> {
                            for (Stat<?> stat : Stats.BLOCK_MINED) {
                                if (player.getStats().getValue(stat) > 0) return true;
                            }
                            return false;
                        }
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_items_crafted"),
                new Leaderboard(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_items_crafted"),
                        Component.translatable("leaderboard.leaderboards.total_items_crafted"),
                        (PlayerStatsWrapper player) -> {
                            int total = 0;
                            for (Stat<?> stat : Stats.ITEM_CRAFTED) {
                                total += player.getStats().getValue(stat);
                            }
                            return Component.literal(String.valueOf(total));
                        },
                        Comparator.comparingInt((PlayerStatsWrapper player) -> {
                            int total = 0;
                            for (Stat<?> stat : Stats.ITEM_CRAFTED) {
                                total += player.getStats().getValue(stat);
                            }
                            return total;
                        }).reversed(),
                        (PlayerStatsWrapper player) -> {
                            for (Stat<?> stat : Stats.ITEM_CRAFTED) {
                                if (player.getStats().getValue(stat) > 0) return true;
                            }
                            return false;
                        }
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_items_used"),
                new Leaderboard(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_items_used"),
                        Component.translatable("leaderboard.leaderboards.total_items_used"),
                        (PlayerStatsWrapper player) -> {
                            int total = 0;
                            for (Stat<?> stat : Stats.ITEM_USED) {
                                total += player.getStats().getValue(stat);
                            }
                            return Component.literal(String.valueOf(total));
                        },
                        Comparator.comparingInt((PlayerStatsWrapper player) -> {
                            int total = 0;
                            for (Stat<?> stat : Stats.ITEM_USED) {
                                total += player.getStats().getValue(stat);
                            }
                            return total;
                        }).reversed(),
                        (PlayerStatsWrapper player) -> {
                            for (Stat<?> stat : Stats.ITEM_USED) {
                                if (player.getStats().getValue(stat) > 0) return true;
                            }
                            return false;
                        }
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_items_broken"),
                new Leaderboard(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_items_broken"),
                        Component.translatable("leaderboard.leaderboards.total_items_broken"),
                        (PlayerStatsWrapper player) -> {
                            int total = 0;
                            for (Stat<?> stat : Stats.ITEM_BROKEN) {
                                total += player.getStats().getValue(stat);
                            }
                            return Component.literal(String.valueOf(total));
                        },
                        Comparator.comparingInt((PlayerStatsWrapper player) -> {
                            int total = 0;
                            for (Stat<?> stat : Stats.ITEM_BROKEN) {
                                total += player.getStats().getValue(stat);
                            }
                            return total;
                        }).reversed(),
                        (PlayerStatsWrapper player) -> {
                            for (Stat<?> stat : Stats.ITEM_BROKEN) {
                                if (player.getStats().getValue(stat) > 0) return true;
                            }
                            return false;
                        }
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_items_picked_up"),
                new Leaderboard(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_items_picked_up"),
                        Component.translatable("leaderboard.leaderboards.total_items_picked_up"),
                        (PlayerStatsWrapper player) -> {
                            int total = 0;
                            for (Stat<?> stat : Stats.ITEM_PICKED_UP) {
                                total += player.getStats().getValue(stat);
                            }
                            return Component.literal(String.valueOf(total));
                        },
                        Comparator.comparingInt((PlayerStatsWrapper player) -> {
                            int total = 0;
                            for (Stat<?> stat : Stats.ITEM_PICKED_UP) {
                                total += player.getStats().getValue(stat);
                            }
                            return total;
                        }).reversed(),
                        (PlayerStatsWrapper player) -> {
                            for (Stat<?> stat : Stats.ITEM_PICKED_UP) {
                                if (player.getStats().getValue(stat) > 0) return true;
                            }
                            return false;
                        }
                )
        );


        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_dealt"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_dealt"),
                        Component.translatable("leaderboard.leaderboards.damage_dealt"),
                        Stats.CUSTOM.get(Stats.DAMAGE_DEALT),
                        false,
                        Leaderboard.FromStat.DAMAGE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_taken"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_taken"),
                        Component.translatable("leaderboard.leaderboards.damage_taken"),
                        Stats.CUSTOM.get(Stats.DAMAGE_TAKEN),
                        false,
                        Leaderboard.FromStat.DAMAGE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_blocked_by_shield"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_blocked_by_shield"),
                        Component.translatable("leaderboard.leaderboards.damage_blocked_by_shield"),
                        Stats.CUSTOM.get(Stats.DAMAGE_BLOCKED_BY_SHIELD),
                        false,
                        Leaderboard.FromStat.DAMAGE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_absorbed"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_absorbed"),
                        Component.translatable("leaderboard.leaderboards.damage_absorbed"),
                        Stats.CUSTOM.get(Stats.DAMAGE_ABSORBED),
                        false,
                        Leaderboard.FromStat.DAMAGE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_resisted"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_resisted"),
                        Component.translatable("leaderboard.leaderboards.damage_resisted"),
                        Stats.CUSTOM.get(Stats.DAMAGE_RESISTED),
                        false,
                        Leaderboard.FromStat.DAMAGE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_dealt_absorbed"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_dealt_absorbed"),
                        Component.translatable("leaderboard.leaderboards.damage_dealt_absorbed"),
                        Stats.CUSTOM.get(Stats.DAMAGE_DEALT_ABSORBED),
                        false,
                        Leaderboard.FromStat.DAMAGE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_dealt_resisted"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "damage_dealt_resisted"),
                        Component.translatable("leaderboard.leaderboards.damage_dealt_resisted"),
                        Stats.CUSTOM.get(Stats.DAMAGE_DEALT_RESISTED),
                        false,
                        Leaderboard.FromStat.DAMAGE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "animals_bred"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "animals_bred"),
                        Component.translatable("leaderboard.leaderboards.animals_bred"),
                        Stats.CUSTOM.get(Stats.ANIMALS_BRED),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "fish_caught"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "fish_caught"),
                        Component.translatable("leaderboard.leaderboards.fish_caught"),
                        Stats.CUSTOM.get(Stats.FISH_CAUGHT),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "fly_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "fly_distance"),
                        Component.translatable("leaderboard.leaderboards.fly_distance"),
                        Stats.CUSTOM.get(Stats.FLY_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "swim_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "swim_distance"),
                        Component.translatable("leaderboard.leaderboards.swim_distance"),
                        Stats.CUSTOM.get(Stats.SWIM_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "horse_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "horse_distance"),
                        Component.translatable("leaderboard.leaderboards.horse_distance"),
                        Stats.CUSTOM.get(Stats.HORSE_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "boat_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "boat_distance"),
                        Component.translatable("leaderboard.leaderboards.boat_distance"),
                        Stats.CUSTOM.get(Stats.BOAT_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "elytra_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "elytra_distance"),
                        Component.translatable("leaderboard.leaderboards.elytra_distance"),
                        Stats.CUSTOM.get(Stats.AVIATE_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "minecart_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "minecart_distance"),
                        Component.translatable("leaderboard.leaderboards.minecart_distance"),
                        Stats.CUSTOM.get(Stats.MINECART_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "pig_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "pig_distance"),
                        Component.translatable("leaderboard.leaderboards.pig_distance"),
                        Stats.CUSTOM.get(Stats.PIG_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "strider_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "strider_distance"),
                        Component.translatable("leaderboard.leaderboards.strider_distance"),
                        Stats.CUSTOM.get(Stats.STRIDER_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "walk_under_water_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "walk_under_water_distance"),
                        Component.translatable("leaderboard.leaderboards.walk_under_water_distance"),
                        Stats.CUSTOM.get(Stats.WALK_UNDER_WATER_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "walk_on_water_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "walk_on_water_distance"),
                        Component.translatable("leaderboard.leaderboards.walk_on_water_distance"),
                        Stats.CUSTOM.get(Stats.WALK_ON_WATER_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "climb_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "climb_distance"),
                        Component.translatable("leaderboard.leaderboards.climb_distance"),
                        Stats.CUSTOM.get(Stats.CLIMB_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "fall_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "fall_distance"),
                        Component.translatable("leaderboard.leaderboards.fall_distance"),
                        Stats.CUSTOM.get(Stats.FALL_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "crouch_distance"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "crouch_distance"),
                        Component.translatable("leaderboard.leaderboards.crouch_distance"),
                        Stats.CUSTOM.get(Stats.CROUCH_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "crouch_time"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "crouch_time"),
                        Component.translatable("leaderboard.leaderboards.crouch_time"),
                        Stats.CUSTOM.get(Stats.CROUCH_TIME),
                        false,
                        Leaderboard.FromStat.TIME
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "time_since_death"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "time_since_death"),
                        Component.translatable("leaderboard.leaderboards.time_since_death"),
                        Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH),
                        false,
                        Leaderboard.FromStat.TIME
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "time_since_rest"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "time_since_rest"),
                        Component.translatable("leaderboard.leaderboards.time_since_rest"),
                        Stats.CUSTOM.get(Stats.TIME_SINCE_REST),
                        false,
                        Leaderboard.FromStat.TIME
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "villager_trades"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "villager_trades"),
                        Component.translatable("leaderboard.leaderboards.villager_trades"),
                        Stats.CUSTOM.get(Stats.TRADED_WITH_VILLAGER),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "talked_to_villager"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "talked_to_villager"),
                        Component.translatable("leaderboard.leaderboards.talked_to_villager"),
                        Stats.CUSTOM.get(Stats.TALKED_TO_VILLAGER),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "raids_won"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "raids_won"),
                        Component.translatable("leaderboard.leaderboards.raids_won"),
                        Stats.CUSTOM.get(Stats.RAID_WIN),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "raids_triggered"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "raids_triggered"),
                        Component.translatable("leaderboard.leaderboards.raids_triggered"),
                        Stats.CUSTOM.get(Stats.RAID_TRIGGER),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "target_hit"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "target_hit"),
                        Component.translatable("leaderboard.leaderboards.target_hit"),
                        Stats.CUSTOM.get(Stats.TARGET_HIT),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "bells_rung"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "bells_rung"),
                        Component.translatable("leaderboard.leaderboards.bells_rung"),
                        Stats.CUSTOM.get(Stats.BELL_RING),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "items_dropped"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "items_dropped"),
                        Component.translatable("leaderboard.leaderboards.items_dropped"),
                        Stats.CUSTOM.get(Stats.DROP),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "enchantments_done"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "enchantments_done"),
                        Component.translatable("leaderboard.leaderboards.enchantments_done"),
                        Stats.CUSTOM.get(Stats.ENCHANT_ITEM),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "times_slept"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "times_slept"),
                        Component.translatable("leaderboard.leaderboards.times_slept"),
                        Stats.CUSTOM.get(Stats.SLEEP_IN_BED),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_world_time"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "total_world_time"),
                        Component.translatable("leaderboard.leaderboards.total_world_time"),
                        Stats.CUSTOM.get(Stats.TOTAL_WORLD_TIME),
                        false,
                        Leaderboard.FromStat.TIME
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "cake_slices_eaten"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "cake_slices_eaten"),
                        Component.translatable("leaderboard.leaderboards.cake_slices_eaten"),
                        Stats.CUSTOM.get(Stats.EAT_CAKE_SLICE),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "chests_opened"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "chests_opened"),
                        Component.translatable("leaderboard.leaderboards.chests_opened"),
                        Stats.CUSTOM.get(Stats.OPEN_CHEST),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "ender_chests_opened"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "ender_chests_opened"),
                        Component.translatable("leaderboard.leaderboards.ender_chests_opened"),
                        Stats.CUSTOM.get(Stats.OPEN_ENDERCHEST),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "shulker_boxes_opened"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "shulker_boxes_opened"),
                        Component.translatable("leaderboard.leaderboards.shulker_boxes_opened"),
                        Stats.CUSTOM.get(Stats.OPEN_SHULKER_BOX),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "barrels_opened"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "barrels_opened"),
                        Component.translatable("leaderboard.leaderboards.barrels_opened"),
                        Stats.CUSTOM.get(Stats.OPEN_BARREL),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "furnace_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "furnace_interactions"),
                        Component.translatable("leaderboard.leaderboards.furnace_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_FURNACE),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "crafting_table_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "crafting_table_interactions"),
                        Component.translatable("leaderboard.leaderboards.crafting_table_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_CRAFTING_TABLE),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "blast_furnace_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "blast_furnace_interactions"),
                        Component.translatable("leaderboard.leaderboards.blast_furnace_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_BLAST_FURNACE),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "smoker_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "smoker_interactions"),
                        Component.translatable("leaderboard.leaderboards.smoker_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_SMOKER),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "anvil_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "anvil_interactions"),
                        Component.translatable("leaderboard.leaderboards.anvil_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_ANVIL),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "grindstone_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "grindstone_interactions"),
                        Component.translatable("leaderboard.leaderboards.grindstone_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_GRINDSTONE),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "smithing_table_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "smithing_table_interactions"),
                        Component.translatable("leaderboard.leaderboards.smithing_table_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_SMITHING_TABLE),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "beacon_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "beacon_interactions"),
                        Component.translatable("leaderboard.leaderboards.beacon_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_BEACON),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "brewing_stand_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "brewing_stand_interactions"),
                        Component.translatable("leaderboard.leaderboards.brewing_stand_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_BREWINGSTAND),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "lectern_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "lectern_interactions"),
                        Component.translatable("leaderboard.leaderboards.lectern_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_LECTERN),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "campfire_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "campfire_interactions"),
                        Component.translatable("leaderboard.leaderboards.campfire_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_CAMPFIRE),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "cartography_table_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "cartography_table_interactions"),
                        Component.translatable("leaderboard.leaderboards.cartography_table_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_CARTOGRAPHY_TABLE),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "loom_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "loom_interactions"),
                        Component.translatable("leaderboard.leaderboards.loom_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_LOOM),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "stonecutter_interactions"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "stonecutter_interactions"),
                        Component.translatable("leaderboard.leaderboards.stonecutter_interactions"),
                        Stats.CUSTOM.get(Stats.INTERACT_WITH_STONECUTTER),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "hopper_inspections"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "hopper_inspections"),
                        Component.translatable("leaderboard.leaderboards.hopper_inspections"),
                        Stats.CUSTOM.get(Stats.INSPECT_HOPPER),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "dropper_inspections"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "dropper_inspections"),
                        Component.translatable("leaderboard.leaderboards.dropper_inspections"),
                        Stats.CUSTOM.get(Stats.INSPECT_DROPPER),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "dispenser_inspections"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "dispenser_inspections"),
                        Component.translatable("leaderboard.leaderboards.dispenser_inspections"),
                        Stats.CUSTOM.get(Stats.INSPECT_DISPENSER),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "noteblocks_played"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "noteblocks_played"),
                        Component.translatable("leaderboard.leaderboards.noteblocks_played"),
                        Stats.CUSTOM.get(Stats.PLAY_NOTEBLOCK),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "noteblocks_tuned"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "noteblocks_tuned"),
                        Component.translatable("leaderboard.leaderboards.noteblocks_tuned"),
                        Stats.CUSTOM.get(Stats.TUNE_NOTEBLOCK),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "flowers_potted"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "flowers_potted"),
                        Component.translatable("leaderboard.leaderboards.flowers_potted"),
                        Stats.CUSTOM.get(Stats.POT_FLOWER),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "trapped_chests_triggered"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "trapped_chests_triggered"),
                        Component.translatable("leaderboard.leaderboards.trapped_chests_triggered"),
                        Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "records_played"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "records_played"),
                        Component.translatable("leaderboard.leaderboards.records_played"),
                        Stats.CUSTOM.get(Stats.PLAY_RECORD),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "cauldrons_filled"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "cauldrons_filled"),
                        Component.translatable("leaderboard.leaderboards.cauldrons_filled"),
                        Stats.CUSTOM.get(Stats.FILL_CAULDRON),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "cauldrons_used"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "cauldrons_used"),
                        Component.translatable("leaderboard.leaderboards.cauldrons_used"),
                        Stats.CUSTOM.get(Stats.USE_CAULDRON),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "armor_pieces_cleaned"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "armor_pieces_cleaned"),
                        Component.translatable("leaderboard.leaderboards.armor_pieces_cleaned"),
                        Stats.CUSTOM.get(Stats.CLEAN_ARMOR),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "banners_cleaned"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "banners_cleaned"),
                        Component.translatable("leaderboard.leaderboards.banners_cleaned"),
                        Stats.CUSTOM.get(Stats.CLEAN_BANNER),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "shulker_boxes_cleaned"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "shulker_boxes_cleaned"),
                        Component.translatable("leaderboard.leaderboards.shulker_boxes_cleaned"),
                        Stats.CUSTOM.get(Stats.CLEAN_SHULKER_BOX),
                        false
                )
        );

        VANILLA_STATS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "leave_game"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "leave_game"),
                        Component.translatable("leaderboard.leaderboards.leave_game"),
                        Stats.CUSTOM.get(Stats.LEAVE_GAME),
                        false
                )
        );
    }
}
