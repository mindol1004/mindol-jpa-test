package kiz.space.trade.dto;

import kiz.space.trade.model.TrdHeader;
import kiz.space.trade.model.TrdTerm;
import kiz.space.trade.model.TrdTermPricing;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

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
        private Long termNum;
        private Integer tradeType;
        private String termCd;

        private TrdTermSpecDTO.Req trdTermSpec;
        private List<TrdTermPricingDTO.Req> trdTermPricing;

        public TrdTerm toEntity() {
            return TrdTerm.builder()
                    .trdHeader(TrdHeader.builder().tradeNum(tradeNum).tradeType(tradeType).build())
                    .termNum(termNum)
                    .termCd(termCd)
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

        private TrdTermDTO.Mapper trdTerm;

        @Builder
        public Res(
                TrdTermDTO.Mapper trdTerm
        ) {
            this.trdTerm = trdTerm;
        }
    }

    @Getter
    @Setter
    public static class Mapper {
        private Long termNum;
        private Long tradeNum;
        private Integer tradeType;
        private String termCd;

        public static TrdTermDTO.Mapper of(TrdTerm trdTerm) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            modelMapper.typeMap(TrdTerm.class, TrdTermDTO.Mapper.class)
                    .addMapping(dto -> trdTerm.getTrdHeader().getTradeNum(), TrdTermDTO.Mapper::setTradeNum)
                    .addMapping(dto -> trdTerm.getTrdHeader().getTradeType(), TrdTermDTO.Mapper::setTradeType);

            return modelMapper.map(trdTerm, TrdTermDTO.Mapper.class);
        }

        public static List<TrdTermDTO.Mapper> of(List<TrdTerm> trdTerms) {
            return trdTerms.stream()
                    .map(TrdTermDTO.Mapper::of)
                    .collect(Collectors.toList());
        }

    }

}
