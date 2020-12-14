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

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TrdHeaderService {

    private final TrdHeaderRepository trdHeaderRepository;
    private final TrdTermRepository trdTermRepository;

    public TrdHeaderDTO.Res findOne(Long tradeNum) {
        TrdHeader trdHeader = trdHeaderRepository.findByTradeNum(tradeNum);
        return TrdHeaderDTO.Res.of(trdHeader);
    }

    @Transactional
    public void save(TrdHeaderDTO.Req dto) {

        for (TrdHeaderDTO.Req req : dto.getTrdHeader()) {
            final TrdHeader trdHeader = req.toEntity();

//            Optional<Set<TrdTermDTO.Req>> trdTerms = Optional.ofNullable(req.getTrdTerm());
//
//            trdTerms.ifPresent(list -> list.forEach(item -> {
//                final TrdTerm trdTerm = item.toEntity();
//
//                Set<TrdTermPricingDTO.Req> trdTermPricings = item.getTrdTermPricing();
//                trdTermPricings.forEach(subItem -> {
//                    subItem.setTradeNum(req.getTradeNum());
//
//                    final TrdTermPricing trdTermPricing = subItem.toEntity();
//
//                    Optional<Set<TrdTermPricingCompDTO.Req>> trdTermPricingComps = Optional.ofNullable(subItem.getTrdTermPricingComp());
//                    trdTermPricingComps.ifPresent(subSubList -> subSubList.forEach(subSubItem -> {
//                        subSubItem.setTradeNum(req.getTradeNum());
//                        trdTermPricing.addTermPricingComp(subSubItem.toEntity());
//                    }));
//                    trdTerm.addTermPricing(trdTermPricing);
//                });
//
//                trdHeader.addTrdTerm(trdTerm);
//            }));

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

            trdHeader.trdHeaderUpdate(req, this.getTrdTermList(req.getTrdTerm()));

        }

    }

    private Set<TrdTerm> getTrdTermList(Set<TrdTermDTO.Req> trdTerm) {

        if (CollectionUtils.isEmpty(trdTerm)) {
            return new HashSet<>();
        }

        return trdTerm.stream().map(n -> {
            Optional<TrdTerm> optionalTrdTerm = Optional.ofNullable(trdTermRepository.findOne(n.getTermNum()));
            if (!optionalTrdTerm.isPresent()) {
                throw new RuntimeException("Tag: " + n + " 값을 찾을 수 없습니다.");
            }

            TrdTerm trdTerm1 = optionalTrdTerm.get();
            trdTerm1.trdTermUpdate(n);

            return trdTerm1;
        }).collect(Collectors.toSet());
    }
}
