package net.earlystage.item.material;

import java.util.EnumMap;
import java.util.Map;

import net.earlystage.EarlyStageMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class EarlyStageArmorMaterials {

    public static final TagKey<Item> REPAIRS_STEEL_ARMOR = TagKey.create(Registries.ITEM, EarlyStageMain.identifierOf("repairs_steel_armor"));
    public static final TagKey<Item> REPAIRS_DAMASCUS_STEEL_ARMOR = TagKey.create(Registries.ITEM, EarlyStageMain.identifierOf("repairs_damascus_steel_armor"));

    public static final ArmorMaterial STEEL = new ArmorMaterial(20, defense(2, 5, 7, 3, 5), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f, REPAIRS_STEEL_ARMOR, asset("steel"));

    public static final ArmorMaterial DAMASCUS_STEEL = new ArmorMaterial(20, defense(3, 6, 8, 3, 5), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f, REPAIRS_DAMASCUS_STEEL_ARMOR,
            asset("damascus_steel"));

    private static Map<ArmorType, Integer> defense(int boots, int leggings, int chestplate, int helmet, int body) {
        Map<ArmorType, Integer> map = new EnumMap<>(ArmorType.class);
        map.put(ArmorType.BOOTS, boots);
        map.put(ArmorType.LEGGINGS, leggings);
        map.put(ArmorType.CHESTPLATE, chestplate);
        map.put(ArmorType.HELMET, helmet);
        map.put(ArmorType.BODY, body);
        return map;
    }

    private static ResourceKey<EquipmentAsset> asset(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, EarlyStageMain.identifierOf(name));
    }

}
