package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.PriceDao;
import ThinkEat.mvc.model.dto.PriceDto;
import ThinkEat.mvc.model.entity.Price;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

    private final PriceDao priceDao;
    private final ModelMapper modelMapper;

    @Autowired
    public PriceService(PriceDao priceDao, ModelMapper modelMapper) {
        this.priceDao = priceDao;
        this.modelMapper = modelMapper;
    }

    // 新增價位資料
    @Transactional
    public Price addPrice(PriceDto priceDto) {
        Price price = modelMapper.map(priceDto, Price.class);
        return priceDao.save(price);
    }

    // 以ID修改價位
    public void updatePriceById(Integer priceId, PriceDto priceDto) {
        Optional<Price> priceOpt = priceDao.findById(priceId);
        if (priceOpt.isPresent()) {
            Price updatedPrice = modelMapper.map(priceDto, Price.class);
            priceDao.save(updatedPrice);
        }

    }

    // 以ID刪除價位
    @Transactional
    public void deletePrice(Integer priceId) {
        Optional<Price> priceOpt = priceDao.findById(priceId);
        if (priceOpt.isPresent()) {
            priceDao.delete(priceOpt.get());
        }
    }

    // 以ID尋找單個價位
    public PriceDto getPriceById(Integer priceId) {
        Optional<Price> priceOpt = priceDao.findById(priceId);
        if (priceOpt.isPresent()) {
            Price price = priceOpt.get();
            PriceDto priceDto = modelMapper.map(price, PriceDto.class);
            return priceDto;
        }
        return null;
    }

    // 尋找所有價位
    public List<PriceDto> findAllPrice() {
        List<Price> priceList = priceDao.findAll();
        return priceList.stream()
                .map(price -> modelMapper.map(price, PriceDto.class))
                .toList();
    }

}