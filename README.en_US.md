# Sparrow
_This page is machine-translated._

Based on [jmccc](https://github.com/to2mbn/JMCCC) Minecraft starter for

##Agreement
This project adopts the protocol [LGPL](license.txt)

JMCCC use agreement [MIT license](https://to2mbn.github.io/jmccc/LICENSE.txt)

##Characteristics
-Ability to start all versions of minecraft java client.

-Download and automatically install any version of minecraft java client.

-The visualization interface interaction is implemented by using JavaFX.

##QuickStart

###Build
This project is a java project mainly developed by idea, and Maven is used for dependency management.

Download (clone) the entire warehouse and open it using idea. Then synchronize using the Maven tool.

If you need to build a project, you may need the runtime environment for jdk8. Also, make sure that the environment has a JavaFX environment.

------
If not expected, there should be a set-up build configuration in the project named withUI+ UI platform name. The following are two existing configurations:

|Configuration name | master class|
| :-----------: | :-------------------------:|
| withUI_ JavaFX | com.Sparrow.UI.JavaFX.launcherUI |
|   withUI_ H5   |  com.Sparrow.UI.H5.launcherUI |

If the expected configuration does not exist, configure yourself according to the table above.

Then you can choose the appropriate configuration build project.

###Class &amp; Package &amp; module

####Package

com.Sparrow is the root path of the project package.
-com.Sparrow.Launcher (class) declares the way to start a game quickly for starters.

-com.Sparrow.Utils.minecraft (class) defines a class that contains minecraft game paths and boot methods and other tool methods encapsulated from com.Sparrow.launcher.

-com.Sparrow.Utils stores the various tool classes.

-com.Sparrow.Utils.dialog various dialog boxes (JavaFX Implementation).

-com.Sparrow.Utils.download encapsulates the download function in JMCC.

-com.Sparrow.Utils.pack package, export integration package (that is, zip compression package of game directory).

-com.Sparrow.UI stores the program GUI.

-Each package under it is a platform GUI. By default, there are launcherui classes under each package, which contains the program master class to start the GUI platform under the package.

####Module

Launcher: the core part of the initiator, encapsulates some classes and startup methods, and some UI unified tool classes. Each UI module should contain the launcher module.

UI_ H5: the implementation of initiator GUI under javafx+html5 has been abandoned.

UI_ JavaFX: the implementation of initiator GUI under javafx+fxml.

##Postscript

This project is based on an interface of interest to me [jmcc]（ https://github.com/to2mbn/JMCCC ）, the minecraft game initiator is mainly for learning purpose, and constantly explore the GUI implementation and project management in Java during the process of production.

If you are interested, it is very welcome to provide guidance and some valuable comments on this project. You can do this project at will, on the premise of complying with the open source agreement of this project.

The author has few time, and will develop it once or twice a week, so don't blame.

![ScreenShot_JavaFX](docs/Screenshot_JavaFX.png)