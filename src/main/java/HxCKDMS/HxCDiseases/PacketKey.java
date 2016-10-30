package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCCore.HxCCore;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketKey implements IMessage {
    private int keyid = -1;
    private String name = "";

    public PacketKey() {}

    public PacketKey(int id, String playerName) {
        this.keyid = id;
        this.name = playerName;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(keyid);
        ByteBufUtils.writeUTF8String(byteBuf, name);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        keyid = byteBuf.readInt();
        name = ByteBufUtils.readUTF8String(byteBuf);
    }

    public static class handler implements IMessageHandler<PacketKey, IMessage> {
        @Override
        public IMessage onMessage(PacketKey message, MessageContext ctx) {
            EntityPlayer p = null;
            if (!message.name.isEmpty()) {
                p = HxCCore.server.getEntityWorld().getPlayerEntityByName(message.name);
            }
            if (message.keyid == 1) {
                if(DiseaseConfig.itemVomit && p != null) {
                    Utilities.playSoundAtPlayer(p, "hxcdiseases:vomit", 3, 1 + ((p.worldObj.rand.nextFloat() - 0.5f) / 5));
                }
            }else if (message.keyid == 2) {
                if(DiseaseConfig.farts && p != null) {
                    float pitch = (90-p.rotationPitch)/90;
                    Utilities.playSoundAtPlayer(p, "hxcdiseases:fart", 3, pitch);
                }
            }
            return null;
        }
    }
}
