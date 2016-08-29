# ktXML [![Kotlin](https://img.shields.io/badge/Kotlin-1.0.3-blue.svg)](http://kotlinlang.org) [![Build Status](https://travis-ci.org/musichin/ktXML.svg?branch=master)](https://travis-ci.org/musichin/ktXML) [![codecov](https://codecov.io/gh/musichin/ktXML/branch/master/graph/badge.svg)](https://codecov.io/gh/musichin/ktXML) [![jcenter](https://api.bintray.com/packages/musichin/maven/ktXML/images/download.svg)](https://bintray.com/musichin/maven/ktXML/_latestVersion)
Simple XML processing library with immutable data objects.

## Create XML Object
```kotlin
val element = elementOf("Book") {
    element("chapter1")
    element("chapter2") {
        text("Lorem ipsum")
    }
}
```

## Serialize
```kotlin
val element = elementOf("book")
println(element.serialize())
```

## Deserialize
```kotlin
val element = "<book/>".deserialize()
```

## Binaries
```groovy
repositories {
    maven { url 'https://bintray.com/musichin/maven' }
}

dependencies {
    compile 'com.github.musichin.ktxml:ktxml:x.y.z'
    
    // for serialization/deserialization with pull parser
    compile 'com.github.musichin.ktxml:ktxml-pull:x.y.z'
}
```

## License

    Copyright (C) 2016 Anton Musichin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

