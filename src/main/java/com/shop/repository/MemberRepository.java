package com.shop.repository;

import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);
    // 회원가입 시 중복 회원 있는지 검사하기 위해 이메일로 회원을 검사할 수 있도록 쿼리 메소드를 작성
}
