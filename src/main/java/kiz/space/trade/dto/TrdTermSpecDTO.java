package kiz.space.trade.dto;

import kiz.space.trade.model.TrdTermSpec;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

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
        //private Long termNum;
        private Long tradeNum;
        private String termSpecCd;

        public TrdTermSpec toEntity() {
            return TrdTermSpec.builder()
                    //.termNum(termNum)
                    .tradeNum(tradeNum)
                    .termSpecCd(termSpecCd)
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

        public static TrdTermSpecDTO.Res of(TrdTermSpec trdTermSpec) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(trdTermSpec, TrdTermSpecDTO.Res.class);
        }

        public static List<TrdTermSpecDTO.Res> of(List<TrdTermSpec> trdTermSpecs) {
            return trdTermSpecs.stream()
                    .map(TrdTermSpecDTO.Res::of)
                    .collect(Collectors.toList());
        }
    }

}
