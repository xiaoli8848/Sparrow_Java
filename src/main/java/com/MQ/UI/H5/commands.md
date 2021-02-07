# Commands

- verify [gamePath]

  ​	检查给定的gamePath路径是否为Minecraft游戏路径。

  ​	***gamePath***: 要检查的路径（为.minecraft路径，如"D://.minecraft"）

  ​	**返回**：如果该路径下存储有若干个版本的我的世界，形如"versionPath;versionPath"的字符串，表示每个版本的版本信息路径，如"D://.minecraft//versions//1.9"。

- launch_offline [rootDir, version, playerName, nativesFC, minMemory, maxMemory, windowWidth, windowHeight, serverURL]

  ​	使用给定的参数离线启动游戏。

  ```java
  /**
  * @param rootDir      游戏根路径（即“.minecraft”文件夹的路径）
  * @param version      要启动的版本（如1.8）
  * @param playerName   玩家名
  * @param nativesFC    是否执行natives文件夹完整性的快速检查
  * @param minMemory    游戏可以使用的最小内存
  * @param maxMemory    游戏可以使用的最大内存
  * @param windowWidth  游戏窗口宽度
  * @param windowHeight 游戏窗口高度
  * @param serverURL    指定游戏启动后要进入的服务器的URL地址。可为空，则游戏启动后不进入任何服务器。
  */
  ```

  **返回**：如果启动正常，则返回1；否则返回0。