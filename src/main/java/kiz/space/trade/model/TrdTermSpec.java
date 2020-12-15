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
    @GeneratedValue(generator="SharedPrimaryKeyGenerator")
    @GenericGenerator(name="SharedPrimaryKeyGenerator",
            strategy="foreign",
            parameters = @org.hibernate.annotations.Parameter(name="property", value="trdTerm")
    )
    private Long termNum;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TERM_NUM",referencedColumnName="TERM_NUM")
    private TrdTerm trdTerm;

    @Column(name = "TRADE_NUM")
    private Long tradeNum;

    @Column(name = "TERM_SPEC_CD")
    private String termSpecCd;

    @Builder
    public TrdTermSpec(
            TrdTerm trdTerm,
            Long tradeNum,
            String termSpecCd
    ) {
        this.trdTerm = trdTerm;
        this.tradeNum = tradeNum;
        this.termSpecCd = termSpecCd;
    }

    public void setTrdTerm(TrdTerm trdTerm) {
        this.trdTerm = trdTerm;
    }

}
