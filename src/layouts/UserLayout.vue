<template>
  <div id="userLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <a-space>
          <img src="../assets/nankai2.jpg" class="logo" />
          <div class="title">遥岑编程</div>
        </a-space>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        <a target="_blank"> 在线编程实验平台 </a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<style scoped>
.title {
  font-size: 24px; /* 字体大小 */
  color: #ffff99; /* 字体颜色 */
  margin-left: 16px;
  font-weight: bold; /* 加粗 */
  text-shadow: 2px 2px 4px #000000; /* 阴影效果，参数分别为：水平偏移、垂直偏移、模糊半径、颜色 */
}

#userLayout {
  text-align: center;
  background: url("https://gw.alipayobjects.com/zos/rmsportal/FfdJeJRQWjEeGTpqgBKj.png")
    0% 0% / 100% 100%;
}

#userLayout .logo {
  width: 64px;
  height: 64px;
}

#userLayout .header {
  margin-top: 16px;
}

#userLayout .content {
  margin-bottom: 16px;
  padding: 20px;
}

#userLayout .footer {
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
<script></script>
