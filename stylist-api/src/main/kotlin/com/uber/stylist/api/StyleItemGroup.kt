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

/**
 * A [StyleItemGroup] defines code that must be generated in order for a [View] to receive new functionality.
 * Each [StyleItemGroup] can be declared by multiple [ThemeStencil]s, and generate otherwise duplicate code across all views
 * that exhibit them. Common examples include clicks, attach events, visibility changes, etc. They are a hook into the
 * [ThemeStencil]’s code gen process that are called during each [ThemeStencil]’s generation.
 */
class StyleItemGroup(private vararg val styleItemVarArgs: StyleItem) {
  fun styleItems() = styleItemVarArgs.toList()
}
