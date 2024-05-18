package net.soulsweaponry.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.client.entitydata.ClientPostureData;
import net.soulsweaponry.config.CommonConfig;

public class PostureHudOverlay {

    private static final Identifier FILLED_BAR = new Identifier(SoulsWeaponry.ModId, "textures/gui/posture/full.png");
    private static final Identifier EMPTY_BAR = new Identifier(SoulsWeaponry.ModId, "textures/gui/posture/empty.png");
    private static final Identifier ICON = new Identifier(SoulsWeaponry.ModId, "textures/gui/posture/icon.png");

    public static final IIngameOverlay HUD_POSTURE = ((gui, matrix, partialTicks, width, height) -> {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int x = width / 2;
            int y = height;
            if (client.player != null && !client.player.isDead()) {
                int posture = ClientPostureData.getPosture();
                float posturePerPixel = (float) CommonConfig.MAX_POSTURE_LOSS.get() / (float) 182;
                int posturePixel = MathHelper.floor((float) posture / posturePerPixel);
                if (posture > 0) {
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

                    RenderSystem.setShaderTexture(0, ICON);
                    DrawableHelper.drawTexture(matrix, x - 140, y - 90, 0, 0, 25, 25, 25 ,25);
                    RenderSystem.setShaderTexture(0, EMPTY_BAR);
                    DrawableHelper.drawTexture(matrix, x - 140 + 25, y - 90 + 10, 0, 0, 182, 5, 182, 5);
                    RenderSystem.setShaderTexture(0, FILLED_BAR);
                    DrawableHelper.drawTexture(matrix, x - 140 + 25, y - 90 + 10, 0, 0, posturePixel, 5, 182, 5);
                }
            }
        }
    });
}
