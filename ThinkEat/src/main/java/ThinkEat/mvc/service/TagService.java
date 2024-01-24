package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.TagDao;
import ThinkEat.mvc.model.dto.TagDto;
import ThinkEat.mvc.model.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void updateTagByTagId(Integer tagId, String name) {
        Optional<Tag> tagOpt = tagDao.findById(tagId);
        if (tagOpt.isPresent()) {
            Tag tagToUpdate = tagOpt.get();
            tagToUpdate.setName(name);
            tagDao.save(tagToUpdate);
        }
    }

    // 以ID刪除Tag
    @Transactional
    public void deleteTag(Integer tagId) {
        Optional<Tag> tagOpt = tagDao.findById(tagId);
        if (tagOpt.isPresent()) {
            tagDao.delete(tagOpt.get());
        }
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
}