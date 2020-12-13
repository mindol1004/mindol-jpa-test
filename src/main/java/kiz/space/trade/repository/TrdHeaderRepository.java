package kiz.space.trade.repository;

import kiz.space.trade.model.TrdHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrdHeaderRepository extends JpaRepository<TrdHeader, TrdHeader.TrdHeaderPk> {

    public TrdHeader findByTradeNum(Long tradeNum);

}
