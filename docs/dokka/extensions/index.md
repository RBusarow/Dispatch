[extensions](./index.md)

## Contents

* [Minimum Gradle Config](#minimum-gradle-config)

## Minimum Gradle Config

Click to expand a field.

Add to your module's `build.gradle`:

``` groovy
repositories {
  mavenCentral()
}

dependencies {

  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3"
  implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
  implementation "com.rickbusarow.dispatch:dispatch-extensions:1.0.0-beta03"
}
```

Add to your module's `build.gradle.kts`:

``` kotlin
repositories {
  mavenCentral()
}

dependencies {

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3")
  implementation("com.rickbusarow.dispatch:dispatch-extensions:1.0.0-beta03")
}
```

### Packages

| Name | Summary |
|---|---|
| [dispatch.extensions.channel](dispatch.extensions.channel/index.md) |  |
| [dispatch.extensions.flow](dispatch.extensions.flow/index.md) |  |

### Index

[All Types](alltypes/index.md)