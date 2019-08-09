package com.example.demo.behaviors;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.expandable.ExpandableTransformationWidget;
import com.google.android.material.expandable.ExpandableWidgetHelper;

@CoordinatorLayout.DefaultBehavior(ExpandBehavior.class)
public class ExpandRevealFrameLayout extends FrameLayout implements ExpandableTransformationWidget {
  private final ExpandableWidgetHelper expandableWidgetHelper;

  public ExpandRevealFrameLayout(Context context) {
    this(context, null);
  }

  public ExpandRevealFrameLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    expandableWidgetHelper = new ExpandableWidgetHelper(this);
  }

  @Override
  public int getExpandedComponentIdHint() {
    return expandableWidgetHelper.getExpandedComponentIdHint();
  }

  @Override
  public void setExpandedComponentIdHint(int expandedComponentIdHint) {
    expandableWidgetHelper.setExpandedComponentIdHint(expandedComponentIdHint);
  }

  @Override
  public boolean isExpanded() {
    return expandableWidgetHelper.isExpanded();
  }

  @Override
  public boolean setExpanded(boolean expanded) {
    return expandableWidgetHelper.setExpanded(expanded);
  }


  public void getContentRect(Rect rect) {
    rect.set(0, 0, getWidth(), getHeight());
  }
}
