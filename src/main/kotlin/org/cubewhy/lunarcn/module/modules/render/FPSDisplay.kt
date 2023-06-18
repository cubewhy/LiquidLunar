package org.cubewhy.lunarcn.module.modules.render

import net.minecraft.client.Minecraft
import org.cubewhy.lunarcn.gui.hud.ScreenPosition
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleDraggable
import org.cubewhy.lunarcn.module.ModuleInfo
import java.awt.Color

@ModuleInfo(name = "FPSDisplay", document = "Display game fps", category = ModuleCategory.RENDER)
class FPSDisplay : ModuleDraggable() {
    var position: ScreenPosition? = null

    override fun save(position: ScreenPosition) {
        this.position = position
    }

    override fun load(): ScreenPosition {
        if (this.position == null) {
            this.position = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }
        return this.position!!
    }

    override fun getWidth(): Int {
        return 50
    }

    override fun getHeight(): Int {
        return fontRenderer.FONT_HEIGHT
    }

    override fun render(position: ScreenPosition) {
        fontRenderer.drawString("FPS: " + Minecraft.getDebugFPS(), position.absoluteX, position.absoluteY, Color(255, 255, 255).rgb)
    }
}