package smboard.member.dao;

import smboard.member.model.MemberModel;

public interface MemberDao {

	boolean addMember(MemberModel memberModel);
	
	MemberModel findByUserId(String userId);
	
}
