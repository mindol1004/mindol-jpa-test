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
            String termPricingCompCd
    ) {
        this.tradeNum = tradeNum;
        this.termPricingCompCd = termPricingCompCd;
    }

    public void trdTermPricingCompUpdate(TrdTermPricingCompDTO.Req dto) {
        this.termPricingCompCd = dto.getTermPricingCompCd();
    }

    public void setTrdTerm(TrdTerm trdTerm) {
        this.trdTerm = trdTerm;
        if(trdTerm != null) {
            trdTerm.getTrdTermPricingComp().add(this);
        }
    }

    public void setTrdTermPricing(TrdTermPricing trdTermPricing) {
        this.trdTermPricing = trdTermPricing;
        if(trdTermPricing != null) {
            trdTermPricing.getTrdTermPricingComp().add(this);
        }
    }
}
