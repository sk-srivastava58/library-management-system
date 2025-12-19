package com.library.librarymanagement.controller;

import com.library.librarymanagement.entity.Member;
import com.library.librarymanagement.service.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //addMember
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Member addMember(@RequestBody Member member){
        return memberService.addMember(member);
    }

    //getAllMember
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    @GetMapping
    public List<Member> getAllMembers(){
        return memberService.getAllMembers();
    }

    //getMemberBYId
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id){
        return memberService.getMemberById(id);
    }

    //updateMamber
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id,@RequestBody Member member){
        return memberService.updateMember(id, member);
    }

    //DeactivateMember
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/deactivate/{id}")
    public String deactivate(@PathVariable Long id){
        memberService.deactivateMember(id);
        return "Member Deactivated Successfully";
    }
}

