package dupemate.util.mixin.client;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMessageMixin {
    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo ci) {
        if (message.equals(".unloadchunk")) {
            ClientPlayNetworkHandler handler = (ClientPlayNetworkHandler) (Object) this;
            handler.getConnection().disconnect(Text.literal("chunk debounced with 405"));
            ci.cancel();
        }
    }
}
