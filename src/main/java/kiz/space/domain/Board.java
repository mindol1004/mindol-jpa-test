package kiz.space.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOARD_ID")
    private Long boardId;

    @Column(name = "BOARD_TITLE" , length = 100)
    private String boardTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID", nullable = false),
            @JoinColumn(name = "MEMBER_SEQ", referencedColumnName = "MEMBER_SEQ", nullable = false)
    })
    private Member member;
//
//    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Comment> comments = new HashSet<>();

    @Builder
    public Board(String boardTitle, Member member) {
        this.boardTitle = boardTitle;
        this.member = member;
    }

    public void setMember(Member member) {
        if(this.member != null) {
            this.member.getBoards().remove(this);
        }
        this.member = member;

        if(member != null) {
            member.getBoards().add(this);
        }
    }

}
