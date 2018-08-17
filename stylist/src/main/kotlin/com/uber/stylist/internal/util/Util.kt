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

package com.uber.stylist.internal.util

import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Project
import java.io.File

internal fun resolveVariantOutputDir(project: Project, variant: BaseVariant, plugin: String): File = project.file(
    "${project.projectDir}/build/generated/res/$plugin/${variant.flavorName}/${variant.buildType.name}".sanitize()
)

internal fun String.sanitize(): String = replace('/', File.separatorChar)
