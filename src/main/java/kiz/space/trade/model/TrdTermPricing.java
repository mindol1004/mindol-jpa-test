package kiz.space.trade.model;

import kiz.space.trade.dto.TrdTermPricingDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "TRD_TERM_PRICING")
public class TrdTermPricing implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TERM_PRICING_NUM")
    private Long termPricingNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERM_NUM")
    private TrdTerm trdTerm;

    @Column(name = "TRADE_NUM")
    private Long tradeNum;

    @Column(name = "TERM_PRICING_CD")
    private String termPricingCd;

    @OneToMany(mappedBy = "trdTerm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TrdTermPricingComp> trdTermPricingComp = new HashSet<>();

    @Builder
    public TrdTermPricing(
            Long tradeNum,
            String termPricingCd
    ) {
        this.tradeNum = tradeNum;
        this.termPricingCd = termPricingCd;
    }

    public void trdTermPricingUpdate(TrdTermPricingDTO.Req dto) {
        this.termPricingCd = dto.getTermPricingCd();
    }

    public void setTrdTerm(TrdTerm trdTerm) {
        this.trdTerm = trdTerm;
        if(trdTerm != null) {
            trdTerm.getTrdTermPricing().add(this);
        }
    }

    public void addTermPricingComp(TrdTermPricingComp trdTermPricingComp) {
        this.trdTermPricingComp.add(trdTermPricingComp);
        trdTermPricingComp.setTrdTermPricing(this);
    }

}
