package dev.jumpingpxl.addons.chattime.core.settings;

import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.activities.OverlayWidgetActivity;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.inject.LabyGuice;

public class WidgetOverlayService {

  private final AbstractWidget<?> holder;
  private final Supplier<WidgetOverlayActivity> activitySupplier;

  private OverlayWidgetActivity overlay;
  private boolean open;

  private WidgetOverlayService(AbstractWidget<?> holder,
      Supplier<WidgetOverlayActivity> activitySupplier) {
    this.holder = holder;
    this.activitySupplier = activitySupplier;
  }

  public static WidgetOverlayService create(AbstractWidget<?> holder,
      Supplier<WidgetOverlayActivity> activitySupplier) {
    return new WidgetOverlayService(holder, activitySupplier);
  }

  public boolean press() {
    if (this.open) {
      this.closeOverlay();
    } else {
      this.openOverlay();
    }

    return true;
  }

  public void dispose() {
    this.closeOverlay();
  }

  public AbstractWidget<?> holder() {
    return this.holder;
  }

  private void closeOverlay() {
    this.open = false;
    if (Objects.nonNull(this.overlay)) {
      this.overlay.closeOverlayActivity();
      this.overlay = null;
    }
  }

  private void openOverlay() {
    this.open = true;

    LabyAPI labyAPI = LabyGuice.getInstance(LabyAPI.class);

    ScreenRendererWidget screenRenderer = new ScreenRendererWidget();
    this.overlay = new OverlayWidgetActivity(
        Byte.MAX_VALUE,
        (Activity) this.holder.getRoot(),
        screenRenderer
    );

    labyAPI.getScreenOverlayHandler().registerOverlay(this.overlay);

    WidgetOverlayActivity widgetOverlayActivity = this.activitySupplier.get();
    widgetOverlayActivity.setOverlayService(this);
    widgetOverlayActivity.setHolder(this.holder);
    widgetOverlayActivity.setScreenRenderer(screenRenderer);

    screenRenderer.displayScreen(widgetOverlayActivity);
  }
}
