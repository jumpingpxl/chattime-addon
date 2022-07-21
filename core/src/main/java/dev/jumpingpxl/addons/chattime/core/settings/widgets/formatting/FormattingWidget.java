package dev.jumpingpxl.addons.chattime.core.settings.widgets.formatting;

import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeFormat;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;

@AutoWidget
@SettingWidget
public class FormattingWidget extends DivWidget {

  @SettingElement
  @Target({ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface FormattingSetting {

  }

  public static class Factory implements WidgetFactory<FormattingSetting, FormattingWidget> {

    @Override
    public FormattingWidget create(Setting setting, FormattingSetting annotation,
        SettingInfo<?> info, SettingAccessor accessor) {
      return WidgetFactory.super.create(setting, annotation, info, accessor);
    }

    @Override
    public Class<?>[] types() {
      return new Class[]{ChatTimeFormat.class};
    }
  }
}
