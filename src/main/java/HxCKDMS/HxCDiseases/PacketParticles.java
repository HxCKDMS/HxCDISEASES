package HxCKDMS.HxCDiseases;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PacketParticles implements IMessage {
    private int type = -1;
    private String name = "";
    private String diseases;

    public PacketParticles() {}

    public PacketParticles(int id, String playerName, String diseases) {
        this.type = id;
        this.name = playerName;
        this.diseases = diseases;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(type);
        ByteBufUtils.writeUTF8String(byteBuf, name);
        ByteBufUtils.writeUTF8String(byteBuf, diseases);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        type = byteBuf.readInt();
        name = ByteBufUtils.readUTF8String(byteBuf);
        diseases = ByteBufUtils.readUTF8String(byteBuf);
    }

    public static class handler implements IMessageHandler<PacketParticles, IMessage> {
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(PacketParticles message, MessageContext ctx) {
            EntityPlayer p = null;
            if (!message.name.isEmpty()) {
                p = Minecraft.getMinecraft().thePlayer.worldObj.getPlayerEntityByName(message.name);
            }
            if (message.type == 1) {
                if(p != null) {
                    Utilities.SpawnVomit(p, message.diseases);
                }
            }else if (message.type == 2) {
                if(p != null) {
                    Utilities.SpawnDiarrhea(p, message.diseases);
                }
            }
            return null;
        }
    }
}
