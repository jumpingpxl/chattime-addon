package dev.jumpingpxl.addons.chattime.core;

import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeFormat;
import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeStyle;
import dev.jumpingpxl.addons.chattime.core.settings.widgets.formatting.FormattingWidget.FormattingSetting;
import dev.jumpingpxl.addons.chattime.core.settings.widgets.style.StyleSettingWidget.StyleSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ConfigName;

@SuppressWarnings("FieldMayBeFinal")
@ConfigName("settings")
public class ChatTimeConfiguration extends Config {

  @SwitchSetting
  private boolean enabled = true;

  @StyleSetting
  private ChatTimeStyle style = ChatTimeStyle.of("&e┃ &6%time% &8» &r");

  @FormattingSetting
  private ChatTimeFormat format = ChatTimeFormat.of("HH:mm:ss");

  public boolean enabled() {
    return this.enabled;
  }

  public ChatTimeStyle style() {
    return this.style;
  }

  public ChatTimeFormat formatting() {
    return this.format;
  }
}
