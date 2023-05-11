package com.paneedah.weaponlib;

import com.paneedah.weaponlib.compatibility.CompatibleExposureCapability;
import com.paneedah.weaponlib.compatibility.IMessage;
import com.paneedah.weaponlib.compatibility.CompatibleMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import static com.paneedah.weaponlib.compatibility.CompatibilityProvider.compatibility;

public class ExposureMessageHandler implements CompatibleMessageHandler<ExposureMessage, IMessage>  {
    
    @SuppressWarnings("unused")
    private ModContext modContext;

    public ExposureMessageHandler(ModContext modContext) {
        this.modContext = modContext;
    }

    @Override
    public <T extends IMessage> T onCompatibleMessage(ExposureMessage message, MessageContext messageContext) {
        if(messageContext.side == Side.CLIENT) {
            compatibility.runInMainClientThread(() -> {
                CompatibleExposureCapability.updateExposures(compatibility.clientPlayer(), message.getExposures());
            });
        }
        return null;
    }
}
