package ThinkEat.mvc.dao;

import ThinkEat.mvc.entity.PriceData;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PriceDataDaoImplInMemory implements PriceDataDao {
    private static List<PriceData> pricesSum = new CopyOnWriteArrayList<>();
    private static AtomicInteger atomicPriceId = new AtomicInteger(0);

    // 構造函數
    public PriceDataDaoImplInMemory() {
        pricesSum.add(new PriceData(atomicPriceId.incrementAndGet(), "低於100"));
        pricesSum.add(new PriceData(atomicPriceId.incrementAndGet(), "100~200之間"));
        pricesSum.add(new PriceData(atomicPriceId.incrementAndGet(), "200~300之間"));
        pricesSum.add(new PriceData(atomicPriceId.incrementAndGet(), "300~400之間"));
        pricesSum.add(new PriceData(atomicPriceId.incrementAndGet(), "400~500之間"));
        pricesSum.add(new PriceData(atomicPriceId.incrementAndGet(), "超過500"));
    }

    @Override
    public int addPrice(PriceData priceData) {
        priceData.setId(atomicPriceId.incrementAndGet());
        pricesSum.add(priceData);
        return 1;
    }

    @Override
    public int updatePriceByPriceId(Integer priceId, PriceData updatedPriceData) {
        Optional<PriceData> priceDataToUpdate = pricesSum.stream()
                .filter(priceData -> priceData.getId().equals(priceId))
                .findFirst();

        if (priceDataToUpdate.isPresent()) {
            // 找到相應的價格資料，進行更新
            int index = pricesSum.indexOf(priceDataToUpdate.get());
            pricesSum.set(index, updatedPriceData);
            return 1;
        } else {
            // 未找到匹配的價格 ID，返回 0 表示未進行任何更新
            return 0;
        }
    }

    @Override
    public int deletePrice(Integer priceId) {
        pricesSum.removeIf(priceData -> priceData.getId().equals(priceId));
        return 1;
    }

    @Override
    public Optional<PriceData> getPriceById(Integer priceId) {
        return pricesSum.stream().filter(priceData -> priceData.getId().equals(priceId)).findFirst();
    }

    @Override
    public List<PriceData> findAllPrice() {

        return new ArrayList<>(pricesSum);
    }
}