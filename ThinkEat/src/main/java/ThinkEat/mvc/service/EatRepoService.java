package ThinkEat.mvc.service;

import ThinkEat.mvc.OldBean.dao.OEatRepoDao;
import ThinkEat.mvc.OldBean.dao.OPriceDataDao;
import ThinkEat.mvc.OldBean.dao.OTagDataDao;
import ThinkEat.mvc.OldBean.EatRepo;
import ThinkEat.mvc.OldBean.ResData;
import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.repository.ResDataDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class EatRepoService {
    private static final AtomicInteger atomicEatRepoId = new AtomicInteger(0);  //文章ID
    private static final List<EatRepoDto> eatRepoDtoList = new CopyOnWriteArrayList<>();


    private final OEatRepoDao OEatRepoDao;
    private final OTagDataDao OTagDataDao;
    private final OPriceDataDao OPriceDataDao;
    private final ResDataService resDataService;
    private final ModelMapper modelMapper;

    @Autowired
    public EatRepoService(OEatRepoDao eatRepoDa, OTagDataDao OTagDataDao, OPriceDataDao OPriceDataDao, @Lazy ResDataService resDataService, ModelMapper modelMapper) {
        this.OTagDataDao = OTagDataDao;
        this.OPriceDataDao = OPriceDataDao;
        this.OEatRepoDao = eatRepoDa;
        this.resDataService = resDataService;
        this.modelMapper = modelMapper;
    }

    //新增文章
    @Transactional
    public int addEatRepo(EatRepoDto eatRepoDto) {
        Integer eatRepoDtoId = atomicEatRepoId.incrementAndGet();
        eatRepoDto.setEatRepoDtoId(eatRepoDtoId);
        EatRepo eatRepo = modelMapper.map(eatRepoDto, EatRepo.class);

        ResDataDto resDataDto = eatRepoDto.getResDataDto();
        // 如果 ResDataDto 不為空，則進行相關處理
        if (resDataDto != null) {
            // 將 ResDataDto 映射為 ResData 對象
            ResData resData = modelMapper.map(resDataDto, ResData.class);

            // 將 ResData 設置到 EatRepo 中
            eatRepo.setResData(resData);
        }
        eatRepoDtoList.add(eatRepoDto);

        return OEatRepoDao.addEatRepo(eatRepo);
    }

    //以ID修改文章
    public int updateEatRepoByEatRepoId(Integer eatRepoId, EatRepoDto eatRepoDto) {
        Optional<EatRepo> eatRepoOpt = OEatRepoDao.getEatRepoByEatRepoId(eatRepoId);
        if (eatRepoOpt.isPresent()) {
            EatRepo eatRepoToUpdate = eatRepoOpt.get();
            modelMapper.map(eatRepoDto, eatRepoToUpdate);
            return OEatRepoDao.updateEatRepoByEatRepoId(eatRepoId, eatRepoToUpdate);
        }
        return 0;
    }

    //以ID刪除文章
    @Transactional
    public int deleteEatRepo(Integer eatRepoId) {
        return OEatRepoDao.deleteEatRepo(eatRepoId);
    }

    //以ID尋找單篇食記
    public EatRepoDto getEatRepoByEatRepoId(Integer eatRepoId) {
        Optional<EatRepoDto> eatRepoOpt = eatRepoDtoList.stream()
                .filter(eatRepoDto -> eatRepoDto.getEatRepoDtoId().equals(eatRepoId))
                .findFirst();

        if(eatRepoOpt.isPresent()){
            EatRepoDto eatRepoDto = eatRepoOpt.get();
            return eatRepoDto;
        }
        return null;
    }

    //尋找所有食紀
    public List<EatRepoDto> findAllEatRepo() {
        return eatRepoDtoList;
    }

    //尋找所有餐廳ID相同的食記
    public List<EatRepoDto> getAllEatRepoByResId(Integer resId) {
        return eatRepoDtoList.stream()
                .filter(eatRepoDto -> eatRepoDto.getResDataDto() != null && eatRepoDto.getResDataDto().getResId().equals(resId))
                .collect(Collectors.toList());
    }
}
