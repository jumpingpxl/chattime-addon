package dev.jumpingpxl.addons.chattime.core.settings;

import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.util.bounds.Rectangle;
import org.spongepowered.include.com.google.common.collect.Lists;

public abstract class WidgetOverlayActivity extends Activity {

  private final List<StyleBlock> blocks;
  private WidgetOverlayService overlayService;
  private ScreenRendererWidget screenRendererWidget;
  private AbstractWidget<?> holder;
  private boolean loadedPosition;
  private HorizontalAlignment horizontalAlignment;

  protected WidgetOverlayActivity() {
    this.blocks = Lists.newArrayList();
  }

  @Override
  public void initialize(Parent parent) {
    if (!(parent instanceof ScreenRendererWidget)) {
      throw new UnsupportedOperationException("Parent has to be a ScreenRendererWidget");
    }

    this.loadedPosition = false;
    super.initialize(parent);
  }

  @Override
  protected void postStyleSheetLoad() {
    super.postStyleSheetLoad();
    this.loadedPosition = true;
    this.updatePosition();
  }

  protected void setOverlayService(WidgetOverlayService overlayService) {
    this.overlayService = overlayService;
  }

  protected void setScreenRenderer(ScreenRendererWidget screenRenderer) {
    this.screenRendererWidget = screenRenderer;
    if (this.loadedPosition) {
      this.updatePosition();
    }
  }

  public void setHolder(AbstractWidget<?> holder) {
    this.holder = holder;
  }

  private void updatePosition() {
    if (!this.loadedPosition || Objects.isNull(this.screenRendererWidget)) {
      return;
    }

    ScreenRendererWidget widget = this.screenRendererWidget;
    float distance = 0;
    float width = 0;
    float height = 0;
    for (StyleBlock overlayPositionBlock : this.overlayPositionBlocks()) {
      for (Entry<String, SingleInstruction> entry : overlayPositionBlock.getInstructions()
          .entrySet()) {
        String value = entry.getValue().getValue();
        switch (entry.getKey().toLowerCase()) {
          case "distance-to-holder":
            distance = Float.parseFloat(value);
            break;
          case "width":
            width = value.equalsIgnoreCase("fit-content") ? -1 : Float.parseFloat(value);
            break;
          case "height":
            height = value.equalsIgnoreCase("fit-content") ? -1 : Float.parseFloat(value);
            break;
          case "alignment":
            this.horizontalAlignment = HorizontalAlignment.of(value);
            break;
        }
      }
    }

    if (Objects.isNull(this.horizontalAlignment)) {
      this.horizontalAlignment = HorizontalAlignment.CENTER;
    }

    this.tryPosition(this.holder.getBounds().rectangle(BoundsType.MIDDLE), VerticalPosition.BOTTOM,
        false, distance, width, height);
  }

  private void tryPosition(Rectangle holder, VerticalPosition position, boolean force,
      float distanceToWidget, float width, float height) {
    float outerWidth = width == -1 ? this.getDocument().getContentWidth(BoundsType.OUTER) : width;
    float outerHeight =
        height == -1 ? this.getDocument().getContentHeight(BoundsType.OUTER) : height;

    float x = 0;
    float y = 0;
    float maxX = 0;
    float maxY = 0;

    //Calculate Horizontal Alignment
    switch (this.horizontalAlignment) {
      case CENTER:
        x = holder.getCenterX() - width / 2;
        break;
      case LEFT:
        x = holder.getX();
        break;
      case RIGHT:
        x = holder.getX() + holder.getWidth();
    }

    maxX = x + outerWidth;

    //Calculate Vertical Alignment
    if (position == VerticalPosition.BOTTOM) {
      y = holder.getY() + holder.getHeight() + distanceToWidget;
      maxX = y + height;
    } else if (position == VerticalPosition.TOP) {
      maxX = holder.getY() - distanceToWidget;
      y = maxX - height;
    }

    Bounds absoluteBounds = this.labyAPI.getMinecraft().getMinecraftWindow().getAbsoluteBounds();
    if (!force && (absoluteBounds.getX() > x || absoluteBounds.getY() > y
        || absoluteBounds.getX() + absoluteBounds.getWidth() < maxX
        || absoluteBounds.getY() + absoluteBounds.getHeight() < maxY)) {
      this.tryPosition(holder,
          position == VerticalPosition.BOTTOM ? VerticalPosition.TOP : position, true,
          distanceToWidget, width, height);
      return;
    }

    this.screenRendererWidget.getBounds().setOuterPosition(x, y);
    this.screenRendererWidget.getBounds().setOuterSize(maxX - x, maxY - y);
  }

  protected List<StyleBlock> overlayPositionBlocks() {
    if (this.blocks.isEmpty()) {
      for (StyleSheet stylesheet : this.getStylesheets()) {
        for (StyleBlock block : stylesheet.getBlocks()) {
          if (!block.getRawSelector().equalsIgnoreCase(".widget-overlay-renderer")) {
            continue;
          }

          this.blocks.add(block);
        }
      }
    }

    return this.blocks;
  }

  public enum HorizontalAlignment {
    LEFT, CENTER, RIGHT;

    private static final HorizontalAlignment[] VALUES = values();

    public static HorizontalAlignment of(String string) {
      HorizontalAlignment alignment = CENTER;
      for (HorizontalAlignment value : VALUES) {
        if (value.name().equalsIgnoreCase(string)) {
          alignment = value;
          break;
        }
      }

      return alignment;
    }
  }

  private enum VerticalPosition {
    TOP, BOTTOM
  }
}
