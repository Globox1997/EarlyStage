package net.earlystage.init;

import net.earlystage.EarlyStageMain;
import net.earlystage.item.BarkItem;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.oblivion.init.BlockInit;
import net.regions_unexplored.block.RuBlocks;

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
            // Rune
            RUNE_BARK = ItemInit.register("rune_bark", new BarkItem(new Item.Settings(), BlockInit.RUNE_WOOD_LOG, BlockInit.RUNE_WOOD, 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(BlockInit.RUNE_WOOD_LOG, BlockInit.STRIPPED_RUNE_WOOD_LOG);
            BarkItem.STRIPPED_LOG_COMPAT.put(BlockInit.RUNE_WOOD, BlockInit.STRIPPED_RUNE_WOOD);
            // Silver Birch
            SILVER_BIRCH_BARK = ItemInit.register("silver_birch_bark", new BarkItem(new Item.Settings(), BlockInit.SILVER_BIRCH_LOG, BlockInit.SILVER_BIRCH, 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(BlockInit.SILVER_BIRCH_LOG, BlockInit.STRIPPED_SILVER_BIRCH_LOG);
            BarkItem.STRIPPED_LOG_COMPAT.put(BlockInit.SILVER_BIRCH, BlockInit.STRIPPED_SILVER_BIRCH);
            // Iron
            IRON_WOOD_BARK = ItemInit.register("iron_wood_bark", new BarkItem(new Item.Settings(), BlockInit.IRON_WOOD_LOG, BlockInit.IRON_WOOD, 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(BlockInit.IRON_WOOD_LOG, BlockInit.STRIPPED_IRON_WOOD_LOG);
            BarkItem.STRIPPED_LOG_COMPAT.put(BlockInit.IRON_WOOD, BlockInit.STRIPPED_IRON_WOOD);
        }
        if (FabricLoader.getInstance().isModLoaded("natures_spirit")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("natures_spirit_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
            // Aspen
            ASPEN_BARK = ItemInit.register("aspen_bark", new BarkItem(new Item.Settings(), NSWoods.ASPEN.getLog(), NSWoods.ASPEN.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.ASPEN.getLog(), NSWoods.ASPEN.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.ASPEN.getWood(), NSWoods.ASPEN.getStrippedWood());
            // Cedar
            CEDAR_BARK = ItemInit.register("cedar_bark", new BarkItem(new Item.Settings(), NSWoods.CEDAR.getLog(), NSWoods.CEDAR.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.CEDAR.getLog(), NSWoods.CEDAR.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.CEDAR.getWood(), NSWoods.CEDAR.getStrippedWood());
            // Coconut
            COCONUT_BARK = ItemInit.register("coconut_bark", new BarkItem(new Item.Settings(), NSWoods.COCONUT.getLog(), NSWoods.COCONUT.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.COCONUT.getLog(), NSWoods.COCONUT.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.COCONUT.getWood(), NSWoods.COCONUT.getStrippedWood());
            // Cypress
            CYPRESS_BARK = ItemInit.register("cypress_bark", new BarkItem(new Item.Settings(), NSWoods.CYPRESS.getLog(), NSWoods.CYPRESS.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.CYPRESS.getLog(), NSWoods.CYPRESS.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.CYPRESS.getWood(), NSWoods.CYPRESS.getStrippedWood());
            // Fir
            FIR_BARK = ItemInit.register("fir_bark", new BarkItem(new Item.Settings(), NSWoods.FIR.getLog(), NSWoods.FIR.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.FIR.getLog(), NSWoods.FIR.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.FIR.getWood(), NSWoods.FIR.getStrippedWood());
            // Ghaf
            GHAF_BARK = ItemInit.register("ghaf_bark", new BarkItem(new Item.Settings(), NSWoods.GHAF.getLog(), NSWoods.GHAF.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.GHAF.getLog(), NSWoods.GHAF.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.GHAF.getWood(), NSWoods.GHAF.getStrippedWood());
            // Joshua
            JOSHUA_BARK = ItemInit.register("joshua_bark", new BarkItem(new Item.Settings(), NSWoods.JOSHUA.getLog(), NSWoods.JOSHUA.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.JOSHUA.getLog(), NSWoods.JOSHUA.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.JOSHUA.getWood(), NSWoods.JOSHUA.getStrippedWood());
            // Larch
            LARCH_BARK = ItemInit.register("larch_bark", new BarkItem(new Item.Settings(), NSWoods.LARCH.getLog(), NSWoods.LARCH.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.LARCH.getLog(), NSWoods.LARCH.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.LARCH.getWood(), NSWoods.LARCH.getStrippedWood());
            // Mahogany
            MAHOGANY_BARK = ItemInit.register("mahogany_bark", new BarkItem(new Item.Settings(), NSWoods.MAHOGANY.getLog(), NSWoods.MAHOGANY.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.MAHOGANY.getLog(), NSWoods.MAHOGANY.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.MAHOGANY.getWood(), NSWoods.MAHOGANY.getStrippedWood());
            // Maple
            MAPLE_BARK = ItemInit.register("maple_bark", new BarkItem(new Item.Settings(), NSWoods.MAPLE.getLog(), NSWoods.MAPLE.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.MAPLE.getLog(), NSWoods.MAPLE.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.MAPLE.getWood(), NSWoods.MAPLE.getStrippedWood());
            // Olive
            OLIVE_BARK = ItemInit.register("olive_bark", new BarkItem(new Item.Settings(), NSWoods.OLIVE.getLog(), NSWoods.OLIVE.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.OLIVE.getLog(), NSWoods.OLIVE.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.OLIVE.getWood(), NSWoods.OLIVE.getStrippedWood());
            // Palo
            PALO_BARK = ItemInit.register("palo_bark", new BarkItem(new Item.Settings(), NSWoods.PALO_VERDE.getLog(), NSWoods.PALO_VERDE.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.PALO_VERDE.getLog(), NSWoods.PALO_VERDE.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.PALO_VERDE.getWood(), NSWoods.PALO_VERDE.getStrippedWood());
            // Redwood
            REDWOOD_BARK = ItemInit.register("redwood_bark", new BarkItem(new Item.Settings(), NSWoods.REDWOOD.getLog(), NSWoods.REDWOOD.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.REDWOOD.getLog(), NSWoods.REDWOOD.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.REDWOOD.getWood(), NSWoods.REDWOOD.getStrippedWood());
            // Saxaul
            SAXAUL_BARK = ItemInit.register("saxaul_bark", new BarkItem(new Item.Settings(), NSWoods.SAXAUL.getLog(), NSWoods.SAXAUL.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.SAXAUL.getLog(), NSWoods.SAXAUL.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.SAXAUL.getWood(), NSWoods.SAXAUL.getStrippedWood());
            // Sugi
            SUGI_BARK = ItemInit.register("sugi_bark", new BarkItem(new Item.Settings(), NSWoods.SUGI.getLog(), NSWoods.SUGI.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.SUGI.getLog(), NSWoods.SUGI.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.SUGI.getWood(), NSWoods.SUGI.getStrippedWood());
            // Willow
            WILLOW_BARK = ItemInit.register("willow_bark", new BarkItem(new Item.Settings(), NSWoods.WILLOW.getLog(), NSWoods.WILLOW.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.WILLOW.getLog(), NSWoods.WILLOW.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.WILLOW.getWood(), NSWoods.WILLOW.getStrippedWood());
            // Wisteria
            WISTERIA_BARK = ItemInit.register("wisteria_bark", new BarkItem(new Item.Settings(), NSWoods.WISTERIA.getLog(), NSWoods.WISTERIA.getWood(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.WISTERIA.getLog(), NSWoods.WISTERIA.getStrippedLog());
            BarkItem.STRIPPED_LOG_COMPAT.put(NSWoods.WISTERIA.getWood(), NSWoods.WISTERIA.getStrippedWood());
        }
        if (FabricLoader.getInstance().isModLoaded("regions_unexplored")) {
            ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("regions_unexplored_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
            // Baobab
            BAOBAB_BARK = ItemInit.register("baobab_bark", new BarkItem(new Item.Settings(), RuBlocks.BAOBAB_LOG.get(), RuBlocks.BAOBAB_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.BAOBAB_LOG.get(), RuBlocks.STRIPPED_BAOBAB_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.BAOBAB_WOOD.get(), RuBlocks.STRIPPED_BAOBAB_WOOD.get());
            // Blackwood
            BLACKWOOD_BARK = ItemInit.register("blackwood_bark", new BarkItem(new Item.Settings(), RuBlocks.BLACKWOOD_LOG.get(), RuBlocks.BLACKWOOD_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.BLACKWOOD_LOG.get(), RuBlocks.STRIPPED_BLACKWOOD_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.BLACKWOOD_WOOD.get(), RuBlocks.STRIPPED_BLACKWOOD_WOOD.get());
            // Brimwood
            BRIMWOOD_BARK = ItemInit.register("brimwood_bark", new BarkItem(new Item.Settings(), RuBlocks.BRIMWOOD_LOG.get(), RuBlocks.BRIMWOOD_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.BRIMWOOD_LOG.get(), RuBlocks.STRIPPED_BRIMWOOD_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.BRIMWOOD_WOOD.get(), RuBlocks.STRIPPED_BRIMWOOD_WOOD.get());
            // Cobalt
            COBALT_BARK = ItemInit.register("cobalt_bark", new BarkItem(new Item.Settings(), RuBlocks.COBALT_LOG.get(), RuBlocks.COBALT_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.COBALT_LOG.get(), RuBlocks.STRIPPED_COBALT_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.COBALT_WOOD.get(), RuBlocks.STRIPPED_COBALT_WOOD.get());
            // Dead
            DEAD_BARK = ItemInit.register("dead_bark", new BarkItem(new Item.Settings(), RuBlocks.DEAD_LOG.get(), RuBlocks.DEAD_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.DEAD_LOG.get(), RuBlocks.STRIPPED_DEAD_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.DEAD_WOOD.get(), RuBlocks.STRIPPED_DEAD_WOOD.get());
            // Eucalyptus
            EUCALYPTUS_BARK = ItemInit.register("eucalyptus_bark", new BarkItem(new Item.Settings(), RuBlocks.EUCALYPTUS_LOG.get(), RuBlocks.EUCALYPTUS_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.EUCALYPTUS_LOG.get(), RuBlocks.STRIPPED_EUCALYPTUS_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.EUCALYPTUS_WOOD.get(), RuBlocks.STRIPPED_EUCALYPTUS_WOOD.get());
            // Kapok
            KAPOK_BARK = ItemInit.register("kapok_bark", new BarkItem(new Item.Settings(), RuBlocks.KAPOK_LOG.get(), RuBlocks.KAPOK_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.KAPOK_LOG.get(), RuBlocks.STRIPPED_KAPOK_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.KAPOK_WOOD.get(), RuBlocks.STRIPPED_KAPOK_WOOD.get());
            // Magnolia
            MAGNOLIA_BARK = ItemInit.register("magnolia_bark", new BarkItem(new Item.Settings(), RuBlocks.MAGNOLIA_LOG.get(), RuBlocks.MAGNOLIA_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.MAGNOLIA_LOG.get(), RuBlocks.STRIPPED_MAGNOLIA_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.MAGNOLIA_WOOD.get(), RuBlocks.STRIPPED_MAGNOLIA_WOOD.get());
            // Mauve
            MAUVE_BARK = ItemInit.register("mauve_bark", new BarkItem(new Item.Settings(), RuBlocks.MAUVE_LOG.get(), RuBlocks.MAUVE_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.MAUVE_LOG.get(), RuBlocks.STRIPPED_MAUVE_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.MAUVE_WOOD.get(), RuBlocks.STRIPPED_MAUVE_WOOD.get());
            // Palm
            PALM_BARK = ItemInit.register("palm_bark", new BarkItem(new Item.Settings(), RuBlocks.PALM_LOG.get(), RuBlocks.PALM_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.PALM_LOG.get(), RuBlocks.STRIPPED_PALM_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.PALM_WOOD.get(), RuBlocks.STRIPPED_PALM_WOOD.get());
            // Pine
            PINE_BARK = ItemInit.register("pine_bark", new BarkItem(new Item.Settings(), RuBlocks.PINE_LOG.get(), RuBlocks.PINE_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.PINE_LOG.get(), RuBlocks.STRIPPED_PINE_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.PINE_WOOD.get(), RuBlocks.STRIPPED_PINE_WOOD.get());
            // Small Oak
            SMALL_OAK_BARK = ItemInit.register("small_oak_bark", new BarkItem(new Item.Settings(), RuBlocks.SMALL_OAK_LOG.get(), null, 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.SMALL_OAK_LOG.get(), RuBlocks.STRIPPED_SMALL_OAK_LOG.get());
            // Socotra
            SOCOTRA_BARK = ItemInit.register("socotra_bark", new BarkItem(new Item.Settings(), RuBlocks.SOCOTRA_LOG.get(), RuBlocks.SOCOTRA_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.SOCOTRA_LOG.get(), RuBlocks.STRIPPED_SOCOTRA_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.SOCOTRA_WOOD.get(), RuBlocks.STRIPPED_SOCOTRA_WOOD.get());
            // Cypress
            RU_CYPRESS_BARK = ItemInit.register("ru_cypress_bark", new BarkItem(new Item.Settings(), RuBlocks.CYPRESS_LOG.get(), RuBlocks.CYPRESS_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.CYPRESS_LOG.get(), RuBlocks.STRIPPED_CYPRESS_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.CYPRESS_WOOD.get(), RuBlocks.STRIPPED_CYPRESS_WOOD.get());
            // Joshua
            RU_JOSHUA_BARK = ItemInit.register("ru_joshua_bark", new BarkItem(new Item.Settings(), RuBlocks.JOSHUA_LOG.get(), RuBlocks.JOSHUA_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.JOSHUA_LOG.get(), RuBlocks.STRIPPED_JOSHUA_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.JOSHUA_WOOD.get(), RuBlocks.STRIPPED_JOSHUA_WOOD.get());
            // Larch
            RU_LARCH_BARK = ItemInit.register("ru_larch_bark", new BarkItem(new Item.Settings(), RuBlocks.LARCH_LOG.get(), RuBlocks.LARCH_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.LARCH_LOG.get(), RuBlocks.STRIPPED_LARCH_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.LARCH_WOOD.get(), RuBlocks.STRIPPED_LARCH_WOOD.get());
            // Maple
            RU_MAPLE_BARK = ItemInit.register("ru_maple_bark", new BarkItem(new Item.Settings(), RuBlocks.MAPLE_LOG.get(), RuBlocks.MAPLE_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.MAPLE_LOG.get(), RuBlocks.STRIPPED_MAPLE_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.MAPLE_WOOD.get(), RuBlocks.STRIPPED_MAPLE_WOOD.get());
            // Redwood
            RU_REDWOOD_BARK = ItemInit.register("ru_redwood_bark", new BarkItem(new Item.Settings(), RuBlocks.REDWOOD_LOG.get(), RuBlocks.REDWOOD_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.REDWOOD_LOG.get(), RuBlocks.STRIPPED_REDWOOD_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.REDWOOD_WOOD.get(), RuBlocks.STRIPPED_REDWOOD_WOOD.get());
            // Willow
            RU_WILLOW_BARK = ItemInit.register("ru_willow_bark", new BarkItem(new Item.Settings(), RuBlocks.WILLOW_LOG.get(), RuBlocks.WILLOW_WOOD.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.WILLOW_LOG.get(), RuBlocks.STRIPPED_WILLOW_LOG.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.WILLOW_WOOD.get(), RuBlocks.STRIPPED_WILLOW_WOOD.get());

            // Blue Bioshroom
            BLUE_BIOSHROOM_BARK = ItemInit.register("blue_bioshroom_bark", new BarkItem(new Item.Settings(), RuBlocks.BLUE_BIOSHROOM_HYPHAE.get(), RuBlocks.BLUE_BIOSHROOM_STEM.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.BLUE_BIOSHROOM_HYPHAE.get(), RuBlocks.STRIPPED_BLUE_BIOSHROOM_HYPHAE.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.BLUE_BIOSHROOM_STEM.get(), RuBlocks.STRIPPED_BLUE_BIOSHROOM_STEM.get());
            // Green Bioshroom
            GREEN_BIOSHROOM_BARK = ItemInit.register("green_bioshroom_bark", new BarkItem(new Item.Settings(), RuBlocks.GREEN_BIOSHROOM_HYPHAE.get(), RuBlocks.GREEN_BIOSHROOM_STEM.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.GREEN_BIOSHROOM_HYPHAE.get(), RuBlocks.STRIPPED_GREEN_BIOSHROOM_HYPHAE.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.GREEN_BIOSHROOM_STEM.get(), RuBlocks.STRIPPED_GREEN_BIOSHROOM_STEM.get());
            // Pink Bioshroom
            PINK_BIOSHROOM_BARK = ItemInit.register("pink_bioshroom_bark", new BarkItem(new Item.Settings(), RuBlocks.PINK_BIOSHROOM_HYPHAE.get(), RuBlocks.PINK_BIOSHROOM_STEM.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.PINK_BIOSHROOM_HYPHAE.get(), RuBlocks.STRIPPED_PINK_BIOSHROOM_HYPHAE.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.PINK_BIOSHROOM_STEM.get(), RuBlocks.STRIPPED_PINK_BIOSHROOM_STEM.get());
            // Yellow Bioshroom
            YELLOW_BIOSHROOM_BARK = ItemInit.register("yellow_bioshroom_bark", new BarkItem(new Item.Settings(), RuBlocks.YELLOW_BIOSHROOM_HYPHAE.get(), RuBlocks.YELLOW_BIOSHROOM_STEM.get(), 150));
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.YELLOW_BIOSHROOM_HYPHAE.get(), RuBlocks.STRIPPED_YELLOW_BIOSHROOM_HYPHAE.get());
            BarkItem.STRIPPED_LOG_COMPAT.put(RuBlocks.YELLOW_BIOSHROOM_STEM.get(), RuBlocks.STRIPPED_YELLOW_BIOSHROOM_STEM.get());
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
    }
}
