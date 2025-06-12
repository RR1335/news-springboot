package biz.baijing.mapper;

import biz.baijing.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 新增分类
     * @param category
     */
    @Insert("insert into category(category_name, category_alias, create_user, create_time, update_time) " +
            "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    /**
     * 查询分类列表根据用户Id
     * @param userId
     * @return
     */
    @Select("select * from category where create_user = #{userId}")
    List<Category> listById(Integer userId);

    /**
     * 根据Id查询详情，登录用户创建的
     * @param id
     * @param userId
     * @return
     */
    @Select("select * from category where create_user = #{userId} && id = #{id}")
    Category findById(Integer id, Integer userId);

    /**
     * 更新分类
     * @param category
     */
    @Update("update category set  category_name = #{categoryName} , category_alias = #{categoryAlias}, update_time = #{updateTime} " +
            "where id = #{id} ")
    void update(Category category);
}
