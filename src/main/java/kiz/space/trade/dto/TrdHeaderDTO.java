package kiz.space.trade.dto;

import kiz.space.trade.model.TrdHeader;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrdHeaderDTO {

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
        private Integer tradeType;
        private String tradeCd;

        private List<TrdHeaderDTO.Req> trdHeader;

        private List<TrdTermDTO.Req> trdTerm;

        public TrdHeader toEntity() {
            return TrdHeader.builder()
                    .tradeNum(tradeNum)
                    .tradeType(tradeType)
                    .tradeCd(tradeCd)
                    .trdTerms(
                        Optional.ofNullable(trdTerm)
                            .map(n -> {
                                return n.stream().map(i -> {
                                    i.setTradeNum(tradeNum);
                                    i.setTradeType(tradeType);
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

        private TrdHeaderDTO.Mapper trdHeader;
        private TrdTermDTO.Mapper trdTerm;
        private TrdTermSpecDTO.Mapper trdTermSpec;
        private List<TrdTermPricingDTO.Mapper> trdTermPricing;

        @Builder
        public Res(
                TrdHeaderDTO.Mapper trdHeader,
                TrdTermDTO.Mapper trdTerm,
                TrdTermSpecDTO.Mapper trdTermSpec,
                List<TrdTermPricingDTO.Mapper> trdTermPricing
        ) {
            this.trdHeader = trdHeader;
            this.trdTerm = trdTerm;
            this.trdTermSpec = trdTermSpec;
            this.trdTermPricing = trdTermPricing;
        }
    }

    @Getter
    @Setter
    public static class Mapper {
        private Long tradeNum;
        private Integer tradeType;
        private String tradeCd;

        public static Mapper of(TrdHeader trdHeader) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            return modelMapper.map(trdHeader, TrdHeaderDTO.Mapper.class);
        }

        public static List<TrdHeaderDTO.Mapper> of(List<TrdHeader> trdHeaders) {
            return trdHeaders.stream()
                    .map(TrdHeaderDTO.Mapper::of)
                    .collect(Collectors.toList());
        }

    }
    
}
