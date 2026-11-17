package net.earlystage.network.packet;

import net.earlystage.EarlyStageMain;
import net.earlystage.data.SieveDropTemplate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.List;

public record SieveDropPacket(List<SieveDropTemplate> templates) implements CustomPacketPayload {

    public static final Type<SieveDropPacket> PACKET_ID = new Type<>(EarlyStageMain.identifierOf("sieve_drop_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SieveDropPacket> PACKET_CODEC = StreamCodec.ofMember(SieveDropPacket::write, SieveDropPacket::read);

    private static void write(SieveDropPacket payload, RegistryFriendlyByteBuf buf) {
        buf.writeInt(payload.templates().size());
        for (SieveDropTemplate template : payload.templates()) {
            buf.writeInt(BuiltInRegistries.ITEM.getId(template.getBlockItem()));
            buf.writeInt(template.getBlockDrops().size());
            for (int i = 0; i < template.getBlockDrops().size(); i++) {
                buf.writeInt(BuiltInRegistries.ITEM.getId(template.getBlockDrops().get(i)));
                buf.writeFloat(template.getDropChances().get(i));
                buf.writeInt(template.getRollCount().get(i));
            }
        }
    }

    private static SieveDropPacket read(RegistryFriendlyByteBuf buf) {
        int templateCount = buf.readInt();
        List<SieveDropTemplate> templates = new ArrayList<>();
        for (int t = 0; t < templateCount; t++) {
            Item blockItem = BuiltInRegistries.ITEM.byId(buf.readInt());
            int dropCount = buf.readInt();
            List<Item> drops = new ArrayList<>();
            List<Float> chances = new ArrayList<>();
            List<Integer> rolls = new ArrayList<>();
            for (int i = 0; i < dropCount; i++) {
                drops.add(BuiltInRegistries.ITEM.byId(buf.readInt()));
                chances.add(buf.readFloat());
                rolls.add(buf.readInt());
            }
            templates.add(new SieveDropTemplate(blockItem, drops, chances, rolls));
        }
        return new SieveDropPacket(templates);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}
