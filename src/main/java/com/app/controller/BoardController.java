package com.app.controller;


import com.app.dto.ChatDto;
import com.app.dto.JoinDto;
import com.app.dto.MarkDto;
import com.app.dto.PartyDto;
import com.app.service.board.BoardService;
import com.app.service.chat.ChatService;
import com.app.service.party.PartyService;
import com.app.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;
    @Autowired
    PartyService partyService;
    @Autowired
    UserService userService;
    @Autowired
    ChatService chatService;


    @GetMapping("/detail/{planId}") // 글상세 페이지 요청
    public String detail(@PathVariable("planId") int planId, Model model, HttpSession session) {

        // 요청했을때 모집글정보, 멤버정보, 조회수증가 시켜야함

        // 글 정보 가져오기
        JoinDto item = boardService.findPostById(planId);
        model.addAttribute("item", item);
        
        // 조회수 증가        
        String sessionId = (String) session.getAttribute("userId");
        if(!(sessionId.equals(item.getUserId()))) { //접속자와 작성자가 다를때만
        	boardService.joinBoardViewIncrease(planId);
        }

        // 멤버 정보 리스트 가져오기
        List<PartyDto> list = partyService.myTeamDetail(planId);
        model.addAttribute("CurrPersonnel", list.size()); // 참여중인 인원 몇명
        model.addAttribute("memberList", list); // 참여중인 인원 정보

        // 사진 정보 가져오기
        List<JoinDto> images = boardService.select_images_by_planId(planId);
        model.addAttribute("images", images);
        model.addAttribute("imageCount", images.size());

        return "post_detail";
    }

    @PostMapping("/joinParty") // 동행신청하기 	plan_id 에 user_id insert\
    @ResponseBody
    public String joinParty(@RequestBody String requestBody, HttpSession session) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        PartyDto partyDto = objectMapper.readValue(requestBody, PartyDto.class);


        String userId = (String) session.getAttribute("userId"); //아이디는 세션에서 받아오고
        partyDto.setUserId(userId); //dto에 세션id 세팅

        int result = 0;

        int checkJoin = partyService.checkStatus(partyDto); //여행 참가여부 조회

        if (checkJoin == 0) {
            result = partyService.joinParty(partyDto); //party 테이블에 inserst

            // 입장하는 채팅방 기존 내역 모두 읽음처리
            ChatDto chatDto = new ChatDto();
            chatDto.setPlanId(partyDto.getPlanId());
            chatDto.setUserId(partyDto.getUserId());
            chatService.readAllChatMessage(chatDto);
        }

        if (result > 0) {
            return "true";
        }
        return "false";
    }

    @PostMapping("/joinDead") // 모집 마감?
    @ResponseBody
    public String joinDead(@RequestBody String requestBody, HttpSession session) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        PartyDto partyDto = objectMapper.readValue(requestBody, PartyDto.class);
        partyDto.setUserId(session.getAttribute("userId").toString());

        int result = partyService.joinDead(partyDto); //여행 모집 마감 update 쿼리

        if (result == 1) { //마감성공
            return "true";
        } else {            //마감실패
            return "false";
        }

    }

    @PostMapping("/joinDelete") // 모집 삭제
    @ResponseBody
    public String joinDelete(@RequestBody String requestBody, HttpSession session) throws JsonProcessingException {
        System.out.println("joinDelete 버튼 눌림");
        ObjectMapper objectMapper = new ObjectMapper();
        PartyDto partyDto = objectMapper.readValue(requestBody, PartyDto.class);
        partyDto.setUserId(session.getAttribute("userId").toString());

        int result = partyService.joinDelete(partyDto); //여행 모집 마감 update 쿼리

        if (result == 1) { //삭제성공
            return "true";
        } else {            //삭제실패
            return "false";
        }

    }
    
	@GetMapping("/travelTogether") /* 참가중인 여행 */
    public String travelTogether(Model model, HttpSession httpSession) {
    	
    	String userId = (String) httpSession.getAttribute("userId");
    	
    	
    	List<JoinDto> travelTogether
    						= boardService.travelTogether(userId);
    	model.addAttribute("items",travelTogether);
    	return "travelTogether";
    }
    	
	@GetMapping("/myPartying") /* 내 모집내역 */
    public String myPartying(Model model, HttpSession httpSession) {
    	
    	String userId = (String) httpSession.getAttribute("userId");
    	
    	
    	List<JoinDto> myPartying
    						= boardService.myPartying(userId);
    	model.addAttribute("items",myPartying);
    	return "myPartying";
    }
	@PostMapping("/joinLike") //추천 버튼 클릭
	@ResponseBody
	public int joinLike(@RequestBody String requestBody, HttpSession session) throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		MarkDto markDto = objectMapper.readValue(requestBody, MarkDto.class);
		
		String userId = (String) session.getAttribute("userId");
		
		MarkDto isNull =	//추천 여부 확인 
				boardService.selectBoardMark(markDto.getPlanId(), userId, markDto.getSort());
		
		if (isNull == null) { //추천한 적 없으면
			int count = boardService.insertBoardMark(markDto.getPlanId(), userId, markDto.getSort()); //추천하기
			return count; //추천 성공 (추천 횟수 반환)
		} else {
			return -1;	//추천 실패
		}
	}

}