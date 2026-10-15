package net.earlystage.init;

import net.earlystage.EarlyStageMain;
import net.earlystage.item.BarkItem;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CompatInit {

    // Oblivion
    public static Item RUNE_BARK;
    public static Item SILVER_BIRCH_BARK;
    public static Item IRON_WOOD_BARK;

    // Natures Spirit
    public static Item ASPEN_BARK;
    public static Item CEDAR_BARK;
    public static Item COCONUT_BARK;
    public static Item CYPRESS_BARK;
    public static Item FIR_BARK;
    public static Item GHAF_BARK;
    public static Item JOSHUA_BARK;
    public static Item LARCH_BARK;
    public static Item MAHOGANY_BARK;
    public static Item MAPLE_BARK;
    public static Item OLIVE_BARK;
    public static Item PALO_BARK;
    public static Item REDWOOD_BARK;
    public static Item SAXAUL_BARK;
    public static Item SUGI_BARK;
    public static Item WILLOW_BARK;
    public static Item WISTERIA_BARK;

    // Regions unexplored
    public static Item BAOBAB_BARK;
    public static Item BLACKWOOD_BARK;
    public static Item BLUE_BIOSHROOM_BARK;
    public static Item BRIMWOOD_BARK;
    public static Item COBALT_BARK;
    public static Item DEAD_BARK;
    public static Item EUCALYPTUS_BARK;
    public static Item GREEN_BIOSHROOM_BARK;
    public static Item KAPOK_BARK;
    public static Item MAGNOLIA_BARK;
    public static Item MAUVE_BARK;
    public static Item PALM_BARK;
    public static Item PINE_BARK;
    public static Item PINK_BIOSHROOM_BARK;
    public static Item SMALL_OAK_BARK;
    public static Item SOCOTRA_BARK;
    public static Item YELLOW_BIOSHROOM_BARK;
    // Same as in natures spirit
    public static Item RU_CYPRESS_BARK;
    public static Item RU_JOSHUA_BARK;
    public static Item RU_LARCH_BARK;
    public static Item RU_MAPLE_BARK;
    public static Item RU_REDWOOD_BARK;
    public static Item RU_WILLOW_BARK;

    // Terrestria
    public static Item HEMLOCK_BARK;
    public static Item YUCCA_PALM_BARK;
    public static Item SAKURA_BARK;
    public static Item RUBBER_BARK;
    public static Item RAINBOW_EUCALYPTUS_BARK;
    public static Item JAPANESE_MAPLE_BARK;
    // Same as in natures spirit
    public static Item TE_CYPRESS_BARK;
    public static Item TE_WILLOW_BARK;
    public static Item TE_REDWOOD_BARK;

    // Meadow
    public static Item ALPINE_BIRCH_BARK;

    // Beachparty
    public static Item BP_PALM_BARK;

    private static final Map<Identifier, List<Consumer<Block>>> PENDING_BLOCKS = new HashMap<>();

    public static void init() {
        RegistryEntryAddedCallback.event(Registries.BLOCK).register((rawId, id, block) -> {
            List<Consumer<Block>> actions = PENDING_BLOCKS.remove(id);
            if (actions != null) {
                actions.forEach(action -> action.accept(block));
            }
        });

        if (FabricLoader.getInstance().isModLoaded("oblivion")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("oblivion_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
            // Rune Wood
            RUNE_BARK = registerBark("rune_bark", "oblivion:rune_wood_log", "oblivion:rune_wood");
            addStrippedCompat("oblivion:rune_wood_log", "oblivion:stripped_rune_wood_log");
            addStrippedCompat("oblivion:rune_wood", "oblivion:stripped_rune_wood");

            // Silver Birch
            SILVER_BIRCH_BARK = registerBark("silver_birch_bark", "oblivion:silver_birch_log", "oblivion:silver_birch");
            addStrippedCompat("oblivion:silver_birch_log", "oblivion:stripped_silver_birch_log");
            addStrippedCompat("oblivion:silver_birch", "oblivion:stripped_silver_birch");

            // Iron Wood
            IRON_WOOD_BARK = registerBark("iron_wood_bark", "oblivion:iron_wood_log", "oblivion:iron_wood");
            addStrippedCompat("oblivion:iron_wood_log", "oblivion:stripped_iron_wood_log");
            addStrippedCompat("oblivion:iron_wood", "oblivion:stripped_iron_wood");
        }
        if (FabricLoader.getInstance().isModLoaded("natures_spirit")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("natures_spirit_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
            // Aspen
            ASPEN_BARK = registerBark("aspen_bark", "natures_spirit:aspen_log", "natures_spirit:aspen_wood");
            addStrippedCompat("natures_spirit:aspen_log", "natures_spirit:stripped_aspen_log");
            addStrippedCompat("natures_spirit:aspen_wood", "natures_spirit:stripped_aspen_wood");

            // Cedar
            CEDAR_BARK = registerBark("cedar_bark", "natures_spirit:cedar_log", "natures_spirit:cedar_wood");
            addStrippedCompat("natures_spirit:cedar_log", "natures_spirit:stripped_cedar_log");
            addStrippedCompat("natures_spirit:cedar_wood", "natures_spirit:stripped_cedar_wood");

            // Coconut
            COCONUT_BARK = registerBark("coconut_bark", "natures_spirit:coconut_log", "natures_spirit:coconut_wood");
            addStrippedCompat("natures_spirit:coconut_log", "natures_spirit:stripped_coconut_log");
            addStrippedCompat("natures_spirit:coconut_wood", "natures_spirit:stripped_coconut_wood");

            // Cypress
            CYPRESS_BARK = registerBark("cypress_bark", "natures_spirit:cypress_log", "natures_spirit:cypress_wood");
            addStrippedCompat("natures_spirit:cypress_log", "natures_spirit:stripped_cypress_log");
            addStrippedCompat("natures_spirit:cypress_wood", "natures_spirit:stripped_cypress_wood");

            // Fir
            FIR_BARK = registerBark("fir_bark", "natures_spirit:fir_log", "natures_spirit:fir_wood");
            addStrippedCompat("natures_spirit:fir_log", "natures_spirit:stripped_fir_log");
            addStrippedCompat("natures_spirit:fir_wood", "natures_spirit:stripped_fir_wood");

            // Ghaf
            GHAF_BARK = registerBark("ghaf_bark", "natures_spirit:ghaf_log", "natures_spirit:ghaf_wood");
            addStrippedCompat("natures_spirit:ghaf_log", "natures_spirit:stripped_ghaf_log");
            addStrippedCompat("natures_spirit:ghaf_wood", "natures_spirit:stripped_ghaf_wood");

            // Joshua
            JOSHUA_BARK = registerBark("joshua_bark", "natures_spirit:joshua_log", "natures_spirit:joshua_wood");
            addStrippedCompat("natures_spirit:joshua_log", "natures_spirit:stripped_joshua_log");
            addStrippedCompat("natures_spirit:joshua_wood", "natures_spirit:stripped_joshua_wood");

            // Larch
            LARCH_BARK = registerBark("larch_bark", "natures_spirit:larch_log", "natures_spirit:larch_wood");
            addStrippedCompat("natures_spirit:larch_log", "natures_spirit:stripped_larch_log");
            addStrippedCompat("natures_spirit:larch_wood", "natures_spirit:stripped_larch_wood");

            // Mahogany
            MAHOGANY_BARK = registerBark("mahogany_bark", "natures_spirit:mahogany_log", "natures_spirit:mahogany_wood");
            addStrippedCompat("natures_spirit:mahogany_log", "natures_spirit:stripped_mahogany_log");
            addStrippedCompat("natures_spirit:mahogany_wood", "natures_spirit:stripped_mahogany_wood");

            // Maple
            MAPLE_BARK = registerBark("maple_bark", "natures_spirit:maple_log", "natures_spirit:maple_wood");
            addStrippedCompat("natures_spirit:maple_log", "natures_spirit:stripped_maple_log");
            addStrippedCompat("natures_spirit:maple_wood", "natures_spirit:stripped_maple_wood");

            // Olive
            OLIVE_BARK = registerBark("olive_bark", "natures_spirit:olive_log", "natures_spirit:olive_wood");
            addStrippedCompat("natures_spirit:olive_log", "natures_spirit:stripped_olive_log");
            addStrippedCompat("natures_spirit:olive_wood", "natures_spirit:stripped_olive_wood");

            // Palo Verde
            PALO_BARK = registerBark("palo_bark", "natures_spirit:palo_verde_log", "natures_spirit:palo_verde_wood");
            addStrippedCompat("natures_spirit:palo_verde_log", "natures_spirit:stripped_palo_verde_log");
            addStrippedCompat("natures_spirit:palo_verde_wood", "natures_spirit:stripped_palo_verde_wood");

            // Redwood
            REDWOOD_BARK = registerBark("redwood_bark", "natures_spirit:redwood_log", "natures_spirit:redwood_wood");
            addStrippedCompat("natures_spirit:redwood_log", "natures_spirit:stripped_redwood_log");
            addStrippedCompat("natures_spirit:redwood_wood", "natures_spirit:stripped_redwood_wood");

            // Saxaul
            SAXAUL_BARK = registerBark("saxaul_bark", "natures_spirit:saxaul_log", "natures_spirit:saxaul_wood");
            addStrippedCompat("natures_spirit:saxaul_log", "natures_spirit:stripped_saxaul_log");
            addStrippedCompat("natures_spirit:saxaul_wood", "natures_spirit:stripped_saxaul_wood");

            // Sugi
            SUGI_BARK = registerBark("sugi_bark", "natures_spirit:sugi_log", "natures_spirit:sugi_wood");
            addStrippedCompat("natures_spirit:sugi_log", "natures_spirit:stripped_sugi_log");
            addStrippedCompat("natures_spirit:sugi_wood", "natures_spirit:stripped_sugi_wood");

            // Willow
            WILLOW_BARK = registerBark("willow_bark", "natures_spirit:willow_log", "natures_spirit:willow_wood");
            addStrippedCompat("natures_spirit:willow_log", "natures_spirit:stripped_willow_log");
            addStrippedCompat("natures_spirit:willow_wood", "natures_spirit:stripped_willow_wood");

            // Wisteria
            WISTERIA_BARK = registerBark("wisteria_bark", "natures_spirit:wisteria_log", "natures_spirit:wisteria_wood");
            addStrippedCompat("natures_spirit:wisteria_log", "natures_spirit:stripped_wisteria_log");
            addStrippedCompat("natures_spirit:wisteria_wood", "natures_spirit:stripped_wisteria_wood");
        }
        if (FabricLoader.getInstance().isModLoaded("regions_unexplored")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("regions_unexplored_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);

            // Baobab
            BAOBAB_BARK = registerBark("baobab_bark", "regions_unexplored:baobab_log", "regions_unexplored:baobab_wood");
            addStrippedCompat("regions_unexplored:baobab_log", "regions_unexplored:stripped_baobab_log");
            addStrippedCompat("regions_unexplored:baobab_wood", "regions_unexplored:stripped_baobab_wood");

            // Blackwood
            BLACKWOOD_BARK = registerBark("blackwood_bark", "regions_unexplored:blackwood_log", "regions_unexplored:blackwood_wood");
            addStrippedCompat("regions_unexplored:blackwood_log", "regions_unexplored:stripped_blackwood_log");
            addStrippedCompat("regions_unexplored:blackwood_wood", "regions_unexplored:stripped_blackwood_wood");

            // Brimwood
            BRIMWOOD_BARK = registerBark("brimwood_bark", "regions_unexplored:brimwood_log", "regions_unexplored:brimwood_wood");
            addStrippedCompat("regions_unexplored:brimwood_log", "regions_unexplored:stripped_brimwood_log");
            addStrippedCompat("regions_unexplored:brimwood_wood", "regions_unexplored:stripped_brimwood_wood");

            // Cobalt
            COBALT_BARK = registerBark("cobalt_bark", "regions_unexplored:cobalt_log", "regions_unexplored:cobalt_wood");
            addStrippedCompat("regions_unexplored:cobalt_log", "regions_unexplored:stripped_cobalt_log");
            addStrippedCompat("regions_unexplored:cobalt_wood", "regions_unexplored:stripped_cobalt_wood");

            // Dead
            DEAD_BARK = registerBark("dead_bark", "regions_unexplored:dead_log", "regions_unexplored:dead_wood");
            addStrippedCompat("regions_unexplored:dead_log", "regions_unexplored:stripped_dead_log");
            addStrippedCompat("regions_unexplored:dead_wood", "regions_unexplored:stripped_dead_wood");

            // Eucalyptus
            EUCALYPTUS_BARK = registerBark("eucalyptus_bark", "regions_unexplored:eucalyptus_log", "regions_unexplored:eucalyptus_wood");
            addStrippedCompat("regions_unexplored:eucalyptus_log", "regions_unexplored:stripped_eucalyptus_log");
            addStrippedCompat("regions_unexplored:eucalyptus_wood", "regions_unexplored:stripped_eucalyptus_wood");

            // Kapok
            KAPOK_BARK = registerBark("kapok_bark", "regions_unexplored:kapok_log", "regions_unexplored:kapok_wood");
            addStrippedCompat("regions_unexplored:kapok_log", "regions_unexplored:stripped_kapok_log");
            addStrippedCompat("regions_unexplored:kapok_wood", "regions_unexplored:stripped_kapok_wood");

            // Magnolia
            MAGNOLIA_BARK = registerBark("magnolia_bark", "regions_unexplored:magnolia_log", "regions_unexplored:magnolia_wood");
            addStrippedCompat("regions_unexplored:magnolia_log", "regions_unexplored:stripped_magnolia_log");
            addStrippedCompat("regions_unexplored:magnolia_wood", "regions_unexplored:stripped_magnolia_wood");

            // Mauve
            MAUVE_BARK = registerBark("mauve_bark", "regions_unexplored:mauve_log", "regions_unexplored:mauve_wood");
            addStrippedCompat("regions_unexplored:mauve_log", "regions_unexplored:stripped_mauve_log");
            addStrippedCompat("regions_unexplored:mauve_wood", "regions_unexplored:stripped_mauve_wood");

            // Palm
            PALM_BARK = registerBark("palm_bark", "regions_unexplored:palm_log", "regions_unexplored:palm_wood");
            addStrippedCompat("regions_unexplored:palm_log", "regions_unexplored:stripped_palm_log");
            addStrippedCompat("regions_unexplored:palm_wood", "regions_unexplored:stripped_palm_wood");

            // Pine
            PINE_BARK = registerBark("pine_bark", "regions_unexplored:pine_log", "regions_unexplored:pine_wood");
            addStrippedCompat("regions_unexplored:pine_log", "regions_unexplored:stripped_pine_log");
            addStrippedCompat("regions_unexplored:pine_wood", "regions_unexplored:stripped_pine_wood");

            // Small Oak
            SMALL_OAK_BARK = registerBark("small_oak_bark", "regions_unexplored:small_oak_log", null);
            addStrippedCompat("regions_unexplored:small_oak_log", "regions_unexplored:stripped_small_oak_log");

            // Socotra
            SOCOTRA_BARK = registerBark("socotra_bark", "regions_unexplored:socotra_log", "regions_unexplored:socotra_wood");
            addStrippedCompat("regions_unexplored:socotra_log", "regions_unexplored:stripped_socotra_log");
            addStrippedCompat("regions_unexplored:socotra_wood", "regions_unexplored:stripped_socotra_wood");

            // Cypress
            RU_CYPRESS_BARK = registerBark("ru_cypress_bark", "regions_unexplored:cypress_log", "regions_unexplored:cypress_wood");
            addStrippedCompat("regions_unexplored:cypress_log", "regions_unexplored:stripped_cypress_log");
            addStrippedCompat("regions_unexplored:cypress_wood", "regions_unexplored:stripped_cypress_wood");

            // Joshua
            RU_JOSHUA_BARK = registerBark("ru_joshua_bark", "regions_unexplored:joshua_log", "regions_unexplored:joshua_wood");
            addStrippedCompat("regions_unexplored:joshua_log", "regions_unexplored:stripped_joshua_log");
            addStrippedCompat("regions_unexplored:joshua_wood", "regions_unexplored:stripped_joshua_wood");

            // Larch
            RU_LARCH_BARK = registerBark("ru_larch_bark", "regions_unexplored:larch_log", "regions_unexplored:larch_wood");
            addStrippedCompat("regions_unexplored:larch_log", "regions_unexplored:stripped_larch_log");
            addStrippedCompat("regions_unexplored:larch_wood", "regions_unexplored:stripped_larch_wood");

            // Maple
            RU_MAPLE_BARK = registerBark("ru_maple_bark", "regions_unexplored:maple_log", "regions_unexplored:maple_wood");
            addStrippedCompat("regions_unexplored:maple_log", "regions_unexplored:stripped_maple_log");
            addStrippedCompat("regions_unexplored:maple_wood", "regions_unexplored:stripped_maple_wood");

            // Redwood
            RU_REDWOOD_BARK = registerBark("ru_redwood_bark", "regions_unexplored:redwood_log", "regions_unexplored:redwood_wood");
            addStrippedCompat("regions_unexplored:redwood_log", "regions_unexplored:stripped_redwood_log");
            addStrippedCompat("regions_unexplored:redwood_wood", "regions_unexplored:stripped_redwood_wood");

            // Willow
            RU_WILLOW_BARK = registerBark("ru_willow_bark", "regions_unexplored:willow_log", "regions_unexplored:willow_wood");
            addStrippedCompat("regions_unexplored:willow_log", "regions_unexplored:stripped_willow_log");
            addStrippedCompat("regions_unexplored:willow_wood", "regions_unexplored:stripped_willow_wood");

            // Blue Bioshroom
            BLUE_BIOSHROOM_BARK = registerBark("blue_bioshroom_bark", "regions_unexplored:blue_bioshroom_hyphae", "regions_unexplored:blue_bioshroom_stem");
            addStrippedCompat("regions_unexplored:blue_bioshroom_hyphae", "regions_unexplored:stripped_blue_bioshroom_hyphae");
            addStrippedCompat("regions_unexplored:blue_bioshroom_stem", "regions_unexplored:stripped_blue_bioshroom_stem");

            // Green Bioshroom
            GREEN_BIOSHROOM_BARK = registerBark("green_bioshroom_bark", "regions_unexplored:green_bioshroom_hyphae", "regions_unexplored:green_bioshroom_stem");
            addStrippedCompat("regions_unexplored:green_bioshroom_hyphae", "regions_unexplored:stripped_green_bioshroom_hyphae");
            addStrippedCompat("regions_unexplored:green_bioshroom_stem", "regions_unexplored:stripped_green_bioshroom_stem");

            // Pink Bioshroom
            PINK_BIOSHROOM_BARK = registerBark("pink_bioshroom_bark", "regions_unexplored:pink_bioshroom_hyphae", "regions_unexplored:pink_bioshroom_stem");
            addStrippedCompat("regions_unexplored:pink_bioshroom_hyphae", "regions_unexplored:stripped_pink_bioshroom_hyphae");
            addStrippedCompat("regions_unexplored:pink_bioshroom_stem", "regions_unexplored:stripped_pink_bioshroom_stem");

            // Yellow Bioshroom
            YELLOW_BIOSHROOM_BARK = registerBark("yellow_bioshroom_bark", "regions_unexplored:yellow_bioshroom_hyphae", "regions_unexplored:yellow_bioshroom_stem");
            addStrippedCompat("regions_unexplored:yellow_bioshroom_hyphae", "regions_unexplored:stripped_yellow_bioshroom_hyphae");
            addStrippedCompat("regions_unexplored:yellow_bioshroom_stem", "regions_unexplored:stripped_yellow_bioshroom_stem");
        }
        if (FabricLoader.getInstance().isModLoaded("terrestria")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("terrestria_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
            // Hemlock
            HEMLOCK_BARK = registerBark("hemlock_bark", "terrestria:hemlock_log", "terrestria:hemlock_wood");
            addStrippedCompat("terrestria:hemlock_log", "terrestria:stripped_hemlock_log");
            addStrippedCompat("terrestria:hemlock_wood", "terrestria:stripped_hemlock_wood");
            // Yucca Palm
            YUCCA_PALM_BARK = registerBark("yucca_palm_bark", "terrestria:yucca_palm_log", null);
            addStrippedCompat("terrestria:yucca_palm_log", "terrestria:stripped_yucca_palm_log");
            // Sakura
            SAKURA_BARK = registerBark("sakura_bark", "terrestria:sakura_log", null);
            addStrippedCompat("terrestria:sakura_log", "terrestria:stripped_sakura_log");
            // Rubber
            RUBBER_BARK = registerBark("rubber_bark", "terrestria:rubber_log", "terrestria:rubber_wood");
            addStrippedCompat("terrestria:rubber_log", "terrestria:stripped_rubber_log");
            addStrippedCompat("terrestria:rubber_wood", "terrestria:stripped_rubber_wood");
            // Rainbow Eucalyptus
            RAINBOW_EUCALYPTUS_BARK = registerBark("rainbow_eucalyptus_bark", "terrestria:rainbow_eucalyptus_log", "terrestria:rainbow_eucalyptus_wood");
            addStrippedCompat("terrestria:rainbow_eucalyptus_log", "terrestria:stripped_rainbow_eucalyptus_log");
            addStrippedCompat("terrestria:rainbow_eucalyptus_wood", "terrestria:stripped_rainbow_eucalyptus_wood");
            // Japanese Maple
            JAPANESE_MAPLE_BARK = registerBark("japanese_maple_bark", "terrestria:japanese_maple_log", "terrestria:japanese_maple_wood");
            addStrippedCompat("terrestria:japanese_maple_log", "terrestria:stripped_japanese_maple_log");
            addStrippedCompat("terrestria:japanese_maple_wood", "terrestria:stripped_japanese_maple_wood");
            // Cypress
            TE_CYPRESS_BARK = registerBark("te_cypress_bark", "terrestria:cypress_log", "terrestria:cypress_wood");
            addStrippedCompat("terrestria:cypress_log", "terrestria:stripped_cypress_log");
            addStrippedCompat("terrestria:cypress_wood", "terrestria:stripped_cypress_wood");
            // Willow
            TE_WILLOW_BARK = registerBark("te_willow_bark", "terrestria:willow_log", "terrestria:willow_wood");
            addStrippedCompat("terrestria:willow_log", "terrestria:stripped_willow_log");
            addStrippedCompat("terrestria:willow_wood", "terrestria:stripped_willow_wood");
            // Redwood
            TE_REDWOOD_BARK = registerBark("te_redwood_bark", "terrestria:redwood_log", "terrestria:redwood_wood");
            addStrippedCompat("terrestria:redwood_log", "terrestria:stripped_redwood_log");
            addStrippedCompat("terrestria:redwood_wood", "terrestria:stripped_redwood_wood");
        }
        if (FabricLoader.getInstance().isModLoaded("bigglobe")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("bigglobe_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
        }
        if (FabricLoader.getInstance().isModLoaded("rocks")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("this_rocks_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
        }
        if (FabricLoader.getInstance().isModLoaded("meadow")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("meadow_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);

            ALPINE_BIRCH_BARK = registerBark("alpine_birch_bark", "meadow:alpine_birch_log", null);
            addStrippedCompat("meadow:alpine_birch_log", "minecraft:stripped_birch_log");
        }
        if (FabricLoader.getInstance().isModLoaded("beachparty")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("beachparty_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);

            BP_PALM_BARK = registerBark("bp_palm_bark", "beachparty:palm_log", "beachparty:palm_wood");
            addStrippedCompat("beachparty:palm_log", "beachparty:stripped_palm_log");
        }
    }

    private static Item registerBark(String name, String logId, @Nullable String woodId) {
        BarkItem barkItem = (BarkItem) ItemInit.register(name, new BarkItem(new Item.Settings(), null, null, 150));
        whenBlockRegistered(logId, barkItem::setLogBlock);
        if (woodId != null) {
            whenBlockRegistered(woodId, barkItem::setWoodBlock);
        }
        return barkItem;
    }

    private static void addStrippedCompat(String blockId, String strippedBlockId) {
        whenBlockRegistered(blockId, block -> whenBlockRegistered(strippedBlockId, strippedBlock -> BarkItem.STRIPPED_LOG_COMPAT.put(block, strippedBlock)));
    }

    private static void whenBlockRegistered(String id, Consumer<Block> action) {
        Identifier identifier = Identifier.of(id);
        if (Registries.BLOCK.containsId(identifier)) {
            action.accept(Registries.BLOCK.get(identifier));
        } else {
            PENDING_BLOCKS.computeIfAbsent(identifier, key -> new ArrayList<>()).add(action);
        }
    }
}
