package com.commit451.uresourcespoet

/**
 * The values within an attribute
 */
data class Attr(val name: String, val formats: List<Format> = emptyList()) {

  enum class Format constructor(private val attrName: String) {
    BOOLEAN("boolean"),
    FLOAT("float"),
    COLOR("color"),
    DIMENSION("dimension"),
    ENUM("enum"),
    FLAG("flag"),
    FRACTION("fraction"),
    INTEGER("integer"),
    REFERENCE("reference"),
    STRING("string");

    override fun toString() = attrName
  }
}
