# MavenArtifacts

<!-- Plugin description -->
This Plugin is an inspection tool designed to detect the use of '.hashCode()' from a URL instance and add prior to the method call another call to transform the URL into an URI to avoid the problems of using '.hashCode()' from an URL instance.
<!-- Plugin description end -->

## Installation

- Manually:

  Download the [latest release](https://github.com/alexdumitriu2001/MavenArtifacts/releases/latest) and run the Gradle 'Run Plugin' command. In order to test the functionality yourself: Create a URL instance in psvm, then apply '.hashCode()' to it. A warning with a functional quick fix will pop up.


---
Plugin based on the [IntelliJ Platform Plugin Template][template].
This plugin was realised for the purpose of a coding test.
The following repository was used as a start point towards the inspection plugin.
https://github.com/JetBrains/intellij-sdk-code-samples/tree/main/comparing_string_references_inspection

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
