package ThinkEat.mvc.Bean.Dao;


import ThinkEat.mvc.entity.EatRepo;
import ThinkEat.mvc.entity.TagData;

import java.util.List;
import java.util.Optional;

public interface TagDataDao {
    //新增
    int addTag(TagData tagData);
    //修改
    int updateTagByTagId(Integer tagId, TagData tagData);
    //刪除
    int deleteTag(Integer tagId);
    //以ID尋找單個Tag
    Optional<TagData> getTagByTagId(Integer tagId);
    //尋找所有Tag
    List<TagData> findAllTag();
}
