package kiz.space.controller;

import kiz.space.dto.MemberDTO;
import kiz.space.service.MemberService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/member/join")
    public ModelAndView memberJoin() {
        return new ModelAndView("html/member/join");
    }

    @GetMapping(value = "/api/members")
    public List<MemberDTO.Response> findAll() {

        List<MemberDTO.Response> result = memberService.findAll();

        return result;
    }

    @PostMapping(value = "/api/members")
    public void save(@RequestBody MemberDTO.Save dto) {
        System.out.println(dto);
        //memberService.save(dto);
    }

    @GetMapping(value = "/api/members2")
    public void get(MemberDTO.Search dto) {
        System.out.println(dto);
        //memberService.save(dto);
    }
}
