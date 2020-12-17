package kiz.space.trade.dto;

import kiz.space.trade.model.TrdHeader;
import kiz.space.trade.model.TrdTerm;
import kiz.space.trade.model.TrdTermPricing;
import kiz.space.trade.model.TrdTermSpec;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TrdTermPricingDTO {

    @Getter
    @Setter
    public static class Search {
        private Long tradeNum;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        private Long termPricingNum;
        private Long tradeNum;
        private String termPricingCd;

        private List<TrdTermPricingCompDTO.Req> trdTermPricingComp;

        public TrdTermPricing toEntity() {
            return TrdTermPricing.builder()
                    .termPricingNum(termPricingNum)
                    .tradeNum(tradeNum)
                    .termPricingCd(termPricingCd)
                    .trdTermPricingComp(
                        Optional.ofNullable(trdTermPricingComp)
                            .map(n -> {
                                return n.stream().map(i -> {
                                    i.setTradeNum(tradeNum);
                                    return i.toEntity();
                                }).collect(Collectors.toSet());
                            }).orElseGet(() -> new HashSet<>())
                    )
                    .build();
        }
    }

    @Getter
    @Setter
    public static class Res {

        private TrdTermPricingDTO.Mapper trdTermPricing;
        private List<TrdTermPricingDTO.Mapper> trdTermPricingList;

        @Builder
        public Res(
                TrdTermPricingDTO.Mapper trdTermPricing,
                List<TrdTermPricingDTO.Mapper> trdTermPricingList
        ) {
            this.trdTermPricing = trdTermPricing;
            this.trdTermPricingList = trdTermPricingList;
        }

    }

    @Getter
    @Setter
    public static class Mapper {
        private Long termPricingNum;
        private Long termNum;
        private Long tradeNum;
        private String termPricingCd;

        public static TrdTermPricingDTO.Mapper of(TrdTermPricing trdTermPricing) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            modelMapper.typeMap(TrdTerm.class, TrdTermDTO.Mapper.class)
                    .addMapping(dto -> trdTermPricing.getTrdTerm().getTermNum(), TrdTermDTO.Mapper::setTermNum);

            return modelMapper.map(trdTermPricing, TrdTermPricingDTO.Mapper.class);
        }

        public static List<TrdTermPricingDTO.Mapper> of(List<TrdTermPricing> trdTermPricing) {
            return trdTermPricing.stream()
                    .map(TrdTermPricingDTO.Mapper::of)
                    .collect(Collectors.toList());
        }

    }

}
