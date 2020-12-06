# MQ

基于 [JMCCC](https://github.com/to2mbn/JMCCC) 的Minecraft启动器。

本项目是为**Windows平台**下的**Java版**Minecraft游戏打造的*游戏启动器*。它基于“[JMCCC](https://github.com/to2mbn/JMCCC)”（即一个开源的Java语言针对Minecraft启动的工具），实现了启动游戏~~（并在游戏运行中维护游戏进程以及玩家在线状态）~~、下载游戏的主要功能，并通过不同平台的UI（现有JavaFX和HTML5）与用户交互。

## 协议 & 许可

本项目采用协议 [LGPL](../LICENSE.txt)。

## （构建）环境
本项目为主要使用IDEA（最旧版本为2020.1）开发的Java项目，使用Maven进行版本、依赖管理。  

请下载（克隆）整个仓库，并使用IDEA打开。然后使用Maven工具下载依赖。

如果你需要构建项目，你可能需要JRE8（及8以上）的运行时环境。另外，请确保环境中带有JavaFX环境。

------

不出意料，项目中应该存在有设置好的构建配置，命名为：withUI_ + UI平台名。以下是两个现有的配置：

|    配置名     |            主类             |
| :-----------: | :-------------------------: |
| withUI_JavaFX | com.MQ.UI.JavaFX.launcherUI |
|   withUI_H5   |   com.MQ.UI.H5.launcherUI   |

如果预期的配置不存在，请根据上表自行配置。

然后，你就可以选择相应的配置构建项目了。

