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

package com.uber.stylist.api

import java.util.ServiceLoader

/**
 * Service responsible for collecting the [ThemeStencils][ThemeStencil] to be used during code generation.
 */
class ThemeStencilService private constructor() {

  private val serviceLoader = ServiceLoader.load(ThemeStencilProvider::class.java)

  /**
   * Gets the [ThemeStencil] implementations loaded.
   *
   * @return The located [ThemeStencils][ThemeStencil].
   */
  fun getStencils(): Set<ThemeStencil> {
    val stencils = LinkedHashSet<ThemeStencil>()
    serviceLoader.iterator()
        .forEach { stencils.addAll(it.stencils()) }
    return stencils
  }

  /**
   * Gets the [StyleItemGroup] implementations that should be applied to every [ThemeStencil].
   *
   * @return The located global [StyleItemGroups][StyleItemGroup].
   */
  fun getGlobalStyleItemGroups(): Set<StyleItemGroup> {
    val styleItemGroups = LinkedHashSet<StyleItemGroup>()
    serviceLoader.iterator()
        .forEach { styleItemGroups.addAll(it.globalStyleItemGroups()) }
    return styleItemGroups
  }

  companion object {
    /**
     * Creates a new [ThemeStencilService].
     *
     * @return the [ThemeStencilService]
     */
    fun newInstance() = ThemeStencilService()
  }
}
