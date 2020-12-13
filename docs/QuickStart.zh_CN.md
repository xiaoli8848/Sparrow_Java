# MQ

基于 [JMCCC](https://github.com/to2mbn/JMCCC) 的Minecraft启动器。

本项目是为**Windows平台**下的**Java版**Minecraft游戏打造的*游戏启动器*。它是一个开源的Java语言针对Minecraft启动的工具），实现了启动游戏，并通过不同平台的UI（现可使用的平台是JavaFX）与用户交互。

## 协议 & 许可

本项目采用[LGPL](../LICENSE.txt)协议。

## （构建）环境
本项目为主要使用IDEA（最旧版本为2020.1，推荐版本为2020.3）开发的Java项目，使用Maven进行依赖管理。  

请下载（克隆）整个仓库，并使用IDEA打开。然后使用Maven工具下载依赖。

如果你需要构建项目，你可能需要最低JDK8，最高JDK15的运行时环境。另外，请确保环境中带有JavaFX环境。

------

如果不出意料，项目中应存在有设置好的构建配置，命名为：withUI_ + UI平台名。以下是两个现有的配置：

|    配置名     |            主类             |                VM参数（如果JavaFX环境未预置）                |
| :-----------: | :-------------------------: | :----------------------------------------------------------: |
| withUI_JavaFX | com.MQ.UI.JavaFX.launcherUI | --module-path "请填入你安装的JavaFX的lib文件夹路径" --add-modules=javafx.controls,javafx.fxml,javafx.web |
|   withUI_H5   |   com.MQ.UI.H5.launcherUI   | --module-path "请填入你安装的JavaFX的lib文件夹路径" --add-modules=javafx.controls,javafx.fxml,javafx.web |

如果预期的配置不存在，请根据上表自行配置。

然后，你就可以选择相应的配置构建项目了。

