package net.earlystage.network.packet;

import net.earlystage.EarlyStageMain;
import net.earlystage.data.SieveDropTemplate;
import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;

public record SieveDropPacket(List<SieveDropTemplate> templates) implements CustomPayload {

    public static final Id<SieveDropPacket> PACKET_ID = new Id<>(EarlyStageMain.identifierOf("sieve_drop_packet"));

    public static final PacketCodec<RegistryByteBuf, SieveDropPacket> PACKET_CODEC = PacketCodec.of(SieveDropPacket::write, SieveDropPacket::read);

    private static void write(SieveDropPacket payload, RegistryByteBuf buf) {
        buf.writeInt(payload.templates().size());
        for (SieveDropTemplate template : payload.templates()) {
            buf.writeInt(Registries.ITEM.getRawId(template.getBlockItem()));
            buf.writeInt(template.getBlockDrops().size());
            for (int i = 0; i < template.getBlockDrops().size(); i++) {
                buf.writeInt(Registries.ITEM.getRawId(template.getBlockDrops().get(i)));
                buf.writeFloat(template.getDropChances().get(i));
                buf.writeInt(template.getRollCount().get(i));
            }
        }
    }

    private static SieveDropPacket read(RegistryByteBuf buf) {
        int templateCount = buf.readInt();
        List<SieveDropTemplate> templates = new ArrayList<>();
        for (int t = 0; t < templateCount; t++) {
            Item blockItem = Registries.ITEM.get(buf.readInt());
            int dropCount = buf.readInt();
            List<Item> drops = new ArrayList<>();
            List<Float> chances = new ArrayList<>();
            List<Integer> rolls = new ArrayList<>();
            for (int i = 0; i < dropCount; i++) {
                drops.add(Registries.ITEM.get(buf.readInt()));
                chances.add(buf.readFloat());
                rolls.add(buf.readInt());
            }
            templates.add(new SieveDropTemplate(blockItem, drops, chances, rolls));
        }
        return new SieveDropPacket(templates);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
