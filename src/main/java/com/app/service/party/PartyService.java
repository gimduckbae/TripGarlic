package com.app.service.party;

import com.app.dto.party.PartyDto;

public interface PartyService {
	
	public PartyDto joinParty(PartyDto partyDto); // 동행신청
	
	public PartyDto myTeamDetail();		// 여행팀 멤버 조회
	
	public PartyDto addMember(int no); // 팀원 업데이트
}
