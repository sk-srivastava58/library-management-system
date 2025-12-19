package com.library.librarymanagement.controller;

import com.library.librarymanagement.entity.IssueRecord;
import com.library.librarymanagement.service.IssueService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issue")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService){
        this.issueService = issueService;
    }

    //issue Book
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    @PostMapping("/book/{bookId}/member/{memberId}")
    public IssueRecord issueBook(@PathVariable Long bookId,@PathVariable Long memberId){
        return  issueService.issueBook(bookId,memberId);
    }

    //Return Book
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    @PutMapping("/return/{issueId}")
    public IssueRecord returnBook(@PathVariable Long issueId){
        return issueService.returnBook(issueId);
    }

    //Get All Issue
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<IssueRecord> getAllIssue(){
        return issueService.getAllIssue();
    }

    //Get Issue by Member
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','MEMBER')")
    @GetMapping("/member/{memberId}")
    public List<IssueRecord> getIssuesByMember(@PathVariable Long memberId){
        return issueService.getIssueByMember(memberId);
    }

    //Get Issue by Book
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    @GetMapping("/book/{bookId}")
    public List<IssueRecord> getIssuesByBook(@PathVariable Long bookId){
        return  issueService.getIssueByBook(bookId);
    }
}
