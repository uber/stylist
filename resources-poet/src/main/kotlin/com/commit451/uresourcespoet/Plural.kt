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
