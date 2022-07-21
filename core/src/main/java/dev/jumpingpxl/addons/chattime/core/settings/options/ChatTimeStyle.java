package dev.jumpingpxl.addons.chattime.core.settings.options;

import java.util.Objects;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.inject.LabyGuice;

public class ChatTimeStyle extends ChatTimeOption<String> {

  protected ChatTimeStyle(String defaultValue) {
    super(defaultValue);
  }

  public static ChatTimeStyle of(String defaultValue) {
    return new ChatTimeStyle(defaultValue);
  }

  @Override
  protected String updateValue(String rawValue) {
    String computedValue = LabyGuice.getInstance(ComponentMapper.class)
        .translateColorCodes('&', '\u00a7', rawValue);
    return computedValue;
  }

  public String realTimeValue(ChatTimeFormat chatTimeFormat) {
    String realTimeValue = this.computedValue();
    String currentFormat = chatTimeFormat.realTimeValue();
    if (Objects.nonNull(currentFormat)) {
      realTimeValue = realTimeValue.replace("%time%", currentFormat);
    }

    return realTimeValue;
  }
}
