package kiz.space.trade.service;

import kiz.space.trade.dto.TrdHeaderDTO;
import kiz.space.trade.dto.TrdTermDTO;
import kiz.space.trade.dto.TrdTermPricingCompDTO;
import kiz.space.trade.dto.TrdTermPricingDTO;
import kiz.space.trade.model.TrdHeader;
import kiz.space.trade.model.TrdTerm;
import kiz.space.trade.model.TrdTermPricing;
import kiz.space.trade.repository.TrdHeaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrdHeaderService {

    private final TrdHeaderRepository trdHeaderRepository;

    public TrdHeaderDTO.Res findOne(Long tradeNum) {
        TrdHeader trdHeader = trdHeaderRepository.findByTradeNum(tradeNum);
        return TrdHeaderDTO.Res.of(trdHeader);
    }

    @Transactional
    public void save(TrdHeaderDTO.Req dto) {

        for (TrdHeaderDTO.Req req : dto.getTrdHeader()) {
            final TrdHeader trdHeader = req.toEntity();

            Optional<Set<TrdTermDTO.Req>> trdTerms = Optional.ofNullable(req.getTrdTerm());

            trdTerms.ifPresent(list -> list.forEach(item -> {
                final TrdTerm trdTerm = item.toEntity();

                Set<TrdTermPricingDTO.Req> trdTermPricings = item.getTrdTermPricing();
                trdTermPricings.forEach(subItem -> {
                    subItem.setTradeNum(req.getTradeNum());

                    final TrdTermPricing trdTermPricing = subItem.toEntity();

                    Optional<Set<TrdTermPricingCompDTO.Req>> trdTermPricingComps = Optional.ofNullable(subItem.getTrdTermPricingComp());
                    trdTermPricingComps.ifPresent(subSubList -> subSubList.forEach(subSubItem -> {
                        subSubItem.setTradeNum(req.getTradeNum());
                        trdTermPricing.addTermPricingComp(subSubItem.toEntity());
                    }));
                    trdTerm.addTermPricing(trdTermPricing);
                });

                trdHeader.addTrdTerm(trdTerm);
            }));

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

            trdHeader.trdHeaderUpdate(req);

            Optional<Set<TrdTerm>> trdTerms = Optional.ofNullable(trdHeader.getTrdTerms());

            Optional<Set<TrdTermDTO.Req>> trdTermReq = Optional.ofNullable(req.getTrdTerm());
            trdTermReq.ifPresent(list -> list.forEach(item -> {
                trdTerms.ifPresent(sublist -> {
                    for(TrdTerm trdterm : sublist) {
                        trdterm.trdTermUpdate(item);
                    }
                });
            }));

//            Optional<Set<TrdTerm>> trdTerms = Optional.ofNullable(trdHeader.getTrdTerms());
//            trdTerms.ifPresent(list -> {
//                for (TrdTerm trdterm : list) {
//
//                    Optional<Set<TrdTermDTO.Req>> trdTermReq = Optional.ofNullable(req.getTrdTerm());
//                    trdTermReq.ifPresent(subList -> subList.forEach(subItem -> {
//                        trdterm.trdTermUpdate(subItem);
//                    }));
//
//                }
//            });

        }

    }
}
