package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.EatRepoDao;
import ThinkEat.mvc.dao.PriceDataDao;
import ThinkEat.mvc.dao.ResDataDao;
import ThinkEat.mvc.dao.TagDataDao;
import ThinkEat.mvc.entity.EatRepo;
import ThinkEat.mvc.entity.ResData;
import ThinkEat.mvc.entity.dto.EatRepoDto;
import ThinkEat.mvc.entity.dto.ResDataDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EatRepoService {

    private final EatRepoDao eatRepoDao;
    private final TagDataDao tagDataDao;
    private final PriceDataDao priceDataDao;
    private final ResDataService resDataService;
    private final ModelMapper modelMapper;

    @Autowired
    public EatRepoService(EatRepoDao eatRepoDa, TagDataDao tagDataDao, PriceDataDao priceDataDao, ResDataService resDataService, ModelMapper modelMapper) {
        this.tagDataDao = tagDataDao;
        this.priceDataDao = priceDataDao;
        this.eatRepoDao = eatRepoDa;
        this.resDataService = resDataService;
        this.modelMapper = modelMapper;
    }

    //新增文章
    @Transactional
    public int addEatRepo(EatRepoDto eatRepoDto) {
        EatRepo eatRepo = modelMapper.map(eatRepoDto, EatRepo.class);

        ResDataDto resDataDto = eatRepoDto.getResDataDto();
        // 如果 ResDataDto 不為空，則進行相關處理
        if (resDataDto != null) {
            // 將 ResDataDto 映射為 ResData 對象
            ResData resData = modelMapper.map(resDataDto, ResData.class);

            // 將 ResData 設置到 EatRepo 中
            eatRepo.setResData(resData);
        }

        return eatRepoDao.addEatRepo(eatRepo);
    }

    //以ID修改文章
    public int updateEatRepoByEatRepoId(Integer eatRepoId, EatRepoDto eatRepoDto) {
        Optional<EatRepo> eatRepoOpt = eatRepoDao.getEatRepoByEatRepoId(eatRepoId);
        if (eatRepoOpt.isPresent()) {
            EatRepo eatRepoToUpdate = eatRepoOpt.get();
            modelMapper.map(eatRepoDto, eatRepoToUpdate);
            return eatRepoDao.updateEatRepoByEatRepoId(eatRepoId, eatRepoToUpdate);
        }
        return 0;
    }

    //以ID刪除文章
    @Transactional
    public int deleteEatRepo(Integer eatRepoId) {
        return eatRepoDao.deleteEatRepo(eatRepoId);
    }

    //以ID尋找單篇食記
    public EatRepoDto getEatRepoByEatRepoId(Integer eatRepoId) {
        Optional<EatRepo> eatRepoOpt = eatRepoDao.getEatRepoByEatRepoId(eatRepoId);
        return eatRepoOpt.map(eatRepo -> modelMapper.map(eatRepo, EatRepoDto.class)).orElse(null);
    }

    //尋找所有食紀
    public List<EatRepoDto> findAllEatRepo() {
        List<EatRepo> eatRepoList = eatRepoDao.findAllEatRepo();
        return eatRepoList.stream()
                .map(eatRepo -> modelMapper.map(eatRepo, EatRepoDto.class))
                .collect(Collectors.toList());
    }
}
