/*
 * Copyright (C) 2018. Uber Technologies
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

import android.support.annotation.VisibleForTesting
import java.io.File
import com.commit451.uresourcespoet.ResourcesPoet
import com.uber.stylist.api.StyleItemGroup
import com.uber.stylist.api.ThemeStencil
import com.uber.stylist.api.ThemeStencilService

/**
 * This contains the core logic of the Stylist library.
 */
object Stylist {

  const val DEFAULT_THEMES_XML_FILENAME = "themes_stylist_generated.xml"

  /**
   * Generate Android XML themes for the [ThemeStencil]s and [StyleItemGroup]s
   * provided by the [ThemeStencilService].
   *
   * @outputDir the res directory to generate the themes into
   * @themesXmlFilename the name of the file for the generated themes
   * @formatSource whether or not to format and indent the generated themes file
   */
  fun generateThemesFor(
      outputDir: File, themesXmlFilename: String, formatSource: Boolean) {
    val themeStencilService = ThemeStencilService.newInstance()
    val styleItemGroups = themeStencilService.getGlobalStyleItemGroups()
    val stencils = themeStencilService.getStencils()
    generateThemesForStencils(stencils, styleItemGroups, outputDir, themesXmlFilename, formatSource)
  }

  /**
   * See [Stylist.generateThemesFor]
   */
  @VisibleForTesting
  internal fun generateThemesForStencils(
      stencils: Set<ThemeStencil>,
      styleItemGroups: Set<StyleItemGroup>,
      outputDir: File,
      themesXmlFileName: String,
      formatSource: Boolean) {

    val themesXmlFile = File("$outputDir/values/$themesXmlFileName").apply {
      parentFile.mkdirs()
      createNewFile()
    }

    ResourcesPoet.create(indent = formatSource).apply {
      stencils.forEach {
        it.setGlobalStyleItemGroups(styleItemGroups)
        addStyle(it.name, it.parent, it.styleItems())
      }
      build(themesXmlFile)
    }
  }
}
