package ThinkEat.mvc.OldBean.dao;

import ThinkEat.mvc.OldBean.TagData;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TagDataDaoImplInMemory implements TagDataDao {
    private static List<TagData> tagsSum = new CopyOnWriteArrayList<>();
    private static AtomicInteger atomicTagId = new AtomicInteger(0);

    // 構造函數
    public TagDataDaoImplInMemory() {
        tagsSum.add(new TagData(atomicTagId.incrementAndGet(), "日式"));
        tagsSum.add(new TagData(atomicTagId.incrementAndGet(), "中式"));
        tagsSum.add(new TagData(atomicTagId.incrementAndGet(), "西式"));
        tagsSum.add(new TagData(atomicTagId.incrementAndGet(), "下午茶"));
        tagsSum.add(new TagData(atomicTagId.incrementAndGet(), "手搖杯"));
        tagsSum.add(new TagData(atomicTagId.incrementAndGet(), "辣"));
    }

    @Override
    public int addTag(TagData tagData) {
        tagData.setId(atomicTagId.incrementAndGet());
        tagsSum.add(tagData);
        return 1;
    }

    @Override
    public int updateTagByTagId(Integer tagId, TagData updatedTagData) {
        Optional<TagData> tagDataToUpdate = tagsSum.stream()
                .filter(tagData -> tagData.getId().equals(tagId))
                .findFirst();

        if (tagDataToUpdate.isPresent()) {
            // 找到相應的標籤資料，進行更新
            int index = tagsSum.indexOf(tagDataToUpdate.get());
            tagsSum.set(index, updatedTagData);
            return 1;
        } else {
            // 未找到匹配的標籤 ID，返回 0 表示未進行任何更新
            return 0;
        }
    }

    @Override
    public int deleteTag(Integer tagId) {
        tagsSum.removeIf(tagData -> tagData.getId().equals(tagId));
        return 1;
    }

    @Override
    public Optional<TagData> getTagByTagId(Integer tagId) {
        return tagsSum.stream().filter(tagData -> tagData.getId().equals(tagId)).findFirst();
    }

    @Override
    public List<TagData> findAllTag() {
        return new ArrayList<>(tagsSum);
    }
}
