# Javascript Hacks for Blackboard

Javascript Hacks for Blackboard (a.k.a JS Hacks) is a building block which allows for the injection of arbitrary HTML/Javascript into many pages in Blackboard, providing a platform to modify Blackboard in ways not easily possible before.

[See the Wiki](https://github.com/AllTheDucks/jshack-v1/wiki) for more information, or to download some prebuilt packages.

# JSHack doesn't use Log4J

JS Hack uses SLF4J/Logback for logging. Libraries utilised by the tool which use Log4J have the logs piped into SLF4J using log4j-over-slf4j. **This means that it is not vulnerable to any Log4J vulnerabilities.**


# Java 21 Support #

Support for Java 21 was added in version 1.1.0.

The work required to support for Java 21 was sponsored by [Università Bocconi Milano](https://www.unibocconi.it/en).