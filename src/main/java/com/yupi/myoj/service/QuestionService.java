package com.yupi.myoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.myoj.model.dto.question.QuestionQueryRequest;
import com.yupi.myoj.model.entity.Question;
import com.yupi.myoj.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.myoj.model.vo.QuestionManageVO;
import com.yupi.myoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author aabbc
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-03-15 15:15:55
*/
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取题目封装
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

    /**
     * 管理题目列表页面
     *
     * @param questionPage   题目分页
     * @param queryWrapper 查询条件
     * @return {@link Page}<{@link QuestionManageVO}>
     */
    Page<QuestionManageVO> listManageQuestionByPage(Page<Question> questionPage, QueryWrapper<Question> queryWrapper);
}
