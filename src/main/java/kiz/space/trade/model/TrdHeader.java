package kiz.space.trade.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@IdClass(TrdHeader.TrdHeaderPk.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "TRD_HEADER")
public class TrdHeader implements Serializable {

    @EqualsAndHashCode
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TrdHeaderPk implements Serializable {
        private Long tradeNum;
        private Integer tradeType;

        @Builder
        public TrdHeaderPk(
                Long tradeNum,
                Integer tradeType
        ) {
            this.tradeNum = tradeNum;
            this.tradeType = tradeType;
        }
    }

    @Id
    @Column(name = "TRADE_NUM")
    private Long tradeNum;

    @Id
    @Column(name = "TRADE_TYPE")
    private Integer tradeType;

    @Column(name = "TRADE_CD")
    private String tradeCd;

    @OneToMany(mappedBy = "trdHeader", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TrdTerm> trdTerms = new HashSet<>();

    @Builder
    public TrdHeader(
            Long tradeNum,
            Integer tradeType,
            String tradeCd,
            Set<TrdTerm> trdTerms
    ) {
        this.tradeNum = tradeNum;
        this.tradeType = tradeType;
        this.tradeCd = tradeCd;
        this.trdTerms = trdTerms;
    }

    public void trdHeaderUpdate(TrdHeader trdHeader) {
        this.tradeCd = trdHeader.getTradeCd();
        this.trdTerms = trdHeader.getTrdTerms();
    }

}
