# Stylist [![Build Status](https://travis-ci.org/uber/stylist.svg?branch=master)](https://travis-ci.org/uber/stylist)

As Android apps grow, providing common styling across app themes becomes challenging. Typically, this results in copy-pasting style items across themes, monolithic themes, or complicated inheritance trees. Stylist is a highly-extensible platform for creating and maintaining an app’s base set of Android XML themes.

## Overview

Stylist is a Gradle plugin written in Kotlin that generates a base set of Android XML themes. Stylist-generated themes are created using a stencil and trait system. Each theme is declared with a single stencil, which is comprised of sets of logically-grouped style items. All of this comes together to create an easily maintainable system of stencils and traits.

*ThemeStencils*: A 1:1 ratio of `ThemeStencil`s to corresponding generated themes. Each `ThemeStencil` declares a theme name and parent theme plus any extra `StyleItemGroup`s that should be included in addition to the globally applicable set.

*StyleItemGroups*: Each `StyleItemGroup` can be declared by multiple `ThemeStencil`s and generate otherwise duplicated style items across all themes that include them. Common examples include default app colors, font sizes, and common dimension values. They are a logical groupings of custom theme attributes that get included in each theme that declares the group.

## Usage

A simple `ThemeStencilProvider` that defines text sizes in Dark and Light themes would look like:

```kotlin
@AutoService(ThemeStencilProvider::class)
class SampleThemeStencilProvider : ThemeStencilProvider {

  private val textSizes = StyleItemGroup(
      StyleItem("textSizeSmall", "12dp"),
      StyleItem("textSizeMedium","16dp"),
      StyleItem("textSizeLarge", "20dp")
  )

  override fun stencils() = linkedSetOf(
      ThemeStencil("Theme.Sample.Dark", "Theme.AppCompat"),
      ThemeStencil("Theme.Sample.Light", "Theme.AppCompat.Light")
  )

  override fun globalStyleItemGroups() = linkedSetOf(
      textSizes
  )
}
```

Leaving you with a generated themes XML resource file like this:

```xml
<?xml version="1.0" encoding="utf-8" standalone="no"?>
<resources>
  <style name="Theme.Sample.Dark" parent="Theme.AppCompat">
    <item name="textSizeSmall">12dp</item>
    <item name="textSizeMedium">16dp</item>
    <item name="textSizeLarge">20dp</item>
  </style>
  <style name="Theme.Sample.Light" parent="Theme.AppCompat.Light">
    <item name="textSizeSmall">12dp</item>
    <item name="textSizeMedium">16dp</item>
    <item name="textSizeLarge">20dp</item>
  </style>
</resources>
```

This may look like a lot of boilerplate for simple style item shared by two themes, but it scales quite well when you want to have many custom color, dimension, and other style items on _numerous_ app themes and custom theme attributes.

## Download

Stylist [![Maven Central](https://img.shields.io/maven-central/v/com.uber.stylist/stylist.svg)](https://mvnrepository.com/artifact/com.uber.stylist/stylist)
```gradle
classpath 'com.uber.stylist:stylist:0.0.1'
```

Stylist Core [![Maven Central](https://img.shields.io/maven-central/v/com.uber.stylist/stylist-core.svg)](https://mvnrepository.com/artifact/com.uber.stylist/stylist-core)
```gradle
classpath 'com.uber.stylist:stylist-core:0.0.1'
```

Stylist API [![Maven Central](https://img.shields.io/maven-central/v/com.uber.stylist/stylist-api.svg)](https://mvnrepository.com/artifact/com.uber.stylist/stylist-api)
```gradle
classpath 'com.uber.stylist:stylist-api:0.0.1'
```

## License

```
Copyright (C) 2018 Uber Technologies

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
