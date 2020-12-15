package kiz.space.trade.dto;

import kiz.space.trade.model.TrdHeader;
import kiz.space.trade.model.TrdTerm;
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

public class TrdTermDTO {

    @Getter
    @Setter
    public static class Search {
        private Long termNum;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        private Long tradeNum;
        private Integer tradeType;
        private String termCd;

        private TrdTermSpecDTO.Req trdTermSpec;
        private Set<TrdTermPricingDTO.Req> trdTermPricing;

        public TrdTerm toEntity() {
            return TrdTerm.builder()
                    .trdHeader(TrdHeader.builder().tradeNum(tradeNum).tradeType(tradeType).build())
                    .termCd(this.termCd)
                    .trdTermSpec(
                        Optional.ofNullable(trdTermSpec)
                            .map(n -> {
                                n.setTradeNum(tradeNum);
                                return n.toEntity();
                            }).orElseGet(() -> null)
                    )
                    .trdTermPricing(
                        Optional.ofNullable(trdTermPricing)
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
        private Long termNum;
        private Long tradeNum;
        private Integer tradeType;
        private String termCd;

        public static TrdTermDTO.Res of(TrdTerm trdTerm) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(trdTerm, TrdTermDTO.Res.class);
        }

        public static List<TrdTermDTO.Res> of(List<TrdTerm> trdTerms) {
            return trdTerms.stream()
                    .map(TrdTermDTO.Res::of)
                    .collect(Collectors.toList());
        }
    }

}
