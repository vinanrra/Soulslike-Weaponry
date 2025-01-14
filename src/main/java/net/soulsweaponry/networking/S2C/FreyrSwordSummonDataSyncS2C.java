package net.soulsweaponry.networking.S2C;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.soulsweaponry.entitydata.FreyrSwordSummonData;
import net.soulsweaponry.entitydata.IEntityDataSaver;

public class FreyrSwordSummonDataSyncS2C {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (client.player != null) {
            ((IEntityDataSaver)client.player).getPersistentData().putUuid(FreyrSwordSummonData.SUMMON_ID, buf.readUuid());
        }
    }
}