package dev.jumpingpxl.addons.chattime.core.settings.options;

import net.labymod.api.Laby;

public class ChatTimeStyle extends ChatTimeOption<String> {

  protected ChatTimeStyle(String defaultValue) {
    super(defaultValue);
  }

  public static ChatTimeStyle of(String defaultValue) {
    return new ChatTimeStyle(defaultValue);
  }

  @Override
  protected String updateValue(String rawValue) {
    return Laby.references().componentMapper().translateColorCodes('&', '\u00a7', rawValue);
  }
}
