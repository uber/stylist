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

import com.uber.stylist.Stylist.DEFAULT_THEMES_XML_FILENAME
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs
import java.io.File

/**
 * Gradle task responsible for invoking Stylist to generate the themes.
 */
open class StylistTask : DefaultTask() {

  @Input
  lateinit var outputDirectory: File

  @Input
  var themesXmlFilename: String = DEFAULT_THEMES_XML_FILENAME

  @Input
  var formatSource: Boolean = true

  @TaskAction
  fun execute(inputs: IncrementalTaskInputs) {
    Stylist.generateThemesFor(outputDirectory, themesXmlFilename, formatSource)
  }
}
