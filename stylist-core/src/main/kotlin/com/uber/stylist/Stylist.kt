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

object Stylist {

  fun generateThemesFor(
      outputDir: File, formatSource: Boolean) {
    val themeStencilService = ThemeStencilService.newInstance()
    val styleItemGroups = themeStencilService.getGlobalStyleItemGroups()
    val stencils = themeStencilService.getStencils()
    generateThemesForStencils(stencils, styleItemGroups, outputDir, formatSource)
  }

  @VisibleForTesting
  fun generateThemesForStencils(
      stencils: Set<ThemeStencil>,
      styleItemGroups: Set<StyleItemGroup>,
      outputDir: File,
      formatSource: Boolean) {

    val valuesFolder = File("$outputDir/values").apply { mkdirs() }
    val themesXml = File(valuesFolder, "ub__themes_generated.xml").apply { createNewFile() }

    ResourcesPoet.create().apply {
      stencils.forEach {
        it.setGlobalStyleItemGroups(styleItemGroups)
        addStyle(it.name, it.parent, it.styleItems())
      }
      indent(formatSource)
      build(themesXml)
    }
  }
}
