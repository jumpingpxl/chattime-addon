package dev.jumpingpxl.addons.chattime.core;

import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeFormatting;
import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeStyle;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@SuppressWarnings("FieldMayBeFinal")
@ConfigName("settings")
public class ChatTimeConfiguration extends AddonConfig {

  @SwitchSetting
  private ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @TextFieldSetting
  private ConfigProperty<String> style = new ConfigProperty<>("&e┃ &6%time% &8» &r");

  @TextFieldSetting
  private ConfigProperty<String> format = new ConfigProperty<>("HH:mm:ss");

  private transient ChatTimeStyle chatTimeStyle = ChatTimeStyle.of(this.style.get());

  private transient ChatTimeFormatting chatTimeFormatting = ChatTimeFormatting.of(
      this.format.get());

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ChatTimeStyle style() {
    if (!this.chatTimeStyle.rawValue().equals(this.style.get())) {
      this.chatTimeStyle.setRawValue(this.style.get());
    }

    return this.chatTimeStyle;
  }

  public ChatTimeFormatting formatting() {
    if (!this.chatTimeFormatting.rawValue().equals(this.format.get())) {
      this.chatTimeFormatting.setRawValue(this.format.get());
    }

    return this.chatTimeFormatting;
  }
}
