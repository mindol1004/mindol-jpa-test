package kiz.space.trade.model;

import kiz.space.trade.dto.TrdTermPricingDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
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

    @OneToMany(mappedBy = "trdTermPricing", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TrdTermPricingComp> trdTermPricingComp = new HashSet<>();

    @Builder
    public TrdTermPricing(
            TrdTerm trdTerm,
            Long tradeNum,
            String termPricingCd,
            Set<TrdTermPricingComp> trdTermPricingComp
    ) {
        this.trdTerm = trdTerm;
        this.tradeNum = tradeNum;
        this.termPricingCd = termPricingCd;
        this.addTrdTermPricingComp(trdTermPricingComp);
    }

    public void setTrdTerm(TrdTerm trdTerm) {
        if(this.trdTerm != null) {
            this.trdTerm = null;
        }
        this.trdTerm = trdTerm;
    }

    public void addTrdTermPricingComp(Set<TrdTermPricingComp> trdTermPricingComp) {
        if(!Arrays.asList(this.trdTermPricingComp).contains(trdTermPricingComp)) {
            this.trdTermPricingComp.addAll(trdTermPricingComp);

            trdTermPricingComp.forEach(i -> {
                i.setTrdTermPricing(this);
            });
        }
    }

}
