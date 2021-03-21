# Commands

## 说明

HTML5 UI中，Java端会扮演本地web服务器处理来自UI的post（websocket）请求。

请求数据格式为：**data:{command arg;arg;arg;...}**

以下是对command及其args的枚举说明。

*rootDir默认须以路径分隔符结束。*

## 指令枚举

### 启动&环境

#### verify [gamePath]

检查给定的gamePath路径是否为Minecraft游戏路径。

***gamePath***: 要检查的路径（为.minecraft路径，如"D://.minecraft"）。

**返回**：如果该路径下存储有若干个版本的我的世界，返回以下格式的json文本；否则返回0。

```json
[
  {
        "corePath":"GameJarPath",
        "setting":"independent/global",
        "independentSettings":{IndependentSettings},
        "version":"GameVersion"
    },
    {
        ...
    },
    ...
]
```

*corePath*：该版本的jar文件路径。

*setting*：该版本套用的参数设置模式。有"independent"和"global"两种值，前者指定该版本有指定的参数设置配置文件（通过"independentSettings"键包含所有自定义的参数设置），后者指定该版本套用缺省参数值。

*independentSettings*：值为一个JSONArray，包含所有配置文件指定的参数键及其值。仅在setting值为independent时可用。

*version*：该版本的版本号。

示例：

```json
[
    {
        "corePath":"D:\\.minecraft\\versions\\1.9\\1.9.jar",
        "independentSettings":"{\"versionName\":\"haha\"}",
        "version":"1.9",
        "setting":"independent"
    }
]
```

#### launch_offline [rootDir, version, playerName, nativesFC, minMemory, maxMemory, windowWidth, windowHeight, serverURL]

使用给定的参数离线启动游戏。

```java
/**
* @param rootDir      游戏根路径（即“.minecraft”文件夹的路径）
* @param version      要启动的版本（如1.8）
* @param playerName   玩家名
* @param nativesFC    是否执行natives文件夹完整性的快速检查
* @param minMemory    游戏可以使用的最小内存（0表示自动）
* @param maxMemory    游戏可以使用的最大内存（0表示自动）
* @param windowWidth  游戏窗口宽度（0为全屏）
* @param windowHeight 游戏窗口高度（0为全屏）
* @param serverURL    指定游戏启动后要进入的服务器的URL地址。可为空，则游戏启动后不进入任何服务器。
*/
```

**返回**：如果启动正常，则返回0；否则返回1。

#### launch_online [rootDir, version, debugPrint, nativesFC, minMemory, maxMemory, windowWidth, windowHeight, serverURL]

```java
/**
 * @param rootDir      游戏根路径（即“.minecraft”文件夹的路径）
 * @param debugPrint   是否将调试信息输出
 * @param nativesFC    是否执行natives文件夹完整性的快速检查
 * @param minMemory    游戏可以使用的最小内存
 * @param maxMemory    游戏可以使用的最大内存
 * @param windowWidth  游戏窗口宽度
 * @param windowHeight 游戏窗口高度
 * @param serverURL    指定游戏启动后要进入的服务器的URL地址。可为空，则游戏启动后不进入任何服务器。
 * @author XiaoLi8848, 1662423349@qq.com
 */
```

#### appendProperties [rootDir, version, key, value]

向对应的游戏版本加入对应的配置信息。如果已存在，则更新其值。

***rootDir***：要加入的游戏版本对应的".minecraft"文件夹。

***version***：要加入的游戏版本的版本号。

***key***：要加入的配置信息的键。

***value***：要加入的配置信息的值。

**返回**：如果成功则返回0，否则返回1。

### 实用功能

#### packGame [gamePath, zipPath]

打包指定目录下的游戏到指定的zip文件。

***gamePath***：要打包的游戏的路径（为.minecraft路径，如"D://.minecraft"）。

***zipPath***：要打包到的zip文件位置（指向.zip文件，该文件必须不存在，如"D://pack.zip"）。

**返回**：如果打包成功则返回0；否则如果要打包的游戏的路径无效，则返回1，否则返回2。

#### unpackGame [zipPath, toPath]

解压指定的游戏zip包到指定目录。

***zipPath***：要解压的游戏zip包的路径。

***toPath***：要解压到的目录。

**返回**：如果解压成功则返回0；否则如果要解压的zip包路径或要解压到的路径无效，则返回1，否则返回2。

### 窗体

#### close

关闭程序。

#### minimize

最小化窗体。