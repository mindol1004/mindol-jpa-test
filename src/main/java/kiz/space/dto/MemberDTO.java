package kiz.space.dto;

import kiz.space.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MemberDTO {

    @Getter
    @Setter
    public static class Search {
        private Long memberSeq;
        private String memberName;
        private Integer[] number;
        private String[] str;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Save {
        private Long memberSeq;
        private String memberName;
        private Set<BoardDto> boards;
        private Integer[] number;

        public Member toEntity() {
            return Member.builder()
                    .memberSeq(this.memberSeq)
                    .memberName(this.memberName)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class Response {
        private Long memberId;
        private Long memberSeq;
        private String memberName;
        private List<BoardDto> boards;
//        private List<CommentResponse> comments;

        public static Response of(Member member) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(member, Response.class);
        }

        public static List<Response> of(List<Member> members) {
            return members.stream()
                    .map(Response::of)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class BoardDto {
        private Long boardId;
        private String boardTitle;
    }

    @Getter
    @Setter
    public static class CommentDto {
        private Long commentId;
        private String commentTitle;
    }


}
