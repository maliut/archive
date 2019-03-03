# 高级 Web 技术 - 多人 VR 环境 Demo
一个简单的多人在线虚拟环境 Demo ，采用 Three.js 和 Socket.io 实现。场景部分修改自 Three.js 的 PointerLockControl Demo。

实现了多人加入同一场景、同步位置信息、在线聊天等功能，可供参考。

代码实现非常简陋，并没有一个优雅的架构，只是为了方便大家快速参考核心功能的实现。

## 如何运行
代码分为服务端和客户端两部分。方便起见直接跑在 localhost 上。

服务端：
``` bash
cd server
npm install socket.io
node app.js
```

客户端：

直接在浏览器中打开 index.html 即可。

可以多开这个页面以观察多人联机的效果。