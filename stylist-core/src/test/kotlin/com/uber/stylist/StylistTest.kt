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

package com.uber.stylist

import com.google.common.io.Files
import com.google.common.truth.Truth.assertThat
import com.uber.stylist.Stylist.DEFAULT_THEMES_XML_FILENAME
import com.uber.stylist.api.StyleItem
import com.uber.stylist.api.StyleItemGroup
import com.uber.stylist.api.ThemeStencil
import org.junit.Test

class StylistTest {

  companion object {
    private const val THEMES_NO_THEMES = """<?xml version="1.0" encoding="utf-8" standalone="no"?>
<resources/>
"""

    private const val THEMES_NO_STYLE_GROUPS = """<?xml version="1.0" encoding="utf-8" standalone="no"?>
<resources>
    <style name="Theme.Test" parent="Theme.AppCompat"/>
    <style name="Theme.Test.Light" parent="Theme.AppCompat.Light"/>
    <style name="Theme.Test.Dialog" parent="Theme.AppCompat.Dialog"/>
    <style name="Theme.Test.Light.Dialog" parent="Theme.AppCompat.Light.Dialog"/>
</resources>
"""

    private const val THEMES_WITH_STYLE_GROUP = """<?xml version="1.0" encoding="utf-8" standalone="no"?>
<resources>
    <style name="Theme.Test" parent="Theme.AppCompat">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
    <style name="Theme.Test.Light" parent="Theme.AppCompat.Light">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
    <style name="Theme.Test.Dialog" parent="Theme.AppCompat.Dialog">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
    <style name="Theme.Test.Light.Dialog" parent="Theme.AppCompat.Light.Dialog">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
</resources>
"""

    private const val THEMES_WITH_STYLE_GROUPS = """<?xml version="1.0" encoding="utf-8" standalone="no"?>
<resources>
    <style name="Theme.Test" parent="Theme.AppCompat">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="textSizeSmall">12dp</item>
        <item name="textSizeMedium">16dp</item>
        <item name="textSizeLarge">20dp</item>
    </style>
    <style name="Theme.Test.Light" parent="Theme.AppCompat.Light">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="textSizeSmall">12dp</item>
        <item name="textSizeMedium">16dp</item>
        <item name="textSizeLarge">20dp</item>
    </style>
    <style name="Theme.Test.Dialog" parent="Theme.AppCompat.Dialog">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="textSizeSmall">12dp</item>
        <item name="textSizeMedium">16dp</item>
        <item name="textSizeLarge">20dp</item>
    </style>
    <style name="Theme.Test.Light.Dialog" parent="Theme.AppCompat.Light.Dialog">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="textSizeSmall">12dp</item>
        <item name="textSizeMedium">16dp</item>
        <item name="textSizeLarge">20dp</item>
    </style>
</resources>
"""

    private const val THEMES_WITH_STYLE_GROUPS_PLUS_ADDED = """<?xml version="1.0" encoding="utf-8" standalone="no"?>
<resources>
    <style name="Theme.Test" parent="Theme.AppCompat">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="textSizeSmall">12dp</item>
        <item name="textSizeMedium">16dp</item>
        <item name="textSizeLarge">20dp</item>
    </style>
    <style name="Theme.Test.Light" parent="Theme.AppCompat.Light">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="textSizeSmall">12dp</item>
        <item name="textSizeMedium">16dp</item>
        <item name="textSizeLarge">20dp</item>
    </style>
    <style name="Theme.Test.Dialog" parent="Theme.AppCompat.Dialog">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="textSizeSmall">12dp</item>
        <item name="textSizeMedium">16dp</item>
        <item name="textSizeLarge">20dp</item>
        <item name="dialogSpecificAttr1">foo</item>
        <item name="dialogSpecificAttr2">bar</item>
    </style>
    <style name="Theme.Test.Light.Dialog" parent="Theme.AppCompat.Light.Dialog">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="textSizeSmall">12dp</item>
        <item name="textSizeMedium">16dp</item>
        <item name="textSizeLarge">20dp</item>
        <item name="dialogSpecificAttr1">foo</item>
        <item name="dialogSpecificAttr2">bar</item>
    </style>
</resources>
"""

    private val THEMES_UNFORMATTED = """<?xml version="1.0" encoding="utf-8" standalone="no"?>
<resources>
<style name="Theme.Test" parent="Theme.AppCompat">
<item name="colorPrimary">@color/colorPrimary</item>
<item name="colorPrimaryDark">@color/colorPrimaryDark</item>
<item name="colorAccent">@color/colorAccent</item>
</style>
<style name="Theme.Test.Light" parent="Theme.AppCompat.Light">
<item name="colorPrimary">@color/colorPrimary</item>
<item name="colorPrimaryDark">@color/colorPrimaryDark</item>
<item name="colorAccent">@color/colorAccent</item>
</style>
<style name="Theme.Test.Dialog" parent="Theme.AppCompat.Dialog">
<item name="colorPrimary">@color/colorPrimary</item>
<item name="colorPrimaryDark">@color/colorPrimaryDark</item>
<item name="colorAccent">@color/colorAccent</item>
</style>
<style name="Theme.Test.Light.Dialog" parent="Theme.AppCompat.Light.Dialog">
<item name="colorPrimary">@color/colorPrimary</item>
<item name="colorPrimaryDark">@color/colorPrimaryDark</item>
<item name="colorAccent">@color/colorAccent</item>
</style>
</resources>""".split("\n").joinToString(separator = "")

    private val basicAppColors = StyleItemGroup(
        StyleItem("colorPrimary", "@color/colorPrimary"),
        StyleItem("colorPrimaryDark", "@color/colorPrimaryDark"),
        StyleItem("colorAccent", "@color/colorAccent")
    )

    private val textSizes = StyleItemGroup(
        StyleItem("textSizeSmall", "12dp"),
        StyleItem("textSizeMedium", "16dp"),
        StyleItem("textSizeLarge", "20dp")
    )

    private val dialogAttrs = StyleItemGroup(
        StyleItem("dialogSpecificAttr1", "foo"),
        StyleItem("dialogSpecificAttr2", "bar")
    )

    val themeStencils: Set<ThemeStencil> = linkedSetOf(
        ThemeStencil("Theme.Test", "Theme.AppCompat"),
        ThemeStencil("Theme.Test.Light", "Theme.AppCompat.Light"),
        ThemeStencil("Theme.Test.Dialog", "Theme.AppCompat.Dialog"),
        ThemeStencil("Theme.Test.Light.Dialog", "Theme.AppCompat.Light.Dialog")
    )

    val themeStencilsAddedStyles: Set<ThemeStencil> = linkedSetOf(
        ThemeStencil("Theme.Test", "Theme.AppCompat"),
        ThemeStencil("Theme.Test.Light", "Theme.AppCompat.Light"),
        ThemeStencil("Theme.Test.Dialog", "Theme.AppCompat.Dialog", dialogAttrs),
        ThemeStencil("Theme.Test.Light.Dialog", "Theme.AppCompat.Light.Dialog", dialogAttrs)
    )
  }

  @Test
  fun testStylist_withNoThemes_shouldGenerateExpectedXml() {
    testXml(emptySet(), emptySet(), THEMES_NO_THEMES)
  }

  @Test
  fun testStylist_withNoStyleGroups_shouldGenerateExpectedXml() {
    testXml(themeStencils, linkedSetOf(), THEMES_NO_STYLE_GROUPS)
  }

  @Test
  fun testStylist_withSingleStyleGroup_shouldGenerateExpectedXml() {
    testXml(themeStencils, linkedSetOf(basicAppColors), THEMES_WITH_STYLE_GROUP)
  }

  @Test
  fun testStylist_withMultipleStyleGroups_shouldGenerateExpectedXml() {
    testXml(themeStencils, linkedSetOf(basicAppColors, textSizes), THEMES_WITH_STYLE_GROUPS)
  }

  @Test
  fun testStylist_withMultipleStyleGroupsPlusAdded_shouldGenerateExpectedXml() {
    testXml(themeStencilsAddedStyles, linkedSetOf(basicAppColors, textSizes), THEMES_WITH_STYLE_GROUPS_PLUS_ADDED)
  }

  @Test
  fun testStylist_withFormatFalse_shouldGenerateExpectedXml() {
    testXml(themeStencils, linkedSetOf(basicAppColors), THEMES_UNFORMATTED, formatSource = false)
  }

  @Test
  fun testStylist_withNonDefaultThemesXmlFilename_shouldCorrectFile() {
    testXml(themeStencils, linkedSetOf(basicAppColors), THEMES_WITH_STYLE_GROUP, themesXmlFileName = "generated_themes_xml_alt_filename.xml")
  }

  private fun testXml(
      stencils: Set<ThemeStencil>,
      globalStyleGroups: Set<StyleItemGroup>,
      expectedXml: String,
      themesXmlFileName: String = DEFAULT_THEMES_XML_FILENAME,
      formatSource: Boolean = true) {
    val outputDir = Files.createTempDir()
    outputDir.resolve("values").apply {
      Stylist.generateThemesForStencils(stencils, globalStyleGroups, outputDir, themesXmlFileName = themesXmlFileName, formatSource = formatSource)

      val xmlFileNames = listFiles().map { it.name }.toList()
      assertThat(xmlFileNames).containsExactly(themesXmlFileName)

      val generatedThemesXml = listFiles().first().readText()
      assertThat(generatedThemesXml).isEqualTo(expectedXml)
    }
    outputDir.deleteRecursively()
  }
}
