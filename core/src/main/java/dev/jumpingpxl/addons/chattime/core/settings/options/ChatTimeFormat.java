package dev.jumpingpxl.addons.chattime.core.settings.options;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class ChatTimeFormat extends ChatTimeOption<SimpleDateFormat> {

  protected ChatTimeFormat(String defaultValue) {
    super(defaultValue);
  }

  public static ChatTimeFormat of(String defaultValue) {
    return new ChatTimeFormat(defaultValue);
  }

  @Override
  protected SimpleDateFormat updateValue(String rawValue) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(rawValue);
    try {
      dateFormat.format(System.currentTimeMillis());
      return dateFormat;
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  public String realTimeValue() {
    if (Objects.isNull(this.computedValue())) {
      return null;
    }

    return this.computedValue().format(System.currentTimeMillis());
  }
}
