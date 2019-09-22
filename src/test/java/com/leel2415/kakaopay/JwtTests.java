package com.leel2415.kakaopay;

import com.leel2415.kakaopay.common.component.JwtComponent;
import com.leel2415.kakaopay.member.dao.MemberDao;
import com.leel2415.kakaopay.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JwtTests {
	
	@Autowired
	private JwtComponent jwtComponent;

	private Member member;

	@Autowired
	private MemberDao memberDao;

	@Before
	public void init(){
		member = Member.builder()
				.userId("leel2415")
				.userPass("12341234")
				.build();

		memberDao.save(member);
	}

	@Test
	public void tokenSuccess() {
		String jwt = jwtComponent.makeJwtToken(member);
		log.debug(jwt);
		assert jwtComponent.checkJwt(jwt, member);
	}
	
	@Test
	public void tokenFail() {
		String jwt = jwtComponent.makeJwtToken(member);

		jwt = jwt.substring(0,jwt.length()-1) + "a";
		assert !jwtComponent.checkJwt(jwt, member);
	}


	
}
