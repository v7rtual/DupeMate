package dupemate.util.mixin.client;

import dupemate.util.ContainerInfoTracker;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {
    @Shadow @Final protected ScreenHandler handler;
    @Shadow protected int backgroundWidth;
    @Shadow protected int backgroundHeight;

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();
        
        if (client.player == null || client.world == null) {
            return;
        }

        String type = null;
        String id = "container_" + this.handler.syncId;
        
        // Detect container type
        if (screen instanceof GenericContainerScreen) {
            type = "Chest";
        } else if (screen instanceof ShulkerBoxScreen) {
            type = "Shulker Box";
        }
        
        if (type != null) {
            // Count items in the container
            int itemCount = 0;
            int containerSize = 0;
            
            if (this.handler instanceof GenericContainerScreenHandler genericHandler) {
                containerSize = genericHandler.getRows() * 9;
            } else if (this.handler instanceof ShulkerBoxScreenHandler) {
                containerSize = 27;
            }
            
            for (int i = 0; i < containerSize && i < this.handler.slots.size(); i++) {
                var stack = this.handler.slots.get(i).getStack();
                if (!stack.isEmpty()) {
                    itemCount += stack.getCount();
                }
            }
            
            // Use approximate position based on player's view
            var pos = client.player.getBlockPos().offset(client.player.getHorizontalFacing(), 2);
            
            // Try to find the actual container block entity nearby
            if (client.world != null) {
                BlockPos actualPos = null;
                for (int dx = -3; dx <= 3; dx++) {
                    for (int dy = -2; dy <= 2; dy++) {
                        for (int dz = -3; dz <= 3; dz++) {
                            BlockPos checkPos = client.player.getBlockPos().add(dx, dy, dz);
                            BlockEntity blockEntity = client.world.getBlockEntity(checkPos);
                            if (blockEntity instanceof ChestBlockEntity || blockEntity instanceof ShulkerBoxBlockEntity) {
                                actualPos = checkPos;
                                if (blockEntity instanceof ChestBlockEntity) {
                                    type = "Chest";
                                    id = "chest_" + checkPos.toShortString();
                                } else {
                                    type = "Shulker Box";
                                    id = "shulker_" + checkPos.toShortString();
                                }
                                pos = actualPos;
                                break;
                            }
                        }
                        if (actualPos != null) break;
                    }
                    if (actualPos != null) break;
                }
            }
            
            ContainerInfoTracker.setContainerInfo(pos, id, itemCount, type);
        }
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void onClose(CallbackInfo ci) {
        ContainerInfoTracker.clear();
    }
}
