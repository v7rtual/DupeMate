package dupemate.util;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public class ContainerInfoHud implements HudRenderCallback {
    
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        if (!ContainerInfoTracker.isActive()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        TextRenderer textRenderer = client.textRenderer;
        int x = 5;
        int y = 5;
        int lineHeight = 10;
        int backgroundColor = 0x80000000; // Semi-transparent black
        int textColor = 0xFFFFFFFF; // White with full opacity

        BlockPos pos = ContainerInfoTracker.getPosition();
        String type = ContainerInfoTracker.getType();
        String id = ContainerInfoTracker.getId();
        int itemCount = ContainerInfoTracker.getItemCount();

        // Create text components
        Text[] lines = new Text[4];
        lines[0] = Text.literal(type).formatted(Formatting.YELLOW, Formatting.BOLD);
        lines[1] = Text.literal("Location: ").formatted(Formatting.GRAY)
                .append(Text.literal(pos != null ? pos.getX() + ", " + pos.getY() + ", " + pos.getZ() : "Unknown").formatted(Formatting.WHITE));
        lines[2] = Text.literal("ID: ").formatted(Formatting.GRAY)
                .append(Text.literal(id).formatted(Formatting.WHITE));
        lines[3] = Text.literal("Items: ").formatted(Formatting.GRAY)
                .append(Text.literal(String.valueOf(itemCount)).formatted(Formatting.WHITE));

        // Calculate background size
        int maxWidth = 0;
        for (Text line : lines) {
            int width = textRenderer.getWidth(line);
            if (width > maxWidth) {
                maxWidth = width;
            }
        }

        drawContext.fill(x - 2, y - 2, x + maxWidth + 2, y + (lineHeight * lines.length) + 2, backgroundColor);

        // Draw text
        for (int i = 0; i < lines.length; i++) {
            drawContext.drawText(textRenderer, lines[i], x, y + (i * lineHeight), textColor, false);
        }
    }
}
