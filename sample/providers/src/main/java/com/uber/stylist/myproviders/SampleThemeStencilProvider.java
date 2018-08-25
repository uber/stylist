/*
 * Copyright (c) 2018. Uber Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uber.stylist.myproviders;

import android.support.annotation.NonNull;

import com.google.auto.service.AutoService;
import com.uber.stylist.api.StyleItem;
import com.uber.stylist.api.StyleItemGroup;
import com.uber.stylist.api.ThemeStencil;
import com.uber.stylist.api.ThemeStencilProvider;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Sample provider for the themes to generate.
 */
@AutoService(ThemeStencilProvider.class)
public class SampleThemeStencilProvider implements ThemeStencilProvider {

  private StyleItemGroup basicAppColors = new StyleItemGroup(
      new StyleItem("colorPrimary", "@color/colorPrimary"),
      new StyleItem("colorPrimaryDark", "@color/colorPrimaryDark"),
      new StyleItem("colorAccent", "@color/colorAccent")
  );

  private StyleItemGroup textSizes = new StyleItemGroup(
      new StyleItem("textSizeSmall", "12dp"),
      new StyleItem("textSizeMedium", "16dp"),
      new StyleItem("textSizeLarge", "20dp")
  );

  private StyleItemGroup dialogAttrs = new StyleItemGroup(
      new StyleItem("dialogSpecificAttr1", "foo"),
      new StyleItem("dialogSpecificAttr2", "bar")
  );

  /**
   *
   * @return a set of theme stencils
   */
  @NonNull
  @Override
  public Set<ThemeStencil> stencils() {
    return new LinkedHashSet<>(Arrays.asList(
        new ThemeStencil("Theme.Sample", "Theme.AppCompat"),
        new ThemeStencil("Theme.Sample.Light", "Theme.AppCompat.Light"),
        new ThemeStencil("Theme.Sample.Dialog", "Theme.AppCompat.Dialog", dialogAttrs),
        new ThemeStencil("Theme.Sample.Light.Dialog", "Theme.AppCompat.Light.Dialog", dialogAttrs)
    ));
  }

  /**
   *
   * @return a set of StyleItemGroups
   */
  @NonNull
  @Override
  public Set<StyleItemGroup> globalStyleItemGroups() {
    return new LinkedHashSet<>(Arrays.asList(
        basicAppColors,
        textSizes
    ));
  }
}
