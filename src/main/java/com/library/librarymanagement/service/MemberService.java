    package com.library.librarymanagement.service;

    import com.library.librarymanagement.entity.Member;
    import com.library.librarymanagement.exception.ResourceNotFoundException;
    import com.library.librarymanagement.repository.MemberRepository;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class MemberService {
        private final MemberRepository memberRepository;


        public MemberService(MemberRepository memberRepository) {
            this.memberRepository = memberRepository;
        }

        //addMember
        public Member addMember(Member member){
            return memberRepository.save(member);
        }

        //getAllMember
        public List<Member> getAllMembers(){
            return  memberRepository.findAll();
        }

        //getMemberById
        public Member getMemberById(Long id){
            return memberRepository
                    .findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Member not found with id: " + id));
        }

        //updateMamber
        public Member updateMember(Long id, Member member){
            Member member1 = getMemberById(id);

            member1.setEmail(member.getEmail());
            member1.setName(member.getName());
            member1.setPhone(member.getPhone());

            return memberRepository.save(member1);

        }

        //deactivateMember
        public void deactivateMember(Long id){
            Member member = getMemberById(id);
            member.setActive(false);
            memberRepository.save(member);
        }
    }
