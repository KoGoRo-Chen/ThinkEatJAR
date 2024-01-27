package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.PriceDao;
import ThinkEat.mvc.model.dto.PricePageDto;
import ThinkEat.mvc.model.dto.UserPageDto;
import ThinkEat.mvc.model.entity.Price;
import ThinkEat.mvc.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Integer addPrice(Price price) {
        priceDao.save(price);
        return price.getId();
    }

    // 以ID修改價位
    public String updatePriceById(Integer priceId, String name) {
        Optional<Price> priceOpt = priceDao.findById(priceId);
        if (priceOpt.isPresent()) {
            Price updatedPrice = priceOpt.get();
            updatedPrice.setName(name);
            priceDao.save(updatedPrice);
            return "修改成功";
        }
        return "找不到價位";
    }

    // 以ID刪除價位
    @Transactional
    public String deletePrice(Integer priceId) {
        Optional<Price> priceOpt = priceDao.findById(priceId);
        if (priceOpt.isPresent()) {
            priceDao.delete(priceOpt.get());
            return "刪除成功";
        }
        return "找不到價位";
    }

    // 以ID尋找單個價位
    public Price getPriceById(Integer priceId) {
        Optional<Price> priceOpt = priceDao.findById(priceId);
        if (priceOpt.isPresent()) {
            Price price = priceOpt.get();
            return price;
        }
        return null;
    }

    // 尋找所有價位
    public List<Price> findAllPrice() {
        List<Price> priceList = priceDao.findAll();
        return priceList;
    }

    //尋找所有價位(分頁)
    public PricePageDto getAllPriceInPage(Pageable pageable) {
        Page<Price> pricePage = priceDao.findAll(pageable);
        return new PricePageDto(pricePage);
    }

}