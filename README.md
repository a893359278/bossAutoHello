### boss 直聘自动向牛人打招呼

java Swing 项目

idea 需要安装 lombok 插件


### 使用
1. 启动项目后，会出现一个登录框
![登录框](image/login.png)

2. 拿到在 boss 直聘后台的 cookie
![cookie](image/cookie.png)

3. 登录后的页面长这样
![cookie](image/main.png)


### 如何修改每次打招呼的睡眠时间？
+ 默认 [20,40) 秒  打一次招呼.  
+ 间隔 60s 翻页.

设置系统环境变量修改上述行为
```properties
boss.hello.sleepStart=20
boss.hello.sleepEnd=40
boss.heelo.sleepPage=60
```