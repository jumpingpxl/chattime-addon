package dev.jumpingpxl.addons.chattime.core.settings.options;

import java.util.Objects;

public abstract class ChatTimeOption<T> {

  private transient final String defaultValue;
  private transient T computedValue;

  private String value;

  protected ChatTimeOption(String defaultValue) {
    this.defaultValue = defaultValue;
    this.value = defaultValue;
  }

  protected abstract T updateValue(String rawValue);

  protected final void setComputedValue(T computedValue) {
    this.computedValue = computedValue;
  }

  public final String rawValue() {
    return this.value;
  }

  public final T computedValue() {
    if (Objects.isNull(this.computedValue)) {
      this.computedValue = this.updateValue(this.rawValue());
    }

    return this.computedValue;
  }

  public final void setRawValue(String rawValue) {
    this.value = Objects.isNull(rawValue) ? this.defaultValue : rawValue;
    this.computedValue = this.updateValue(rawValue);
  }

  public void reset() {
    this.setRawValue(null);
  }
}
