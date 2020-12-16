package kiz.space.trade.dto;

import kiz.space.trade.model.TrdHeader;
import kiz.space.trade.model.TrdTermPricing;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

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
        private Long tradeNum;

        public static TrdTermPricingDTO.Res of(TrdTermPricing trdTermPricing) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(trdTermPricing, TrdTermPricingDTO.Res.class);
        }

        public static List<TrdTermPricingDTO.Res> of(List<TrdTermPricing> trdTermPricing) {
            return trdTermPricing.stream()
                    .map(TrdTermPricingDTO.Res::of)
                    .collect(Collectors.toList());
        }
    }

}
