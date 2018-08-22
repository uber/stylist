/*
 * Copyright (c) 2018. Commit 451
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

package com.commit451.uresourcespoet

/**
 * Represents an Android Plural
 *
 * See <a https:></a>//developer.android.com/guide/topics/resources/string-resource.html#Plurals">https://developer.android.com/guide/topics/resources/string-resource.html#Plurals
 */
data class Plural(val quantity: Quantity, val value: String) {

  enum class Quantity {
    zero,
    one,
    two,
    few,
    many,
    other
  }
}
