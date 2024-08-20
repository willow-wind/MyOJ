// 发布
import { request } from "axios";

export function post(topic) {
  return request({
    url: "/post/create",
    method: "post",
    data: topic,
  });
}
