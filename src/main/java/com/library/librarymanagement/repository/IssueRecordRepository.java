package com.library.librarymanagement.repository;

import com.library.librarymanagement.entity.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRecordRepository extends JpaRepository<IssueRecord,Long> {

    //findMemeberById
    List<IssueRecord> findByMemberId(Long memberId);

    //findBookById
    List<IssueRecord> findByBookId(Long bookId);
}
