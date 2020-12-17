package kiz.space.trade.dto;

import kiz.space.trade.model.TrdTermSpec;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

public class TrdTermSpecDTO {

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
        private String termSpecCd;

        public TrdTermSpec toEntity() {
            return TrdTermSpec.builder()
                    .tradeNum(tradeNum)
                    .termNum(termNum)
                    .termSpecCd(termSpecCd)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class Res {

        private TrdTermSpecDTO.Mapper trdTermSpec;

        @Builder
        public Res(
                TrdTermSpecDTO.Mapper trdTermSpec
        ) {
            this.trdTermSpec = trdTermSpec;
        }
    }

    @Getter
    @Setter
    public static class Mapper {
        private Long termNum;
        private Long tradeNum;
        private String termSpecCd;

        public static TrdTermSpecDTO.Mapper of(TrdTermSpec trdTermSpec) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            return modelMapper.map(trdTermSpec, TrdTermSpecDTO.Mapper.class);
        }

        public static List<TrdTermSpecDTO.Mapper> of(List<TrdTermSpec> trdTermSpecs) {
            return trdTermSpecs.stream()
                    .map(TrdTermSpecDTO.Mapper::of)
                    .collect(Collectors.toList());
        }

    }

}
