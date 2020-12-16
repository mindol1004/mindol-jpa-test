package kiz.space.trade.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "TRD_TERM_SPEC")
public class TrdTermSpec implements Serializable {

    @Id
    @Column(name = "TERM_NUM")
    private Long termNum;

    @MapsId
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TERM_NUM", referencedColumnName = "TERM_NUM")
    private TrdTerm trdTerm;

    @Column(name = "TRADE_NUM")
    private Long tradeNum;

    @Column(name = "TERM_SPEC_CD")
    private String termSpecCd;

    @Builder
    public TrdTermSpec(
            Long tradeNum,
            String termSpecCd
    ) {
        this.tradeNum = tradeNum;
        this.termSpecCd = termSpecCd;
    }

    public void setTrdTerm(TrdTerm trdTerm) {
        if(this.trdTerm != null) {
            this.trdTerm = null;
        }
        this.trdTerm = trdTerm;
    }

}
