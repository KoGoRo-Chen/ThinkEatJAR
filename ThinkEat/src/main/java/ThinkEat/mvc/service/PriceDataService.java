package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.PriceDataDao;
import ThinkEat.mvc.entity.PriceData;
import ThinkEat.mvc.entity.dto.PriceDataDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriceDataService {

    private final PriceDataDao priceDataDao;
    private final ModelMapper modelMapper;

    @Autowired
    public PriceDataService(PriceDataDao priceDataDao, ModelMapper modelMapper) {
        this.priceDataDao = priceDataDao;
        this.modelMapper = modelMapper;
    }

    // 新增價位資料
    @Transactional
    public int addPrice(PriceDataDto priceDataDto) {
        PriceData priceData = modelMapper.map(priceDataDto, PriceData.class);
        return priceDataDao.addPrice(priceData);
    }

    // 以ID修改價位
    public int updatePriceByPriceId(Integer priceId, PriceDataDto updatedPriceDataDto) {
        Optional<PriceData> priceDataToUpdate = priceDataDao.getPriceById(priceId);
        if (priceDataToUpdate.isPresent()) {
            PriceData updatedPriceData = modelMapper.map(updatedPriceDataDto, PriceData.class);
            return priceDataDao.updatePriceByPriceId(priceId, updatedPriceData);
        }
        return 0;
    }

    // 以ID刪除價位
    @Transactional
    public int deletePrice(Integer priceId) {
        return priceDataDao.deletePrice(priceId);
    }

    // 以ID尋找單個價位
    public Optional<PriceDataDto> getPriceById(Integer priceId) {
        Optional<PriceData> priceData = priceDataDao.getPriceById(priceId);
        return priceData.map(data -> modelMapper.map(data, PriceDataDto.class));
    }

    // 尋找所有價位
    public List<PriceDataDto> findAllPrice() {
        List<PriceData> priceDataList = priceDataDao.findAllPrice();
        return priceDataList.stream()
                .map(data -> modelMapper.map(data, PriceDataDto.class))
                .collect(Collectors.toList());
    }
}