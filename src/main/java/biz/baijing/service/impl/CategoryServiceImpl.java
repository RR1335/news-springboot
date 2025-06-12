package biz.baijing.service.impl;

import biz.baijing.mapper.CategoryMapper;
import biz.baijing.pojo.Category;
import biz.baijing.service.CategoryService;
import biz.baijing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     * @param category
     */
    public void add(Category category) {
        // 创建人 ID ， 更新时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) map.get("id");
        category.setCreateUser(loginUserId);

        // alias 不可为空格
        if (category.getCategoryAlias().isBlank()) {
            category.setCategoryAlias(category.getCategoryName());
        }

        categoryMapper.add(category);
    }
}
