### ApkTool [2.9.2][1] for Android

[![](https://jitpack.io/v/timscriptov/android-apktool.svg)](https://jitpack.io/#timscriptov/android-apktool)

# Screenshot
| ![Decode](/ART/screenshot_1.png) | ![Build](/ART/screenshot_2.png) |
|----------------------------------|---------------------------------|

[1]: https://github.com/iBotPeaches/Apktool/commit/69914eb5966490f8b987e4072746e026de23218e

## Add it in your root build.gradle at the end of repositories:
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

## Add the dependency
```groovy
dependencies {
    implementation("com.github.timscriptov:android-apktool:Tag")
}
```
