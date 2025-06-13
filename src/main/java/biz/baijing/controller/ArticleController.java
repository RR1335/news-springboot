package biz.baijing.controller;

import biz.baijing.pojo.Article;
import biz.baijing.pojo.Result;
import biz.baijing.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@Validated
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加文章
     * @param article
     * @return
     */
    @PostMapping
    public Result add(@RequestBody @Validated Article article) {

        articleService.add(article);

        return Result.success();
    }

}
