package ThinkEat.mvc.service;

import ThinkEat.mvc.OldBean.dao.OTagDataDao;
import ThinkEat.mvc.OldBean.TagData;
import ThinkEat.mvc.dto.TagDataDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagDataService {

    private final OTagDataDao OTagDataDao;
    private final ModelMapper modelMapper;

    @Autowired
    public TagDataService(OTagDataDao OTagDataDao, ModelMapper modelMapper) {
        this.OTagDataDao = OTagDataDao;
        this.modelMapper = modelMapper;
    }

    // 新增Tag
    @Transactional
    public int addTag(TagDataDto tagDataDto) {
        TagData tagData = modelMapper.map(tagDataDto, TagData.class);
        return OTagDataDao.addTag(tagData);
    }

    // 以ID修改Tag
    public int updateTagByTagId(Integer tagId, TagDataDto updatedTagDataDto) {
        Optional<TagData> tagDataToUpdate = OTagDataDao.getTagByTagId(tagId);
        if (tagDataToUpdate.isPresent()) {
            TagData updatedTagData = modelMapper.map(updatedTagDataDto, TagData.class);
            return OTagDataDao.updateTagByTagId(tagId, updatedTagData);
        }
        return 0;
    }

    // 以ID刪除Tag
    @Transactional
    public int deleteTag(Integer tagId) {
        return OTagDataDao.deleteTag(tagId);
    }

    // 以ID尋找單個Tag
    public Optional<TagDataDto> getTagById(Integer tagId) {
        Optional<TagData> tagData = OTagDataDao.getTagByTagId(tagId);
        return tagData.map(data -> modelMapper.map(data, TagDataDto.class));
    }

    // 尋找所有Tag
    public List<TagDataDto> findAllTag() {
        List<TagData> tagDataList = OTagDataDao.findAllTag();
        return tagDataList.stream()
                .map(data -> modelMapper.map(data, TagDataDto.class))
                .collect(Collectors.toList());
    }
}