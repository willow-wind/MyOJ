<template>
  <div id="postView">
    <div
      style="max-width: 800px; margin: 0 auto"
      label-align="left"
      auto-label-width
      author="Socrates"
      content="Comment body content."
      datetime="1 hour"
    >
      <h2>编写帖子</h2>
      <a-form :model="form" label-align="left">
        <a-form-item field="title" label="标题">
          <a-input v-model="form.title" placeholder="请输入标题" />
        </a-form-item>
        <!--        <a-form-item field="tags" label="标签">-->
        <!--          <a-input-tag-->
        <!--            v-model="form.tags"-->
        <!--            placeholder="请选择标签"-->
        <!--            allow-clear-->
        <!--          />-->
        <!--        </a-form-item>-->
        <a-form-item field="content" label="发起讨论">
          <MdEditor :value="form.content" :handle-change="onContentChange" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" style="min-width: 200px" @click="doSubmit"
            >发布
          </a-button>
        </a-form-item>
      </a-form>
    </div>

    <a-table
      style="max-width: 800px; margin: 0 auto"
      label-align="left"
      auto-label-width
      :ref="postRef"
      :columns="columns"
      :data="dataList"
      :pagination="{
        showTotal: true,
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total,
      }"
      @page-change="onPageChange"
    >
      <template #title="{ record }"> 标题{{ record.title }} </template>
      <template #content="{ record }"> 内容{{ record.content }} </template>
      <!--      <template #actions>-->
      <!--        <span class="action" key="heart" @click="onThumbNumChange">-->
      <!--          <span v-if="thumb">-->
      <!--            <IconHeartFill :style="{ color: '#f53f3f' }" />-->
      <!--          </span>-->
      <!--          <span v-else>-->
      <!--            <IconHeart />-->
      <!--          </span>-->
      <!--          {{ 83 + (thumb ? 1 : 0) }}-->
      <!--        </span>-->
      <!--        <span class="action" key="star" @click="onFavourNumChange">-->
      <!--          <span v-if="favour">-->
      <!--            <IconStarFill-->
      <!--              :style="{-->
      <!--                color: '#ffb400',-->
      <!--              }"-->
      <!--            />-->
      <!--          </span>-->
      <!--          <span v-else>-->
      <!--            <IconStar />-->
      <!--          </span>-->
      <!--          {{ 3 + (favour ? 1 : 0) }}-->
      <!--        </span>-->
      <!--        <span class="action" key="reply"> <IconMessage /> Reply </span>-->
      <!--      </template>-->
      <!--      <template #avatar>-->
      <!--        <a-avatar>-->
      <!--          <img-->
      <!--            alt="avatar"-->
      <!--            src="https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/3ee5f13fb09879ecb5185e440cef6eb9.png~tplv-uwbnlip3yd-webp.webp"-->
      <!--          />-->
      <!--        </a-avatar>-->
      <!--      </template>-->
      <!--      <a-comment-->
      <!--        align="right"-->
      <!--        avatar="https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/3ee5f13fb09879ecb5185e440cef6eb9.png~tplv-uwbnlip3yd-webp.webp"-->
      <!--      >-->
      <!--        <template #actions>-->
      <!--          <a-button key="0" type="secondary"> Cancel </a-button>-->
      <!--          <a-button key="1" type="primary"> Reply </a-button>-->
      <!--        </template>-->
      <!--        <template #content>-->
      <!--          <a-input placeholder="Here is you content." />-->
      <!--        </template>-->
      <!--      </a-comment>-->
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import {
  IconHeart,
  IconMessage,
  IconStar,
  IconStarFill,
  IconHeartFill,
} from "@arco-design/web-vue/es/icon";
import {
  PostVO,
  PostControllerService,
  PostQueryRequest,
} from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRoute } from "vue-router";
import MdEditor from "@/components/MdEditor.vue";
import { valueOf } from "axios";

const route = useRoute();

let form = ref({
  title: "",
  tags: [],
  content: "",
  thumbNum: 0,
  favourNum: 0,
});

const postRef = ref();
const dataList = ref([]);
const total = ref(0);
const searchParams = ref<PostQueryRequest>({
  title: "",
  content: "",
  pageSize: 10,
  current: 1,
});

const loadData = async () => {
  const res = await PostControllerService.listPostVoByPageUsingPost(
    searchParams.value
  );
  if (res.code === 0) {
    dataList.value = res.data.records;
    total.value = res.data.total;
  } else {
    message.error("加载失败" + res.message);
  }
};

watchEffect(() => {
  loadData();
});

onMounted(() => {
  loadData();
});

const columns = [
  {
    title: "标题",
    dataIndex: "title",
  },
  {
    title: "内容",
    dataIndex: "content",
  },
];

const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page,
  };
};

// export default {
//   components: {
//     MdEditor,
//     IconHeart,
//     IconMessage,
//     IconStar,
//     IconStarFill,
//     IconHeartFill,
//   },
//   setup() {
//     const like = ref(false);
//     const star = ref(false);
//     const onLikeChange = () => {
//       like.value = !like.value;
//     };
//     const onStarChange = () => {
//       star.value = !star.value;
//     };
//
//     return {
//       like,
//       star,
//       onLikeChange,
//       onStarChange,
//     };
//   },
// };

const thumb = ref(false);
const favour = ref(false);

const doSubmit = async () => {
  console.log(form.value);
  const res = await PostControllerService.addPostUsingPost(form.value);
  if (res.code === 0) {
    message.success("发布成功");
  } else {
    message.error("发布失败" + res.message);
  }
};

const onThumbNumChange = () => {
  thumb.value = !thumb.value;
};

const onFavourNumChange = () => {
  favour.value = !favour.value;
};

const onContentChange = (value: string) => {
  form.value.content = value;
};
</script>
<style scoped>
.action {
  display: inline-block;
  padding: 0 4px;
  color: var(--color-text-1);
  line-height: 24px;
  background: transparent;
  border-radius: 2px;
  cursor: pointer;
  transition: all 0.1s ease;
}
.action:hover {
  background: var(--color-fill-3);
}
</style>
