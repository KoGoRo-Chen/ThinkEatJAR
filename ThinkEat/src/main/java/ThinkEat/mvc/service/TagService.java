package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.TagDao;
import ThinkEat.mvc.model.dto.PricePageDto;
import ThinkEat.mvc.model.dto.TagPageDto;
import ThinkEat.mvc.model.entity.Price;
import ThinkEat.mvc.model.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagDao tagDao;
    private final ModelMapper modelMapper;

    @Autowired
    public TagService(TagDao tagDao, ModelMapper modelMapper) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
    }

    // 新增Tag
    @Transactional
    public Integer addTag(Tag tag) {
        tagDao.save(tag);
        return tag.getId();
    }

    // 以ID修改Tag
    public String updateTagByTagId(Integer tagId, String name) {
        Optional<Tag> tagOpt = tagDao.findById(tagId);
        if (tagOpt.isPresent()) {
            Tag tagToUpdate = tagOpt.get();
            tagToUpdate.setName(name);
            tagDao.save(tagToUpdate);
            return "修改成功";
        }
        return "找不到標籤";
    }

    // 以ID刪除Tag
    @Transactional
    public String deleteTag(Integer tagId) {
        Optional<Tag> tagOpt = tagDao.findById(tagId);
        if (tagOpt.isPresent()) {
            tagDao.delete(tagOpt.get());
            return "刪除成功";
        }
        return "找不到標籤";
    }

    // 以ID尋找單個Tag
    public Tag getTagById(Integer tagId) {
        Optional<Tag> tagOpt = tagDao.findById(tagId);
        if (tagOpt.isPresent()) {
            Tag tag = tagOpt.get();
            return tag;
        }
        return null;
    }

    // 尋找所有Tag
    public List<Tag> findAllTag() {
        List<Tag> tagList = tagDao.findAll();
        return tagList;
    }

    //尋找所有Tag(分頁)
    public TagPageDto getAllTagInPage(Pageable pageable) {
        Page<Tag> tagPage = tagDao.findAll(pageable);
        return new TagPageDto(tagPage);
    }
}