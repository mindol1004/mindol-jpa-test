package kiz.space.trade.service;

import kiz.space.trade.dto.TrdHeaderDTO;
import kiz.space.trade.dto.TrdTermDTO;
import kiz.space.trade.dto.TrdTermPricingCompDTO;
import kiz.space.trade.dto.TrdTermPricingDTO;
import kiz.space.trade.model.TrdHeader;
import kiz.space.trade.model.TrdTerm;
import kiz.space.trade.model.TrdTermPricing;
import kiz.space.trade.repository.TrdHeaderRepository;
import kiz.space.trade.repository.TrdTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TrdHeaderService {

    @PersistenceContext
    private final EntityManager em;

    private final TrdHeaderRepository trdHeaderRepository;
    private final TrdTermRepository trdTermRepository;

    public TrdHeaderDTO.Res findOne(Long tradeNum) {
        TrdHeader trdHeader = trdHeaderRepository.findByTradeNum(tradeNum);
        return TrdHeaderDTO.Res.of(trdHeader);
    }

    @Transactional
    public void save(TrdHeaderDTO.Req dto) {

        for (TrdHeaderDTO.Req req : dto.getTrdHeader()) {

            TrdHeader trdHeader = req.toEntity();

            trdHeaderRepository.save(trdHeader);
        }

    }

    @Transactional
    public void update(TrdHeaderDTO.Req dto) {

        for (TrdHeaderDTO.Req req : dto.getTrdHeader()) {

            TrdHeader.TrdHeaderPk pk = TrdHeader.TrdHeaderPk.builder().tradeNum(req.getTradeNum()).tradeType(req.getTradeType()).build();
            final TrdHeader trdHeader = trdHeaderRepository.findOne(pk);

            if (trdHeader == null) {
                throw new IllegalArgumentException("Data Not Found");
            }

            trdHeader.trdHeaderUpdate(req.toEntity());
            trdHeaderRepository.save(trdHeader);
            //em.detach(trdHeader);
        }

    }

//    private Set<TrdTerm> getTrdTermList(Set<TrdTermDTO.Req> trdTerm) {
//
//        if (CollectionUtils.isEmpty(trdTerm)) {
//            return new HashSet<>();
//        }
//
//        return trdTerm.stream().map(n -> {
//            Optional<TrdTerm> optionalTrdTerm = Optional.ofNullable(trdTermRepository.findByTermNum(n.getTermNum()));
//            if (!optionalTrdTerm.isPresent()) {
//                throw new RuntimeException("Tag: " + n + " 값을 찾을 수 없습니다.");
//            }
//            TrdTerm trdTerm1 = optionalTrdTerm.get();
//            trdTerm1.updateTrdTerm(n);
//
//            return trdTerm1;
//        }).collect(Collectors.toSet());
//    }

}
