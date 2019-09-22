package com.leel2415.kakaopay.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	@Id
	private String userId;
	private String userPass;
}
