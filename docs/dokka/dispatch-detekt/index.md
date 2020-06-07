[dispatch-detekt](./index.md)

## Contents

* [Rules](#rules)
* [Setup](#setup)
  * [Adding new dependencies](#adding-new-dependencies)
  * [Configuration](#configuration)
  * [Gradle Daemon bug](#gradle-daemon-bug)

## Rules

| **Name**                      | **Description**
| ----------------------------  | --------------- |
| [AndroidxLifecycleScopeUsage](https://rbusarow.github.io/Dispatch/dispatch-detekt//dispatch.detekt.rules/-androidx-lifecycle-scope-usage/index.html) | Looks for accidental usage of the [androidx lifecyclescope](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-master-dev:lifecycle/lifecycle-runtime-ktx/src/main/java/androidx/lifecycle/Lifecycle.kt;l=44) extension.
| [HardCodedDispatcher](https://rbusarow.github.io/Dispatch/dispatch-detekt//dispatch.detekt.rules/-hard-coded-dispatcher/index.html)    | Detects use of a hard-coded reference to the [kotlinx.coroutines.Dispatchers](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/index.html) singleton.

## Setup

If you don't already have [Detekt](https://detekt.github.io/detekt) set up in your project, follow [the official quick start guide](https://detekt.github.io/detekt/#quick-start-with-gradle).

After that is working, you need to add dependencies for the Detekt CLI and these extension rules to each module which will be analyzed.  The easiest way to do this is to apply them from the **root project**-level gradle file.

### Adding new dependencies

In root project-level `build.gradle` or `build.gradle.kts`:

``` kotlin
allprojects {
  dependencies {
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:1.9.1")
    
    detektPlugins("com.rickbusarow.dispatch:dispatch-detekt:1.0.0-beta03")
  }
}
```

### Configuration

After adding the dependencies, you'll want to add parameters to your detekt config .yml file.

If you don't already have a config file, you can create one by invoking:

`./gradlew detektGenerateConfig`

Then, add the following to the bottom of the `detekt-config.yml` file:

``` yaml
dispatch:
  active: true                             # disables all dispatch checks
  AndroidxLifecycleScopeUsage:             # incorrect lifecycleScope
    active: true
  HardCodedDispatcher:                     # finds usage of Dispatchers.______
    active: true
    allowDefaultDispatcher: false          # if true, Detekt will ignore all usage of Dispatchers.Default
    allowIODispatcher: false               # if true, Detekt will ignore all usage of Dispatchers.IO
    allowMainDispatcher: false             # if true, Detekt will ignore all usage of Dispatchers.Main
    allowMainImmediateDispatcher: false    # if true, Detekt will ignore all usage of Dispatchers.Main.immediate
    allowUnconfinedDispatcher: false       # if true, Detekt will ignore all usage of Dispatchers.Unconfined
```

### Gradle Daemon bug

There is an [issue](https://github.com/detekt/detekt/issues/2582) with ClassLoader caching which may cause issues the first time running Detekt.  The workaround is to execute `./gradlew --stop` once via command line.  You should only ever need to do this one time, if at all.  The fix for this has already been merged into Detekt.

### Packages

| Name | Summary |
|---|---|
| [dispatch.detekt.rules](dispatch.detekt.rules/index.md) |  |

### Index

[All Types](alltypes/index.md)