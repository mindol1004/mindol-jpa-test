package kiz.space.trade.service;

import kiz.space.trade.dto.TrdHeaderDTO;
import kiz.space.trade.dto.TrdTermDTO;
import kiz.space.trade.dto.TrdTermPricingDTO;
import kiz.space.trade.dto.TrdTermSpecDTO;
import kiz.space.trade.model.TrdHeader;
import kiz.space.trade.model.TrdTerm;
import kiz.space.trade.model.TrdTermPricing;
import kiz.space.trade.model.TrdTermSpec;
import kiz.space.trade.repository.TrdHeaderRepository;
import kiz.space.trade.repository.TrdTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrdHeaderService {

    @PersistenceContext
    private final EntityManager em;

    private final TrdHeaderRepository trdHeaderRepository;
    private final TrdTermRepository trdTermRepository;

    public TrdHeaderDTO.Res findOne(Long tradeNum, Integer tradeType, Long termNum) {

        TrdHeader.TrdHeaderPk pk = TrdHeader.TrdHeaderPk.builder().tradeNum(tradeNum).tradeType(tradeType).build();

        TrdHeader trdHeader = trdHeaderRepository.findOne(pk);
        Optional<TrdTerm> trdTerm = trdHeader.getTrdTerms().stream().filter(f -> f.getTermNum() == termNum).findFirst();
        TrdTermSpec trdTermSpec = trdTerm.map(TrdTerm::getTrdTermSpec).orElse(TrdTermSpec.builder().build());
        List<TrdTermPricing> trdTermPricing = trdTerm.map(TrdTerm::getTrdTermPricing).orElse(new HashSet<>()).stream().collect(Collectors.toList());

        return TrdHeaderDTO.Res.builder()
                .trdHeader(TrdHeaderDTO.Mapper.of(trdHeader))
                .trdTerm(TrdTermDTO.Mapper.of(trdTerm.get()))
                .trdTermSpec(TrdTermSpecDTO.Mapper.of(trdTermSpec))
                .trdTermPricing(TrdTermPricingDTO.Mapper.of(trdTermPricing))
                .build();
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

    @Transactional
    public void delete(TrdHeaderDTO.Req dto) {
        for (TrdHeaderDTO.Req req : dto.getTrdHeader()) {

            TrdHeader.TrdHeaderPk pk = TrdHeader.TrdHeaderPk.builder().tradeNum(req.getTradeNum()).tradeType(req.getTradeType()).build();
            final TrdHeader trdHeader = trdHeaderRepository.findOne(pk);

            if (trdHeader == null) {
                throw new IllegalArgumentException("Data Not Found");
            }

            //trdHeader.trdHeaderUpdate(req.toEntity());
            trdHeaderRepository.delete(trdHeader);
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
