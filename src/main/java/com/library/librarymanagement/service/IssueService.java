package com.library.librarymanagement.service;


import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.IssueRecord;
import com.library.librarymanagement.entity.Member;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.IssueRecordRepository;
import com.library.librarymanagement.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.library.librarymanagement.exception.ResourceNotFoundException;
import com.library.librarymanagement.exception.BadRequestException;


import java.time.LocalDate;
import java.util.List;

@Service
public class IssueService {

    private final IssueRecordRepository issueRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public IssueService(IssueRecordRepository issueRecordRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.issueRecordRepository = issueRecordRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    //IssueBook
    @Transactional
    public IssueRecord issueBook(Long bookId, Long memberId){
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Book Not Found with id: "+bookId));

        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found with id: " + memberId));

        if (!member.getActive()) {
            throw new BadRequestException("Member is inactive");
        }


        if(book.getAvailableCopies()<=0){
            throw new BadRequestException("Book Not Available");
        }

        book.setAvailableCopies(book.getAvailableCopies()-1);
        if(book.getAvailableCopies()==0){
            book.setStatus(Book.Status.UNAVAILABLE);
        }
        bookRepository.save(book);

        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setBook(book);
        issueRecord.setMember(member);
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(10));
        issueRecord.setStatus(IssueRecord.Status.ISSUED);

        return issueRecordRepository.save(issueRecord);
    }

    //ReturnBook
    @Transactional
    public IssueRecord returnBook(Long issueId){
        IssueRecord issueRecord = issueRecordRepository
                .findById(issueId)
                .orElseThrow(()->new ResourceNotFoundException("Issue record not found with id: "+issueId));

        if(issueRecord.getStatus()== IssueRecord.Status.RETURNED){
            throw new BadRequestException("Book Already Returned");
        }
        LocalDate returnDate = LocalDate.now();
        issueRecord.setReturnDate(returnDate);

        LocalDate dueDate = issueRecord.getDueDate();

        if(returnDate.isAfter(dueDate)){
            long lateDay = dueDate.until(returnDate).getDays();
            issueRecord.setFineAmount(lateDay * 50.0);
            issueRecord.setStatus(IssueRecord.Status.OVERDUE);
        }else{
            issueRecord.setFineAmount(0.0);
            issueRecord.setStatus(IssueRecord.Status.RETURNED);
        }

        Book book = issueRecord.getBook();
        book.setAvailableCopies(book.getAvailableCopies()+1);
        book.setStatus(Book.Status.AVAILABLE);
        bookRepository.save(book);

        return issueRecordRepository.save(issueRecord);
    }

    //Get All Issue Record
    public List<IssueRecord> getAllIssue(){
        return issueRecordRepository.findAll();
    }

    //Get Issue by member
    public List<IssueRecord> getIssueByMember(Long memberId){
        return issueRecordRepository.findByMemberId(memberId);
    }

    //Get Issue by book
    public List<IssueRecord> getIssueByBook(Long bookId){
        return issueRecordRepository.findByBookId(bookId);
    }
}
