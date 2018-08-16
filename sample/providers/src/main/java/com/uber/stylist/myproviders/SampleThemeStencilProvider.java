package com.uber.stylist.myproviders;

import android.support.annotation.NonNull;

import com.commit451.uresourcespoet.StyleItem;
import com.google.auto.service.AutoService;
import com.uber.stylist.api.StyleItemGroup;
import com.uber.stylist.api.ThemeStencil;
import com.uber.stylist.api.ThemeStencilProvider;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@AutoService(ThemeStencilProvider.class)
public class SampleThemeStencilProvider implements ThemeStencilProvider {

  private StyleItemGroup basicAppColors = new StyleItemGroup(
      new StyleItem("colorPrimary", "@color/colorPrimary"),
      new StyleItem("colorPrimaryDark", "@color/colorPrimaryDark"),
      new StyleItem("colorAccent", "@color/colorAccent")
  );

  private StyleItemGroup textSizes = new StyleItemGroup(
      new StyleItem("textSizeSmall", "12dp"),
      new StyleItem("textSizeMedium","16dp"),
      new StyleItem("textSizeLarge", "20dp")
  );

  private StyleItemGroup dialogAttrs = new StyleItemGroup(
      new StyleItem("dialogSpecificAttr1", "foo"),
      new StyleItem("dialogSpecificAttr2", "bar")
  );

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

  @NonNull
  @Override
  public Set<StyleItemGroup> globalStyleItemGroups() {
    return new LinkedHashSet<>(Arrays.asList(
        basicAppColors,
        textSizes
    ));
  }
}
