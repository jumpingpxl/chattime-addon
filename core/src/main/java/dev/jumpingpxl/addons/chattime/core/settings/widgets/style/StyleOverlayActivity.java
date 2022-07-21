package dev.jumpingpxl.addons.chattime.core.settings.widgets.style;

import dev.jumpingpxl.addons.chattime.core.settings.WidgetOverlayActivity;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import org.jetbrains.annotations.Nullable;

@AutoActivity
@Link("style-overlay.lss")
public class StyleOverlayActivity extends WidgetOverlayActivity {

  private final StyleSettingWidget styleWidget;

  protected StyleOverlayActivity(StyleSettingWidget styleWidget) {
    this.styleWidget = styleWidget;
  }

  @Override
  public void initialize(Parent parent) {
    super.initialize(parent);
  }

  @Override
  public <T extends LabyScreen> @Nullable T renew() {
    return (T) new StyleOverlayActivity(this.styleWidget);
  }
}
