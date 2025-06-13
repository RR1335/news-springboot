package biz.baijing.service.impl;

import biz.baijing.mapper.ArticleMapper;
import biz.baijing.pojo.Article;
import biz.baijing.pojo.PageBean;
import biz.baijing.service.ArticleService;
import biz.baijing.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 新增文章
     * @param article
     */
    public void add(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        article.setCreateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) map.get("id");

        article.setCreateUser(loginUserId);

        articleMapper.add(article);

    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @param state
     * @return
     */
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {

        PageBean<Article> pageBean = new PageBean<>();

        // PageHelper 分页查询插件
        PageHelper.startPage(pageNum, pageSize);

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) map.get("id");

        List<Article> articlesList =  articleMapper.list( loginUserId, categoryId, state);

        Page<Article> articlePage = (Page<Article>) articlesList;

        pageBean.setTotal(articlePage.getTotal());
        pageBean.setItems(articlePage.getResult());

        return pageBean ;
    }
}
