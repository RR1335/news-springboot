package biz.baijing.service;


import biz.baijing.pojo.Article;
import biz.baijing.pojo.PageBean;

public interface ArticleService {
    /**
     * 新增文章
     * @param article
     */
    void add(Article article);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @param state
     * @return
     */
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
