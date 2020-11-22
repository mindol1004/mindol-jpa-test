package kiz.space.controller;

import kiz.space.dto.MemberDTO;
import kiz.space.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/api/members")
    public List<MemberDTO.Response> findAll() {

        List<MemberDTO.Response> result = memberService.findAll();

        return result;
    }

    @PostMapping(value = "/api/members")
    public void save(@RequestBody MemberDTO.Save dto) {
        memberService.save(dto);
    }
}
