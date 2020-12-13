package kiz.space.trade.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "TRD_TERM_PRICING_COMP")
public class TrdTermPricingComp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TERM_PRICING_COMP_NUM")
    private Long termPricingCompNum;

    @Column(name = "TERM_PRICING_NUM")
    private Long termPricingNum;
    @Column(name = "TRADE_NUM")
    private Long tradeNum;
    @Column(name = "TERM_NUM")
    private Long termNum;

    @Builder
    public TrdTermPricingComp(
            Long termPricingNum,
            Long tradeNum,
            Long termNum
    ) {
        this.termPricingNum = termPricingNum;
        this.tradeNum = tradeNum;
        this.termNum = termNum;
    }

}
