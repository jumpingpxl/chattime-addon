package dev.jumpingpxl.addons.chattime.core;

import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeFormatting;
import dev.jumpingpxl.addons.chattime.core.settings.options.ChatTimeStyle;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class ChatTimeConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @TextFieldSetting
  private final ConfigProperty<String> style = new ConfigProperty<>(
      "&e\u2503 &6%time% &8\u00BB &r");

  @TextFieldSetting
  private final ConfigProperty<String> format = new ConfigProperty<>("HH:mm:ss");

  private final transient ChatTimeStyle chatTimeStyle = ChatTimeStyle.of(this.style.get());

  private final transient ChatTimeFormatting chatTimeFormatting = ChatTimeFormatting.of(
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
