package dev.jumpingpxl.addons.chattime.core.settings.widgets.style;

import dev.jumpingpxl.addons.chattime.core.ChatTime;
import dev.jumpingpxl.addons.chattime.core.settings.WidgetOverlayService;
import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeFormat;
import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeStyle;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.inject.LabyGuice;

@AutoWidget
@SettingWidget
public class StyleSettingWidget extends DivWidget {

  private final ChatTime chatTime;
  private final ChatTimeStyle style;
  private final WidgetOverlayService overlayService;

  private StyleSettingWidget(ChatTimeStyle style) {
    this.style = style;

    this.chatTime = LabyGuice.getInstance(ChatTime.class);
    this.overlayService = WidgetOverlayService.create(this, () -> {
      return new StyleOverlayActivity(this);
    });

  }

  @Override
  public void render(Stack stack, MutableMouse mouse, float partialTicks) {
    super.render(stack, mouse, partialTicks);

    Bounds bounds = this.getBounds();
    String preview = this.style.realTimeValue(this.chatTime.configuration().formatting());
    TextRenderer textRenderer = Laby.getLabyAPI().renderPipeline().getTextRenderer();
    textRenderer.text(preview)
        .pos(bounds.getCenterX(BoundsType.INNER), bounds.getY(BoundsType.INNER))
        .centered(true).shadow(false).render(stack);
  }

  @Override
  public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
    return super.mouseClicked(mouse, mouseButton) || (this.isHovered()
        && this.overlayService.press());
  }

  @Override
  public void dispose() {
    if (Objects.nonNull(this.overlayService)) {
      this.overlayService.dispose();
    }

    super.dispose();
  }

  @SettingElement
  @Target({ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface StyleSetting {

  }

  @SettingFactory
  public static class Factory implements WidgetFactory<StyleSetting, StyleSettingWidget> {

    @Override
    public StyleSettingWidget create(Setting setting, StyleSetting annotation,
        SettingInfo<?> info, SettingAccessor accessor) {
      return new StyleSettingWidget(accessor.get());
    }

    @Override
    public Class<?>[] types() {
      return new Class[]{ChatTimeStyle.class, ChatTimeFormat.class};
    }
  }
}
