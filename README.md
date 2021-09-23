# ktXML
[![Kotlin](https://img.shields.io/badge/Kotlin-1.5.31-blue.svg)](http://kotlinlang.org)
[![CI](https://github.com/musichin/ktXML/actions/workflows/ci.yml/badge.svg)](https://github.com/musichin/ktXML/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/musichin/ktXML/branch/master/graph/badge.svg)](https://codecov.io/gh/musichin/ktXML)

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

## Serialization

### Serialize
```kotlin
val element = elementOf("book")
println(element.serialize())
```

### Deserialize
```kotlin
val element = "<book/>".deserialize()
```

## Binaries
```groovy
repositories {
    mavenCentral()
}

dependencies {
    compile 'de.musichin.ktxml:ktxml:x.y.z'
    
    // for serialization/deserialization with pull parser
    compile 'de.musichin.ktxml:ktxml-pull:x.y.z'
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

