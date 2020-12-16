package kiz.space.trade.model;

import kiz.space.trade.dto.TrdTermDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "TRD_TERM")
public class TrdTerm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TERM_NUM")
    private Long termNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "TRADE_NUM", referencedColumnName = "TRADE_NUM", updatable = false),
            @JoinColumn(name = "TRADE_TYPE", referencedColumnName = "TRADE_TYPE", updatable = false)
    })
    private TrdHeader trdHeader;

    @Column(name = "TERM_CD")
    private String termCd;

    @OneToOne(mappedBy = "trdTerm", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private TrdTermSpec trdTermSpec;

    @OneToMany(mappedBy = "trdTerm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TrdTermPricing> trdTermPricing = new HashSet<>();

    @OneToMany(mappedBy = "trdTerm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TrdTermPricingComp> trdTermPricingComp = new HashSet<>();

    @Builder
    public TrdTerm(
            TrdHeader trdHeader,
            Long termNum,
            String termCd,
            TrdTermSpec trdTermSpec,
            Set<TrdTermPricing> trdTermPricing
    ) {
        this.trdHeader = trdHeader;
        this.termNum = termNum;
        this.termCd = termCd;
        this.addTrdTermSpec(trdTermSpec);
        this.addTrdTermPricing(trdTermPricing);
    }

    public void addTrdTermSpec(TrdTermSpec trdTermSpec) {
        this.trdTermSpec = trdTermSpec;
        if (trdTermSpec != null) {
            trdTermSpec.setTrdTerm(this);
        }
    }

    public void addTrdTermPricing(Set<TrdTermPricing> trdTermPricing) {
        if(!this.trdTermPricing.contains(trdTermPricing)) {
            this.trdTermPricing.addAll(trdTermPricing);

            trdTermPricing.forEach(i -> {
                i.setTrdTerm(this);
                i.getTrdTermPricingComp().forEach(j -> {
                    j.setTrdTerm(this);
                });
            });
        }
    }

}
