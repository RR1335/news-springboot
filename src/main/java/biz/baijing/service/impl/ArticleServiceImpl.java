package biz.baijing.service.impl;

import biz.baijing.mapper.ArticleMapper;
import biz.baijing.pojo.Article;
import biz.baijing.service.ArticleService;
import biz.baijing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
