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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "TRADE_NUM", referencedColumnName = "TRADE_NUM"),
            @JoinColumn(name = "TERM_NUM", referencedColumnName = "TERM_NUM")
    })
    private Set<TrdTermPricing> trdTermPricingList = new HashSet<>();

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
}
