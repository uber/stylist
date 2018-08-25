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

import com.uber.stylist.api.StyleItem

/**
 * The definition for an an XML theme to be generated.
 */
class ThemeStencil(
    val name: String,
    val parent: String,
    private vararg val addedStyleItemGroups: StyleItemGroup = emptyArray()) {

  private val globalStyleItemGroups = mutableSetOf<StyleItemGroup>()

  /**
   * Returns a collection of [StyleItems][StyleItem] to be included in the XML theme
   * including those that are globally applicable.
   *
   * @return the collection of [StyleItems][StyleItem]
   */
  fun styleItems(): List<StyleItem> = (globalStyleItemGroups + addedStyleItemGroups.toSet())
      .flatMap { it.styleItems() }
      .toList()

  /**
   * Applies the globally-applicable [StyleItemGroups][StyleItemGroup] to this [ThemeStencil].
   *
   * @param styleItemGroups the set of [StyleItemGroups][StyleItemGroup]
   */
  fun setGlobalStyleItemGroups(styleItemGroups: Set<StyleItemGroup>) {
    globalStyleItemGroups.apply {
      clear()
      addAll(styleItemGroups)
    }
  }
}
