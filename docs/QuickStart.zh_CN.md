# MQ

基于 [JMCCC](https://github.com/to2mbn/JMCCC) 的Minecraft启动器。

本项目是为**Windows平台**下的**Java版**Minecraft游戏打造的*游戏启动器*。它是一个开源的Java语言针对Minecraft启动的工具），实现了启动游戏，并通过不同平台的UI（现可使用的平台是JavaFX）与用户交互。

## 协议 & 许可

本项目采用[LGPL](../LICENSE.txt)协议。
_声明：本项目为完全免费开源项目，所使用的任何插件、依赖等，均为学习研究用途。_

## （构建）环境
本项目为主要使用IDEA（2020.1及以上）开发的Java项目，使用Maven进行依赖管理。  

请下载（克隆）整个仓库，并使用IDEA打开。然后使用Maven工具下载依赖。

如果你需要构建项目，你可能需要JDK8的运行时环境。另外，请确保环境中带有JavaFX环境。

------

如果不出意料，项目中应存在有设置好的构建配置，命名为：withUI_ + UI平台名。以下是两个现有的配置：

|    配置名     |            主类             |
| :-----------: | :-------------------------:|
| withUI_JavaFX | com.MQ.UI.JavaFX.launcherUI |
|   withUI_H5   |   com.MQ.UI.H5.launcherUI |

如果预期的配置不存在，请根据上表自行配置。

然后，你就可以选择相应的配置构建项目了。

## 类&包

com.MQ为本项目包根路径。

- com.MQ.launcher（类） 声明了启动器的快速启动游戏的方法。
- com.MQ.Minecraft（类）  定义了一个类，包含Minecraft游戏路径和封装自 com.MQ.launcher 的启动方法和其它工具方法。
- com.MQ.Tools 存储了各工具类。
  - com.MQ.Tools.dialog 各类对话框（JavaFX实现）。
  - com.MQ.Tools.Download 封装JMCCC中的下载功能。
  - com.MQ.Tools.pack 打包、导出整合包（即游戏目录的zip压缩包）。
  - com.MQ.Tools.SystemPlatform 用于与系统组件交互（如发出消息通知，打开文件）。
- com.MQ.UI 存储了程序GUI。
  - 其下的每个包都是一个平台的GUI。并且，默认地，每个包下都会有 launcherUI 类，这其中包含程序主类，用于启动该包下的GUI平台。
