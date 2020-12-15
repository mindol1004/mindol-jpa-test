package kiz.space.trade.model;

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
            @JoinColumn(name = "TRADE_NUM", referencedColumnName = "TRADE_NUM"),
            @JoinColumn(name = "TRADE_TYPE", referencedColumnName = "TRADE_TYPE")
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
            String termCd,
            TrdTermSpec trdTermSpec,
            Set<TrdTermPricing> trdTermPricing
    ) {
        this.trdHeader = trdHeader;
        this.termCd = termCd;
        this.addTrdTermSpec(trdTermSpec);
        this.addTermPricing(trdTermPricing);
    }

//    public void setTrdHeader(TrdHeader trdHeader) {
//        this.trdHeader = trdHeader;
//        if(trdHeader != null) {
//            trdHeader.getTrdTerms().add(this);
//        }
//    }

    public void addTrdTermSpec(TrdTermSpec trdTermSpec) {
        this.trdTermSpec = trdTermSpec;
        if (trdTermSpec != null) {
            trdTermSpec.setTrdTerm(this);
        }
    }

    public void addTermPricing(Set<TrdTermPricing> trdTermPricing) {
        this.trdTermPricing = trdTermPricing;

        Optional<Set<TrdTermPricing>> trdTermPricingList = Optional.ofNullable(trdTermPricing);
        trdTermPricingList.ifPresent(list -> list.forEach(item -> {
            item.setTrdTerm(this);
        }));

    }

//    public void trdTermUpdate(TrdTermDTO.Req dto) {
//        this.termCd = dto.getTermCd();
//    }
//
//    public void addTermPricing(TrdTermPricing trdTermPricing) {
//        this.trdTermPricing.add(trdTermPricing);
//        trdTermPricing.setTrdTerm(this);
//
//        Optional<Set<TrdTermPricingComp>> trdTermPricingComps = Optional.of(trdTermPricing.getTrdTermPricingComp());
//        trdTermPricingComps.ifPresent(list -> list.forEach(item -> {
//            this.addTermPricingComp(item);
//        }));
//
//    }
//
//    public void addTermPricingComp(TrdTermPricingComp trdTermPricingComp) {
//        this.trdTermPricingComp.add(trdTermPricingComp);
//        trdTermPricingComp.setTrdTerm(this);
//    }
}
