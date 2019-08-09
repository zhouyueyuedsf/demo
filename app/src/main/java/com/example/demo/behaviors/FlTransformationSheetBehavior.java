/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo.behaviors;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewParent;
import androidx.annotation.AnimatorRes;
import androidx.annotation.CallSuper;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.Positioning;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

/**
 * Behavior that should be attached to any sheet that should appear when a {@link
 * FloatingActionButton} is {@link FloatingActionButton#setExpanded(boolean)} expanded}.
 *
 * <p>A sheet usually has some width and height that's smaller than the screen, has an elevation,
 * and may have a scrim underneath.
 */
public class FlTransformationSheetBehavior extends FlTransformationBehavior {

  private Map<View, Integer> importantForAccessibilityMap;

  public FlTransformationSheetBehavior() {}

  public FlTransformationSheetBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected FabTransformationSpec onCreateMotionSpec(Context context, boolean expanded) {
    @AnimatorRes int specRes;
    if (expanded) {
      specRes = R.animator.mtrl_fab_transformation_sheet_expand_spec;
    } else {
      specRes = R.animator.mtrl_fab_transformation_sheet_collapse_spec;
    }

    FabTransformationSpec spec = new FabTransformationSpec();
    spec.timings = MotionSpec.createFromResource(context, specRes);
    spec.positioning = new Positioning(Gravity.CENTER, 0f, 0f);
    return spec;
  }

  @CallSuper
  @Override
  protected boolean onExpandedStateChange(
      View dependency, View child, boolean expanded, boolean animated) {
    return super.onExpandedStateChange(dependency, child, expanded, animated);
  }

}
