import { post } from "@/plugins/ajax";


/*
 * 新增评论
 */
export const AddComment = (param) => post("/api/ordercomment/addComment", param);

/*
 * 获取评论列表
 */
export const GetCommentList = () => post("/api/ordercomment/getCommentList");
