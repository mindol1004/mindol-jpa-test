package kiz.space.service;

import kiz.space.domain.Board;
import kiz.space.domain.Member;
import kiz.space.dto.MemberDTO;
import kiz.space.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDTO.Response> findAll() {

        List<Member> result = memberRepository.findAll();

        return MemberDTO.Response.of(result);
    }

    @Transactional
    public void save(MemberDTO.Save dto) {
        final Member member = dto.toEntity();

        dto.getBoards().forEach(boardDto -> {
            Board board = Board.builder().member(member).boardTitle(boardDto.getBoardTitle()).build();
            member.addBoard(board);
        });

        memberRepository.save(member);
    }
}
