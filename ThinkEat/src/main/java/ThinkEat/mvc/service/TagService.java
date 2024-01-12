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
    public Tag addTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        return tagDao.save(tag);
    }

    // 以ID修改Tag
    public void updateTagByTagId(Integer tagId, TagDto tagDto) {
        Optional<Tag> tagOpt = tagDao.findById(tagId);
        if (tagOpt.isPresent()) {
            Tag tagToUpdate = tagOpt.get();
            modelMapper.map(tagToUpdate, TagDto.class);
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
    public TagDto getTagById(Integer tagId) {
        Optional<Tag> tagOpt = tagDao.findById(tagId);
        if (tagOpt.isPresent()) {
            Tag tag = tagOpt.get();
            TagDto tagDto = modelMapper.map(tag, TagDto.class);
            return tagDto;
        }
        return null;
    }

    // 尋找所有Tag
    public List<TagDto> findAllTag() {
        List<Tag> tagList = tagDao.findAll();
        return tagList.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .toList();
    }
}