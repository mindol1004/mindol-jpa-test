package kiz.space.trade.dto;

import kiz.space.trade.model.TrdHeader;
import kiz.space.trade.model.TrdTermPricing;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrdTermPricingDTO {

    @Getter
    @Setter
    public static class Search {
        private Long tradeNum;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        private Long tradeNum;
        private Long termNum;
        private String termPricingCd;

        public TrdTermPricing toEntity() {
            return TrdTermPricing.builder()
                    .tradeNum(tradeNum)
                    .termNum(termNum)
                    .termPricingCd(termPricingCd)
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
