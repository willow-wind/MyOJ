<template>
  <div id="manageQuestionView">
    <a-table
      :ref="tableRef"
      :columns="columns"
      :data="dataList"
      :pagination="{
        showTotal: true,
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total,
      }"
      :data-source="dataSource"
      @page-change="onPageChange"
    >
      <template #tags="{ record }">
        <a-space wrap>
          <a-tag
            v-for="(tag, index) of JSON.parse(record.tags)"
            :key="index"
            color="arcoblue"
            >{{ tag }}
          </a-tag>
        </a-space>
      </template>
      <template #optional="{ record }">
        <a-space>
          <a-button type="primary" @click="doUpdate(record)"> 修改</a-button>
          <a-button status="danger" @click="doDelete(record)">删除</a-button>
        </a-space>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import {
  Page_Question_,
  Question,
  QuestionControllerService,
  QuestionQueryRequest,
  QuestionVO,
} from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import * as querystring from "querystring";
import { useRouter } from "vue-router";

const tableRef = ref();
const question = ref<QuestionVO>();
const dataList = ref([]);
const total = ref(0);
const searchParams = ref<QuestionQueryRequest>({
  pageSize: 10,
  current: 1,
});

const loadData = async () => {
  const res = await QuestionControllerService.listManageQuestionByPageUsingPost(
    searchParams.value
  );
  if (res.code === 0) {
    dataList.value = res.data.records;
    total.value = res.data.total;
  } else {
    message.error("加载失败，" + res.message);
  }
};

/**
 * 监听 searchParams 变量，改变时触发页面的重新加载
 */
watchEffect(() => {
  loadData();
});

/**
 * 页面加载时，请求数据
 */
onMounted(() => {
  loadData();
});

// {id: "1", title: "A+ D", content: "新的题目内容", tags: "["二叉树"]", answer: "新的答案", submitNum: 0,…}

const columns = [
  {
    title: "id",
    dataIndex: "id",
    width: 200,
  },
  {
    title: "标题",
    dataIndex: "title",
    ellipsis: true,
    tooltip: true,
    width: 200,
  },
  {
    title: "标签",
    dataIndex: "tags",
    slotName: "tags",
    width: 300,
  },
  {
    title: "提交数",
    dataIndex: "submitNum",
    width: 100,
  },
  {
    title: "通过数",
    dataIndex: "acceptedNum",
    width: 100,
  },
  {
    title: "判题配置",
    dataIndex: "judgeConfig",
    slots: { customRender: "judgeConfig" },
    children: [
      {
        title: "时间限制",
        dataIndex: "timeLimit",
        width: 100,
      },
      {
        title: "内存限制",
        dataIndex: "memoryLimit",
        width: 100,
      },
      {
        title: "堆栈限制",
        dataIndex: "stackLimit",
        width: 100,
      },
    ],
    width: 300,
  },
  {
    title: "创建用户",
    dataIndex: "userName",
    ellipsis: true,
    tooltip: true,
    width: 100,
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    width: 170,
  },
  {
    title: "操作",
    slotName: "optional",
    width: 170,
  },
];

const dataSource = [
  {
    key: "1",
    judgeConfig: {
      timeLimit: 1000,
      memoryLimit: 1000,
      stackLimit: 1000,
    },
    // ... 其他数据项
  },
];

const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page,
  };
};

const doDelete = async (question: Question) => {
  const res = await QuestionControllerService.deleteQuestionUsingPost({
    id: question.id,
  });
  if (res.code === 0) {
    message.success("删除成功");
    await loadData();
  } else {
    message.error("删除失败");
  }
};

const router = useRouter();

const doUpdate = (question: Question) => {
  router.push({
    path: "/update/question",
    query: {
      id: question.id,
    },
  });
};
</script>

<style scoped>
#manageQuestionView {
}
</style>
