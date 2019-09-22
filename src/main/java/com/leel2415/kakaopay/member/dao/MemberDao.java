package com.leel2415.kakaopay.member.dao;

import com.leel2415.kakaopay.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<Member, String>{

	public Member findByUserId(String userId);
}
