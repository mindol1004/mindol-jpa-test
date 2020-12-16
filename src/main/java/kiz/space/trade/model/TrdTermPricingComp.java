package kiz.space.trade.model;

import kiz.space.trade.dto.TrdTermPricingCompDTO;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERM_PRICING_NUM")
    private TrdTermPricing trdTermPricing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERM_NUM")
    private TrdTerm trdTerm;

    @Column(name = "TRADE_NUM")
    private Long tradeNum;

    @Column(name = "TERM_PRICING_COMP_CD")
    private String termPricingCompCd;

    @Builder
    public TrdTermPricingComp(
            Long tradeNum,
            TrdTerm trdTerm,
            TrdTermPricing trdTermPricing,
            String termPricingCompCd
    ) {
        this.tradeNum = tradeNum;
        this.trdTerm = trdTerm;
        this.trdTermPricing = trdTermPricing;
        this.termPricingCompCd = termPricingCompCd;
    }

    public void setTrdTerm(TrdTerm trdTerm) {
        if(this.trdTerm != null) {
            this.trdTerm = null;
        }
        this.trdTerm = trdTerm;
    }

    public void setTrdTermPricing(TrdTermPricing trdTermPricing) {
        if(this.trdTermPricing != null) {
            this.trdTermPricing = null;
        }
        this.trdTermPricing = trdTermPricing;
    }
}
