package HxCKDMS.HxCDiseases;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class PacketShader implements IMessage {
    private boolean enableShade;
    private String shadeString;

    public PacketShader() {}

    public PacketShader(boolean enableShade) {
        this.enableShade = enableShade;
        this.shadeString = "";
    }

    public PacketShader(boolean enableShade, String shadeString) {
        this.enableShade = enableShade;
        this.shadeString = shadeString;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(enableShade);
        ByteBufUtils.writeUTF8String(byteBuf,shadeString);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        enableShade = byteBuf.readBoolean();
        shadeString = ByteBufUtils.readUTF8String(byteBuf);
    }

    public static class handler implements IMessageHandler<PacketShader, IMessage> {
        @Override
        public IMessage onMessage(PacketShader message, MessageContext ctx) {
            if (message.enableShade) {
                ClientShaderManager.setShader(new ResourceLocation(message.shadeString));
            }
            else {
                Minecraft.getMinecraft().entityRenderer.deactivateShader();
            }
            return null;
        }
    }
}
