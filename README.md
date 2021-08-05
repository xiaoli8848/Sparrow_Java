# Sparrow

基于 [JMCCC](https://github.com/to2mbn/JMCCC) 的Minecraft启动器

## 协议

本项目采用协议 [LGPL](LICENSE.txt).

（JMCCC使用协议 [MIT license](https://to2mbn.github.io/jmccc/LICENSE.txt)）

## 特性

- 能够启动所有版本的 Minecraft Java版 客户端。

- 下载并自动安装任何版本的 Minecraft Java版 客户端。

- 使用JavaFX实现可视化界面交互。

## 快速入门

### 构建

本项目为主要使用IDEA开发的Java项目，使用Maven进行依赖管理。

请下载（克隆）整个仓库，并使用IDEA打开。然后使用Maven工具同步。

如果你需要构建项目，你可能需要JDK8的运行时环境。另外，请确保环境中带有JavaFX环境。

------

如果不出意料，项目中应存在有设置好的构建配置，命名为：withUI_ + UI平台名。以下是两个现有的配置：

|    配置名     |            主类             |
| :-----------: | :-------------------------:|
| withUI_JavaFX | com.Sparrow.UI.JavaFX.launcherUI |
|   withUI_H5   |  com.Sparrow.UI.H5.launcherUI |

如果预期的配置不存在，请根据上表自行配置。

然后，你就可以选择相应的配置构建项目了。

### 类 & 包 & 模块

#### 包

com.Sparrow为本项目包根路径。

- com.Sparrow.launcher（类） 声明了启动器的快速启动游戏的方法。
- com.Sparrow.Utils.Minecraft（类） 定义了一个类，包含Minecraft游戏路径和封装自 com.Sparrow.launcher 的启动方法和其它工具方法。
- com.Sparrow.Utils 存储了各工具类。
    - com.Sparrow.Utils.dialog 各类对话框（JavaFX实现）。
    - com.Sparrow.Utils.Download 封装JMCCC中的下载功能。
    - com.Sparrow.Utils.pack 打包、导出整合包（即游戏目录的zip压缩包）。
- com.Sparrow.com.Sparrow.UI 存储了程序GUI。
    - 其下的每个包都是一个平台的GUI。并且，默认地，每个包下都会有 launcherUI 类，这其中包含程序主类，用于启动该包下的GUI平台。

#### 模块

Launcher：启动器核心部分，封装一些类和启动方法，以及一些UI统一要用到的工具类。每个UI模块都要包含Launcher模块。

UI_H5：启动器GUI在JavaFX+HTML5下的实现，已弃用。

UI_JavaFX：启动器GUI在JavaFX+FXML下的实现。

## 后记

本项目是基于一个我感兴趣的接口 [JMCCC](https://github.com/to2mbn/JMCCC) ，制作的Minecraft游戏启动器，主要是学习目的，在制作过程中不断摸索在Java上的GUI实现、项目管理。

如果你有兴趣，非常欢迎为本项目进行指导，提出一些宝贵意见。你完全可以在遵守本项目的开源协议的前提下随意搞一搞这个项目。

作者时间不多，一周会开发一两次，所以请勿见怪。

![屏幕截图_JavaFX](docs/Screenshot_JavaFX.png)