package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCDiseases.GUI.GuiDiagnosis;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PacketGui implements IMessage {
    private String info = "";
    private int gui = 0;

    public PacketGui() {}

    public PacketGui(int gui) {
        this.gui = gui;
    }

    public PacketGui(int gui, String info) {
        this.gui = gui;
        this.info = info;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(gui);
        ByteBufUtils.writeUTF8String(byteBuf,info);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        gui = byteBuf.readInt();
        info = ByteBufUtils.readUTF8String(byteBuf);
    }

    public static class handler implements IMessageHandler<PacketGui, IMessage> {
        @Override
        public IMessage onMessage(PacketGui message, MessageContext ctx) {
            if (message.gui == 0) {
                EntityPlayer p = Minecraft.getMinecraft().thePlayer;
                FMLClientHandler.instance().displayGuiScreen(p, new GuiDiagnosis(message.info));
            }
            return null;
        }
    }
}
