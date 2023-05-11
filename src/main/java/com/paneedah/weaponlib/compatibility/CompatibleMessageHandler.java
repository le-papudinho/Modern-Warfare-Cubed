package com.paneedah.weaponlib.compatibility;

import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface CompatibleMessageHandler<Request extends IMessage, Response extends IMessage> extends IMessageHandler<Request, Response> {
	
	@Override
	default Response onMessage(Request message, MessageContext messageContext) {
		return onCompatibleMessage(message, messageContext);
	}

	<T extends IMessage> T onCompatibleMessage(Request message, MessageContext messageContext);
}
