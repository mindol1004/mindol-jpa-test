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

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "trdTerm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TrdTermPricing> trdTermPricing = new HashSet<>();

    @OneToMany(mappedBy = "trdTerm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TrdTermPricingComp> trdTermPricingComp = new HashSet<>();

    @Builder
    public TrdTerm(
            TrdHeader trdHeader,
            String termCd
    ) {
        this.trdHeader = trdHeader;
        this.termCd = termCd;
    }

    public void setTrdHeader(TrdHeader trdHeader) {
        this.trdHeader = trdHeader;
        if(trdHeader != null) {
            trdHeader.getTrdTerms().add(this);
        }
    }

    public void trdTermUpdate(TrdTermDTO.Req dto) {
        this.termCd = dto.getTermCd();
    }

    public void addTermPricing(TrdTermPricing trdTermPricing) {
        this.trdTermPricing.add(trdTermPricing);
        trdTermPricing.setTrdTerm(this);

        Optional<Set<TrdTermPricingComp>> trdTermPricingComps = Optional.of(trdTermPricing.getTrdTermPricingComp());
        trdTermPricingComps.ifPresent(list -> list.forEach(item -> {
            this.addTermPricingComp(item);
        }));

    }

    public void addTermPricingComp(TrdTermPricingComp trdTermPricingComp) {
        this.trdTermPricingComp.add(trdTermPricingComp);
        trdTermPricingComp.setTrdTerm(this);
    }
}
