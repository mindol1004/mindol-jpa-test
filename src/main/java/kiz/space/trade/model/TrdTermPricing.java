package kiz.space.trade.model;

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

    @Column(name = "TRADE_NUM")
    private Long tradeNum;
    @Column(name = "TERM_NUM")
    private Long termNum;
    @Column(name = "TERM_PRICING_CD")
    private String termPricingCd;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "TRADE_NUM", referencedColumnName = "TRADE_NUM"),
            @JoinColumn(name = "TERM_NUM", referencedColumnName = "TERM_NUM"),
            @JoinColumn(name = "TERM_PRICING_NUM", referencedColumnName = "TERM_PRICING_NUM")
    })
    private Set<TrdTermPricingComp> trdTermPricingComp = new HashSet<>();

    @Builder
    public TrdTermPricing(
            Long tradeNum,
            Long termNum,
            String termPricingCd
    ) {
        this.tradeNum = tradeNum;
        this.termNum = termNum;
        this.termPricingCd = termPricingCd;
    }

}
