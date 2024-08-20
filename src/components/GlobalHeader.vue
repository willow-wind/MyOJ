<template>
  <a-row id="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
      <a-menu
        mode="horizontal"
        :selected-keys="selectedKeys"
        @menu-item-click="doMenuClick"
      >
        <a-menu-item
          key="0"
          :style="{ padding: 0, marginRight: '38px' }"
          disabled
        >
          <div class="title-bar">
            <img class="logo" src="../assets/nankai2.jpg" />
            <div class="title">遥岑编程</div>
          </div>
        </a-menu-item>
        <a-menu-item v-for="item in visibleRoutes" :key="item.path">
          {{ item.name }}
        </a-menu-item>
      </a-menu>
    </a-col>
    <a-col flex="100px">
      <a-dropdown trigger="hover">
        <template
          v-if="loginUserInfo.userRole as string !== ACCESS_ENUM.NOT_LOGIN"
        >
          <template v-if="loginUserInfo.userAvatar">
            <a-avatar>
              <img
                alt="avatar"
                :src="loginUserInfo.userAvatar"
                width="24px"
                height="24px"
              />
            </a-avatar>
            {{ loginUserInfo.userName }}
          </template>
          <template v-else>
            <a-avatar>
              <IconUser />
            </a-avatar>
            {{ loginUserInfo.userName }}
          </template>
        </template>
        <template v-else>
          <a-avatar :style="{ backgroundColor: '#168CFF' }"> 未登录</a-avatar>
        </template>
        <template #content>
          <template v-if="loginUserInfo.userRole !== ACCESS_ENUM.NOT_LOGIN">
            <a-doption>
              <template #icon>
                <icon-idcard />
              </template>
              <template #default>
                <a-anchor-link href="/user/info">个人信息</a-anchor-link>
              </template>
            </a-doption>
            <a-doption>
              <template #icon>
                <icon-poweroff />
              </template>
              <template #default>
                <a-anchor-link @click="logout">退出登陆</a-anchor-link>
              </template>
            </a-doption>
          </template>
          <template v-else>
            <a-doption>
              <template #icon>
                <icon-user />
              </template>
              <a-anchor-link href="/user/login">用户登录</a-anchor-link>
            </a-doption>
            <a-doption>
              <template #icon>
                <icon-user-add />
              </template>
              <a-anchor-link href="/user/register">用户注册</a-anchor-link>
            </a-doption>
          </template>
        </template>
      </a-dropdown>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import { routes } from "../router/routes";
import { useRoute, useRouter } from "vue-router";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import checkAccess from "@/access/checkAccess";
import ACCESS_ENUM from "@/access/accessEnum";
import {
  IconUser,
  IconIdcard,
  IconPoweroff,
  IconUserAdd,
} from "@arco-design/web-vue/es/icon";
import { LoginUserVO, UserControllerService } from "../../generated";

const router = useRouter();
const store = useStore();

// 展示在菜单的路由数组
const visibleRoutes = computed(() => {
  return routes.filter((item, index) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    // 根据权限过滤菜单
    if (
      !checkAccess(store.state.user.loginUser, item?.meta?.access as string)
    ) {
      return false;
    }
    return true;
  });
});

// 默认主页
const selectedKeys = ref(["/"]);

// 路由跳转后，更新选中的菜单项
router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});

console.log();

setTimeout(() => {
  store.dispatch("user/getLoginUser", {
    userName: "鱼皮管理员",
    userRole: ACCESS_ENUM.ADMIN,
  });
}, 3000);

const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};

const logout = async () => {
  const res = await UserControllerService.userLogoutUsingPost();
  if (res.code === 0) {
    await store.dispatch("user/userLogout");
    router.push({
      path: "/user/login",
      replace: true,
    });
  }
};

const loginUserInfo: LoginUserVO = computed(
  () => store.state.user?.loginUser
) as LoginUserVO;
</script>

<style scoped>
.title-bar {
  display: flex;
  align-items: center;
}

.title {
  font-size: 24px; /* 字体大小 */
  color: #ffff99; /* 字体颜色 */
  margin-left: 16px;
  font-weight: bold; /* 加粗 */
  text-shadow: 2px 2px 4px #000000; /* 阴影效果，参数分别为：水平偏移、垂直偏移、模糊半径、颜色 */
}

.logo {
  height: 48px;
}
</style>
