package com.commit451.uresourcespoet

/**
 * The various resource types
 *
 * @see [https://developer.android.com/guide/topics/resources/available-resources.html](https://developer.android.com/guide/topics/resources/available-resources.html)
 */
enum class Type(private val xmlName: String) {

  ATTR("attr"),
  BOOL("bool"),
  COLOR("color"),
  DRAWABLE("drawable"),
  DIMENSION("dimen"),
  ID("item"),
  INTEGER("integer"),
  INTEGER_ARRAY("integer-array"),
  FONT("font"),
  LAYOUT("layout"),
  MENU("menu"),
  PLURALS("plurals"),
  STRING("string"),
  STRING_ARRAY("string-array"),
  STYLE("style"),
  TYPED_ARRAY("array");

  override fun toString() = xmlName
}
