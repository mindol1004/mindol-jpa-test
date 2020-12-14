package kiz.space.trade.dto;

import kiz.space.trade.model.TrdHeader;
import kiz.space.trade.model.TrdTerm;
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

public class TrdHeaderDTO {

    @Getter
    @Setter
    public static class Search {
        private Long tradeNum;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        private Long tradeNum;
        private Integer tradeType;
        private String tradeCd;

        private Set<TrdHeaderDTO.Req> trdHeader;
        private Set<TrdTermDTO.Req> trdTerm;

        public TrdHeader toEntity() {
            return TrdHeader.builder()
                    .tradeNum(tradeNum)
                    .tradeType(tradeType)
                    .tradeCd(tradeCd)
                    .trdTerms(trdTerm.stream().map(n -> {
                        return n.toEntity();
                    }).collect(Collectors.toSet()))
                    .build();
        }
    }

    @Getter
    @Setter
    public static class Res {
        private Long tradeNum;
        private Integer tradeType;

        public static TrdHeaderDTO.Res of(TrdHeader trdHeader) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(trdHeader, TrdHeaderDTO.Res.class);
        }

        public static List<TrdHeaderDTO.Res> of(List<TrdHeader> trdHeaders) {
            return trdHeaders.stream()
                    .map(TrdHeaderDTO.Res::of)
                    .collect(Collectors.toList());
        }
    }
    
}
