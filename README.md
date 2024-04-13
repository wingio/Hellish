<div align="center">

  <img src=".github/assets/logo.png" alt="Hellish logo" width="200px" />

   # Hellish
   An Android client for the Pointercrate demonlist</p>

   [![Stars](https://img.shields.io/github/stars/wingio/Hellish?style=for-the-badge&logo=github&logoColor=red&labelColor=black&color=red)](https://github.com/wingio/Hellish/stargazers)
   ![Repo size](https://img.shields.io/github/repo-size/wingio/Hellish?style=for-the-badge&logo=github&logoColor=red&labelColor=black&color=red)
   ![Kotlin](https://img.shields.io/github/languages/top/wingio/Hellish?style=for-the-badge&logo=kotlin&logoColor=black&label=%20&color=red)

  <br />

   | Light                                                                                       | Dark                                                                                      |
   |---------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
   | <img src=".github/assets/screenshot-light.jpg" alt="Light mode screenshot" width="200px" /> | <img src=".github/assets/screenshot-dark.jpg" alt="Dark mode screenshot" width="200px" /> |

</div>

Contribute
---
If you're unfamiliar with programming but would still like to help you can by doing one of the following:
- [Testing early builds and reporting issues](https://github.com/wingio/Hellish/issues/new)
- [Translating the app into your language](https://crowdin.com/project/hellish)

Otherwise if you do want to contribute code then it's preferred that you're familiar with the following technologies:
- Kotlin
- Android SDK
- Koin DI
- Jetpack Compose
  - Voyager
- The Pointercrate API
  - Ktor (Client)
  - Kotlinx Serialization

Build
---

#### Prerequisites
- [Git](https://git-scm.com/downloads)
- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Android SDK](https://developer.android.com/studio)

#### Instructions

1. Clone the repo
    - `git clone https://github.com/wingio/Hellish.git && cd Hellish`
2. Build the project
    - Linux: `chmod +x ./gradlew && gradlew assembleDebug`
    - Windows: `./gradlew assembleDebug`
3. Install on device
    - [Enable usb debugging](https://developer.android.com/studio/debug/dev-options) and plug in your phone
    - Run `adb install app/build/outputs/apk/debug/app-debug.apk`

License
---
Hellish is licensed under the GNU General Public License v3.0

[![GitHub License](https://img.shields.io/github/license/wingio/Hellish?style=for-the-badge&labelColor=black&color=red)](LICENSE)