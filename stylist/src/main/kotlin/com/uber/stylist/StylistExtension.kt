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

import com.uber.stylist.StylistPlugin.Companion.DEFAULT_THEMES_XML_FILENAME

class StylistExtension {

  /**
   * Optional setting to control the name of the generated themes.xml file. Defaults to
   * "themes_stylist_generated.xml"
   *
   * @see StylistPlugin.Companion.DEFAULT_THEMES_XML_FILENAME
   */
  var themesXmlFileName: String = DEFAULT_THEMES_XML_FILENAME

  /**
   * Optional setting to control whether the source is formatted with Google Java Format. Defaults to true.
   */
  var formatSource: Boolean = true
}
