package dev.jumpingpxl.addons.chattime.core;

import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeFormatting;
import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeStyle;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ConfigName;

@SuppressWarnings("FieldMayBeFinal")
@ConfigName("settings")
public class ChatTimeConfiguration extends Config {

  @SwitchSetting
  private boolean enabled = true;

  @TextFieldSetting
  private String style = "&e┃ &6%time% &8» &r";

  @TextFieldSetting
  private String format = "HH:mm:ss";

  private transient ChatTimeStyle chatTimeStyle = ChatTimeStyle.of(this.style);
  private transient ChatTimeFormatting chatTimeFormatting = ChatTimeFormatting.of(this.format);

  public boolean enabled() {
    return this.enabled;
  }

  public ChatTimeStyle style() {
    if (!this.chatTimeStyle.rawValue().equals(this.style)) {
      this.chatTimeStyle.setRawValue(this.style);
    }

    return this.chatTimeStyle;
  }

  public ChatTimeFormatting formatting() {
    if (!this.chatTimeFormatting.rawValue().equals(this.format)) {
      this.chatTimeFormatting.setRawValue(this.format);
    }

    return this.chatTimeFormatting;
  }
}
