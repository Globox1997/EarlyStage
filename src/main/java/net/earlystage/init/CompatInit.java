package net.earlystage.init;

import net.earlystage.EarlyStageMain;
import net.earlystage.item.BarkItem;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

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

    public static void init() {
        if (FabricLoader.getInstance().isModLoaded("oblivion")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("oblivion_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
            // Rune Wood
            RUNE_BARK = ItemInit.register("rune_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("oblivion:rune_wood_log")), Registries.BLOCK.get(Identifier.of("oblivion:rune_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("oblivion:rune_wood_log")), Registries.BLOCK.get(Identifier.of("oblivion:stripped_rune_wood_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("oblivion:rune_wood")), Registries.BLOCK.get(Identifier.of("oblivion:stripped_rune_wood")));

            // Silver Birch
            SILVER_BIRCH_BARK = ItemInit.register("silver_birch_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("oblivion:silver_birch_log")), Registries.BLOCK.get(Identifier.of("oblivion:silver_birch")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("oblivion:silver_birch_log")), Registries.BLOCK.get(Identifier.of("oblivion:stripped_silver_birch_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("oblivion:silver_birch")), Registries.BLOCK.get(Identifier.of("oblivion:stripped_silver_birch")));

            // Iron Wood
            IRON_WOOD_BARK = ItemInit.register("iron_wood_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("oblivion:iron_wood_log")), Registries.BLOCK.get(Identifier.of("oblivion:iron_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("oblivion:iron_wood_log")), Registries.BLOCK.get(Identifier.of("oblivion:stripped_iron_wood_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("oblivion:iron_wood")), Registries.BLOCK.get(Identifier.of("oblivion:stripped_iron_wood")));
        }
        if (FabricLoader.getInstance().isModLoaded("natures_spirit")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("natures_spirit_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
            // Aspen
            ASPEN_BARK = ItemInit.register("aspen_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:aspen_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:aspen_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:aspen_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_aspen_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:aspen_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_aspen_wood")));

            // Cedar
            CEDAR_BARK = ItemInit.register("cedar_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:cedar_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:cedar_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:cedar_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_cedar_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:cedar_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_cedar_wood")));

            // Coconut
            COCONUT_BARK = ItemInit.register("coconut_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:coconut_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:coconut_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:coconut_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_coconut_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:coconut_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_coconut_wood")));

            // Cypress
            CYPRESS_BARK = ItemInit.register("cypress_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:cypress_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:cypress_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:cypress_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_cypress_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:cypress_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_cypress_wood")));

            // Fir
            FIR_BARK = ItemInit.register("fir_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:fir_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:fir_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:fir_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_fir_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:fir_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_fir_wood")));

            // Ghaf
            GHAF_BARK = ItemInit.register("ghaf_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:ghaf_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:ghaf_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:ghaf_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_ghaf_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:ghaf_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_ghaf_wood")));

            // Joshua
            JOSHUA_BARK = ItemInit.register("joshua_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:joshua_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:joshua_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:joshua_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_joshua_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:joshua_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_joshua_wood")));

            // Larch
            LARCH_BARK = ItemInit.register("larch_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:larch_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:larch_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:larch_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_larch_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:larch_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_larch_wood")));

            // Mahogany
            MAHOGANY_BARK = ItemInit.register("mahogany_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:mahogany_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:mahogany_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:mahogany_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_mahogany_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:mahogany_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_mahogany_wood")));

            // Maple
            MAPLE_BARK = ItemInit.register("maple_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:maple_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:maple_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:maple_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_maple_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:maple_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_maple_wood")));

            // Olive
            OLIVE_BARK = ItemInit.register("olive_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:olive_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:olive_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:olive_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_olive_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:olive_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_olive_wood")));

            // Palo Verde
            PALO_BARK = ItemInit.register("palo_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:palo_verde_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:palo_verde_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:palo_verde_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_palo_verde_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:palo_verde_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_palo_verde_wood")));

            // Redwood
            REDWOOD_BARK = ItemInit.register("redwood_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:redwood_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:redwood_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:redwood_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_redwood_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:redwood_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_redwood_wood")));

            // Saxaul
            SAXAUL_BARK = ItemInit.register("saxaul_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:saxaul_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:saxaul_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:saxaul_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_saxaul_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:saxaul_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_saxaul_wood")));

            // Sugi
            SUGI_BARK = ItemInit.register("sugi_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:sugi_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:sugi_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:sugi_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_sugi_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:sugi_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_sugi_wood")));

            // Willow
            WILLOW_BARK = ItemInit.register("willow_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:willow_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:willow_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:willow_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_willow_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:willow_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_willow_wood")));

            // Wisteria
            WISTERIA_BARK = ItemInit.register("wisteria_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("natures_spirit:wisteria_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:wisteria_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:wisteria_log")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_wisteria_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("natures_spirit:wisteria_wood")), Registries.BLOCK.get(Identifier.of("natures_spirit:stripped_wisteria_wood")));
        }
        if (FabricLoader.getInstance().isModLoaded("regions_unexplored")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("regions_unexplored_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);

            // Baobab
            BAOBAB_BARK = ItemInit.register("baobab_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:baobab_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:baobab_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:baobab_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_baobab_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:baobab_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_baobab_wood")));

            // Blackwood
            BLACKWOOD_BARK = ItemInit.register("blackwood_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:blackwood_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:blackwood_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:blackwood_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_blackwood_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:blackwood_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_blackwood_wood")));

            // Brimwood
            BRIMWOOD_BARK = ItemInit.register("brimwood_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:brimwood_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:brimwood_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:brimwood_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_brimwood_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:brimwood_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_brimwood_wood")));

            // Cobalt
            COBALT_BARK = ItemInit.register("cobalt_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:cobalt_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:cobalt_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:cobalt_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_cobalt_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:cobalt_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_cobalt_wood")));

            // Dead
            DEAD_BARK = ItemInit.register("dead_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:dead_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:dead_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:dead_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_dead_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:dead_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_dead_wood")));

            // Eucalyptus
            EUCALYPTUS_BARK = ItemInit.register("eucalyptus_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:eucalyptus_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:eucalyptus_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:eucalyptus_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_eucalyptus_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:eucalyptus_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_eucalyptus_wood")));

            // Kapok
            KAPOK_BARK = ItemInit.register("kapok_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:kapok_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:kapok_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:kapok_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_kapok_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:kapok_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_kapok_wood")));

            // Magnolia
            MAGNOLIA_BARK = ItemInit.register("magnolia_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:magnolia_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:magnolia_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:magnolia_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_magnolia_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:magnolia_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_magnolia_wood")));

            // Mauve
            MAUVE_BARK = ItemInit.register("mauve_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:mauve_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:mauve_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:mauve_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_mauve_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:mauve_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_mauve_wood")));

            // Palm
            PALM_BARK = ItemInit.register("palm_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:palm_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:palm_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:palm_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_palm_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:palm_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_palm_wood")));

            // Pine
            PINE_BARK = ItemInit.register("pine_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:pine_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:pine_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:pine_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_pine_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:pine_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_pine_wood")));

            // Small Oak
            SMALL_OAK_BARK = ItemInit.register("small_oak_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:small_oak_log")), null, 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:small_oak_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_small_oak_log")));

            // Socotra
            SOCOTRA_BARK = ItemInit.register("socotra_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:socotra_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:socotra_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:socotra_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_socotra_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:socotra_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_socotra_wood")));

            // Cypress
            RU_CYPRESS_BARK = ItemInit.register("ru_cypress_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:cypress_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:cypress_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:cypress_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_cypress_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:cypress_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_cypress_wood")));

            // Joshua
            RU_JOSHUA_BARK = ItemInit.register("ru_joshua_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:joshua_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:joshua_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:joshua_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_joshua_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:joshua_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_joshua_wood")));

            // Larch
            RU_LARCH_BARK = ItemInit.register("ru_larch_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:larch_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:larch_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:larch_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_larch_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:larch_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_larch_wood")));

            // Maple
            RU_MAPLE_BARK = ItemInit.register("ru_maple_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:maple_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:maple_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:maple_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_maple_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:maple_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_maple_wood")));

            // Redwood
            RU_REDWOOD_BARK = ItemInit.register("ru_redwood_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:redwood_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:redwood_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:redwood_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_redwood_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:redwood_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_redwood_wood")));

            // Willow
            RU_WILLOW_BARK = ItemInit.register("ru_willow_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:willow_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:willow_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:willow_log")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_willow_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:willow_wood")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_willow_wood")));

            // Blue Bioshroom
            BLUE_BIOSHROOM_BARK = ItemInit.register("blue_bioshroom_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:blue_bioshroom_hyphae")), Registries.BLOCK.get(Identifier.of("regions_unexplored:blue_bioshroom_stem")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:blue_bioshroom_hyphae")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_blue_bioshroom_hyphae")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:blue_bioshroom_stem")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_blue_bioshroom_stem")));

            // Green Bioshroom
            GREEN_BIOSHROOM_BARK = ItemInit.register("green_bioshroom_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:green_bioshroom_hyphae")), Registries.BLOCK.get(Identifier.of("regions_unexplored:green_bioshroom_stem")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:green_bioshroom_hyphae")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_green_bioshroom_hyphae")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:green_bioshroom_stem")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_green_bioshroom_stem")));

            // Pink Bioshroom
            PINK_BIOSHROOM_BARK = ItemInit.register("pink_bioshroom_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:pink_bioshroom_hyphae")), Registries.BLOCK.get(Identifier.of("regions_unexplored:pink_bioshroom_stem")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:pink_bioshroom_hyphae")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_pink_bioshroom_hyphae")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:pink_bioshroom_stem")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_pink_bioshroom_stem")));

            // Yellow Bioshroom
            YELLOW_BIOSHROOM_BARK = ItemInit.register("yellow_bioshroom_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("regions_unexplored:yellow_bioshroom_hyphae")), Registries.BLOCK.get(Identifier.of("regions_unexplored:yellow_bioshroom_stem")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:yellow_bioshroom_hyphae")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_yellow_bioshroom_hyphae")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("regions_unexplored:yellow_bioshroom_stem")), Registries.BLOCK.get(Identifier.of("regions_unexplored:stripped_yellow_bioshroom_stem")));
        }
        if (FabricLoader.getInstance().isModLoaded("terrestria")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("terrestria_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
            // Hemlock
            HEMLOCK_BARK = ItemInit.register("hemlock_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("terrestria:hemlock_log")), Registries.BLOCK.get(Identifier.of("terrestria:hemlock_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:hemlock_log")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_hemlock_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:hemlock_wood")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_hemlock_wood")));
            // Yucca Palm
            YUCCA_PALM_BARK = ItemInit.register("yucca_palm_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("terrestria:yucca_palm_log")), null, 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:yucca_palm_log")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_yucca_palm_log")));
            // Sakura
            SAKURA_BARK = ItemInit.register("sakura_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("terrestria:sakura_log")), null, 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:sakura_log")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_sakura_log")));
            // Rubber
            RUBBER_BARK = ItemInit.register("rubber_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("terrestria:rubber_log")), Registries.BLOCK.get(Identifier.of("terrestria:rubber_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:rubber_log")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_rubber_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:rubber_wood")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_rubber_wood")));
            // Rainbow Eucalyptus
            RAINBOW_EUCALYPTUS_BARK = ItemInit.register("rainbow_eucalyptus_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("terrestria:rainbow_eucalyptus_log")), Registries.BLOCK.get(Identifier.of("terrestria:rainbow_eucalyptus_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:rainbow_eucalyptus_log")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_rainbow_eucalyptus_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:rainbow_eucalyptus_wood")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_rainbow_eucalyptus_wood")));
            // Japanese Maple
            JAPANESE_MAPLE_BARK = ItemInit.register("japanese_maple_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("terrestria:japanese_maple_log")), Registries.BLOCK.get(Identifier.of("terrestria:japanese_maple_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:japanese_maple_log")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_japanese_maple_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:japanese_maple_wood")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_japanese_maple_wood")));
            // Cypress
            TE_CYPRESS_BARK = ItemInit.register("te_cypress_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("terrestria:cypress_log")), Registries.BLOCK.get(Identifier.of("terrestria:cypress_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:cypress_log")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_cypress_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:cypress_wood")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_cypress_wood")));
            // Willow
            TE_WILLOW_BARK = ItemInit.register("te_willow_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("terrestria:willow_log")), Registries.BLOCK.get(Identifier.of("terrestria:willow_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:willow_log")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_willow_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:willow_wood")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_willow_wood")));
            // Redwood
            TE_REDWOOD_BARK = ItemInit.register("te_redwood_bark", new BarkItem(new Item.Settings(), Registries.BLOCK.get(Identifier.of("terrestria:redwood_log")), Registries.BLOCK.get(Identifier.of("terrestria:redwood_wood")), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:redwood_log")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_redwood_log")));
            BarkItem.STRIPPED_LOG_COMPAT.put(Registries.BLOCK.get(Identifier.of("terrestria:redwood_wood")), Registries.BLOCK.get(Identifier.of("terrestria:stripped_redwood_wood")));
        }
        if (FabricLoader.getInstance().isModLoaded("bigglobe")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("bigglobe_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
        }
        if (FabricLoader.getInstance().isModLoaded("rocks")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("this_rocks_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
        }
    }
}
