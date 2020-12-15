package kiz.space.trade.repository;

import kiz.space.trade.model.TrdHeader;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrdHeaderRepository extends JpaRepository<TrdHeader, TrdHeader.TrdHeaderPk> {

    public TrdHeader findByTradeNum(Long tradeNum);

    //@EntityGraph(attributePaths = {"trdTerms"}, type = EntityGraph.EntityGraphType.LOAD)
    public TrdHeader findOne(TrdHeader.TrdHeaderPk pk);

    @Query(value = "SELECT IFNULL(MAX(LAST_INSERT_ID(term_num)),0)+1 FROM trd_term", nativeQuery = true)
    public Long getTermNum();

}
