package org.cubewhy.lunarcn.gui.altmanager;

import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class GuiAltManager extends GuiScreen {

    private final GuiScreen base;

    public GuiAltManager(GuiScreen base) {
        this.base = base;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "In dev", this.width / 2, 10, new Color(255, 255, 255).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
