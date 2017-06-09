package com.test.Mybatis;

import java.util.ArrayList;

public interface MemberDAO {
	
	public ArrayList<Member> getMembers();
	public void inserMember(Member member);
	public void updateMember(String name);
	public void deleteMember(String name);
	
}
