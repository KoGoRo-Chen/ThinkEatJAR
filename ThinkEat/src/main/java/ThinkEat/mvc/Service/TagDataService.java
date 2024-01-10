package ThinkEat.mvc.Service;

import ThinkEat.mvc.Bean.Dao.TagDataDao;
import ThinkEat.mvc.entity.PriceData;
import ThinkEat.mvc.entity.TagData;
import ThinkEat.mvc.Bean.Dto.TagDataDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagDataService {

    private final TagDataDao tagDataDao;
    private final ModelMapper modelMapper;

    @Autowired
    public TagDataService(TagDataDao tagDataDao, ModelMapper modelMapper) {
        this.tagDataDao = tagDataDao;
        this.modelMapper = modelMapper;
    }

    // 新增Tag
    @Transactional
    public int addTag(TagDataDto tagDataDto) {
        TagData tagData = modelMapper.map(tagDataDto, TagData.class);
        return tagDataDao.addTag(tagData);
    }

    // 以ID修改Tag
    public int updateTagByTagId(Integer tagId, TagDataDto updatedTagDataDto) {
        Optional<TagData> tagDataToUpdate = tagDataDao.getTagByTagId(tagId);
        if (tagDataToUpdate.isPresent()) {
            TagData updatedTagData = modelMapper.map(updatedTagDataDto, TagData.class);
            return tagDataDao.updateTagByTagId(tagId, updatedTagData);
        }
        return 0;
    }

    // 以ID刪除Tag
    @Transactional
    public int deleteTag(Integer tagId) {
        return tagDataDao.deleteTag(tagId);
    }

    // 以ID尋找單個Tag
    public Optional<TagDataDto> getTagById(Integer tagId) {
        Optional<TagData> tagData = tagDataDao.getTagByTagId(tagId);
        return tagData.map(data -> modelMapper.map(data, TagDataDto.class));
    }

    // 尋找所有Tag
    public List<TagDataDto> findAllTag() {
        List<TagData> tagDataList = tagDataDao.findAllTag();
        return tagDataList.stream()
                .map(data -> modelMapper.map(data, TagDataDto.class))
                .collect(Collectors.toList());
    }
}