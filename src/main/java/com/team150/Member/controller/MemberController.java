package com.team150.Member.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team150.Member.Model.MemberVO;
import com.team150.Member.Service.MemberService;

@Controller // 현재 클래스를 Controller Bean으로 등록함 =>컨트롤러는 서비스 호출
public class MemberController {
	// private static final Logger logger =
	// LoggerFactory.getLogger(MemberController.class);

	@Inject // MemberService객체가 주입됨
	MemberService memberService; // =>서비스는 DAO호출
									// => DAO는 mybatis의 mapper를 호출함

//	고객

	// 로그인 (페이지)
	@RequestMapping("/member/loginForm.do")
	public String login_Form() {
		return "/user/member/loginForm"; // member/login.jsp로 가서 로그인할 수 있도록 화면을 만들어둠
	}

	// 로그인 (실행)
	@RequestMapping("/member/loginCheck.do")
	public ModelAndView loginCheck(@ModelAttribute MemberVO vo, HttpSession session) {
		// 로그인 성공시=>이름 넘어옴, 실패하면 =>null
		String name = memberService.loginCheck(vo, session);
		// logger.info("name"+name);
		ModelAndView mav = new ModelAndView();
		if (name != null) {
			// 로그인 성공하면 시작 페이지로 이동
			mav.setViewName("home");

		} else {
			// 로그인 실패하면
			mav.setViewName("user/member/loginForm");// member/login.jsp로 이동하여 다시 로그인
			mav.addObject("message", "error");
		}
		return mav;
	}

	// 로그아웃 (실행)
	@RequestMapping("/member/logOut.do")
	public ModelAndView logOut(HttpSession session, ModelAndView mav) {
		memberService.logOut(session); // 세션 초기화 작업(=session.invalidate();)
		mav.setViewName("home"); // 이동할 페이지의 이름
		mav.addObject("message", "logOut"); // 변수 저장
		return mav; // 페이지로 이동
	}

	// 회원가입 (페이지)
	@RequestMapping("/member/write.do")
	public String write() {
		return "/user/member/loginRegForm";
	}

	// 회원가입 (실행)
	@RequestMapping("/member/insert.do")
	public String insert(MemberVO vo) { // 폼에 입력한 데이터가 MemberDTO dto변수에 저장됨
		memberService.insert(vo);
		return "redirect:/member/loginForm.do";
		/* return "redirect:/member/list.do"; //목록 갱신 */
	}
	
	// 회원리스트 상세 (유저 페이지)
		@RequestMapping("/member/view.do")
		public String viewMember(@RequestParam String uid, Model model) {
			// member_list.jsp의 view.do?userid=kim이라면 @RequestParam String userid 변수에 kim이
			// 저장됨
			// system.out.println("클릭한 아이디: "+userid); //info, debug, warn, error
			// logger.info("클릭한 아이디: "+userid);
			model.addAttribute("vo", memberService.viewMember(uid)); // 회원정보를 모델에 저장
			return "user/member/view";
		}
	
	// 회원리스트 수정 (실행)
		@RequestMapping("/member/modify.do")
		public String modify(@ModelAttribute MemberVO vo, Model model) {
			// ------------------------------
			// view.jsp에서 작성한 form의 정보를 dto에 묶어 보내면 ModelAttribute 쌓임
			boolean result = memberService.checkPw(vo.getUid(), vo.getUpwd());
			// logger.info("비밀번호 확인: "+result);

			if (result) {// 비밀번호가 맞으면
				memberService.modify(vo); // 레코드 수정
				return "home"; // 회원 상제페이지로 이동
			} else {// 비밀번호가 틀리면
				MemberVO vo2 = memberService.viewMember(vo.getUid());
				vo.setUdate(vo2.getUdate());// 날자가 지워지지 않도록
				model.addAttribute("vo", vo); // 모델에 저장
				model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
				return "/user/member/view"; // 수정 페이지로 되돌아감
			}
		}
		// 회원리스트 삭제 유저용(실행)
		@RequestMapping("/member/delete.do")
		public String remove_u(HttpSession session,@RequestParam String uid, @RequestParam String upwd, Model model) {
			boolean result = memberService.checkPw(uid, upwd);
			if (result) {
				memberService.remove_u(uid);
				memberService.logOut(session);
				return "home";
			} else {
				model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
				model.addAttribute("vo", memberService.viewMember(uid));
				return "user/member/view";
			}
		}

//	----------------------------------------------------------------------------

//	관리자

	// 회원리스트 (페이지)
	@RequestMapping("/manager/list.do") // 사용자가 요청하는 주소
	public String listAll(Model model) {
		List<MemberVO> list = memberService.listAll();// 서비스가 목록을 넘김
		// logger.info("회원목록: "+list); //목록이 잘 넘어오는지 로깅으로 체크
		model.addAttribute("list", list);// 출력하기 전 모델에 저장
		return "manager/member/memberList"; // 출력 페이지로 포워딩
	}

	// 회원리스트 상세 (매니저 페이지)
	@RequestMapping("/manager/member/view.do")
	public String viewManager(@RequestParam String uid, Model model) {
		// member_list.jsp의 view.do?userid=kim이라면 @RequestParam String userid 변수에 kim이
		// 저장됨
		// system.out.println("클릭한 아이디: "+userid); //info, debug, warn, error
		// logger.info("클릭한 아이디: "+userid);
		model.addAttribute("vo", memberService.viewManager(uid)); // 회원정보를 모델에 저장
		return "manager/member/view";
	}
	
	// 회원리스트 삭제 관리자용(실행)
	@RequestMapping("/manager/member/delete.do")
	public String remove_m(@RequestParam String uid, Model model) {
			memberService.remove_m(uid);
			return "redirect:/manager/list.do";
	}
}
