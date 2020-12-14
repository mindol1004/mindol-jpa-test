package kiz.space.trade.dto;

import kiz.space.trade.model.TrdTermPricingComp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class TrdTermPricingCompDTO {

    @Getter
    @Setter
    public static class Search {
        private Long tradeNum;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        private Long tradeNum;
        private String termPricingCompCd;

        public TrdTermPricingComp toEntity() {
            return TrdTermPricingComp.builder()
                    .tradeNum(tradeNum)
                    .termPricingCompCd(termPricingCompCd)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class Res {
        private Long tradeNum;

        public static TrdTermPricingCompDTO.Res of(TrdTermPricingComp trdTermPricingComp) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(trdTermPricingComp, TrdTermPricingCompDTO.Res.class);
        }

        public static List<TrdTermPricingCompDTO.Res> of(List<TrdTermPricingComp> trdTermPricingComp) {
            return trdTermPricingComp.stream()
                    .map(TrdTermPricingCompDTO.Res::of)
                    .collect(Collectors.toList());
        }
    }

}
