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

package com.uber.stylist.api

import com.commit451.uresourcespoet.StyleItem

class ThemeStencil(
    val name: String,
    val parent: String,
    private vararg val addedStyleItemGroups: StyleItemGroup = emptyArray()) {

  private val globalStyleItemGroups = mutableSetOf<StyleItemGroup>()

  fun styleItems(): List<StyleItem> = (globalStyleItemGroups + addedStyleItemGroups.toSet())
      .flatMap { it.styleItems() }
      .toList()

  fun setGlobalStyleItemGroups(styleItemGroups: Set<StyleItemGroup>) {
    globalStyleItemGroups.addAll(styleItemGroups)
  }
}
