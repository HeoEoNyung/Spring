package smboard.board.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import smboard.board.model.BoardCommentModel;
import smboard.board.model.BoardModel;
import smboard.board.service.BoardPageHtml;
import smboard.board.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private ApplicationContext context = new ClassPathXmlApplicationContext("/config/applicationContext.xml");
	
	
	private BoardService boardService = (BoardService) context.getBean("boardService");
	
	private int currentPage = 1;
	private int showArticleLimit = 10;
	private int showPageLimit = 10;
	private int startArticleNum = 0;
	private int endArticleNum = 0;
	private int totalNum = 0;
	
	// ���ε�
	private String uploadPath = "C:\\Java\\eclipse\\Workspace\\SummerBoard\\WebContent\\files\\";
	
	@RequestMapping("/list.do")
	public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response) {
		
		String type = null;
		String keyword = null;
		
		if(request.getParameter("page") == null || request.getParameter("page").trim().isEmpty()
				|| request.getParameter("page").equals("0")) {
			
			currentPage = 1;
		}
		
		else {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		if(request.getParameter("type") != null ) {
			type = request.getParameter("type").trim();
		}
		
		if(request.getParameter("keyword") != null) {
			keyword = request.getParameter("keyword").trim();
		}
		
		startArticleNum = (currentPage - 1) * showArticleLimit + 1;
		endArticleNum = startArticleNum + showArticleLimit - 1;
		
		List<BoardModel> boardList;
		if(type != null && keyword != null) {
			boardList = boardService.searchArticle(type, keyword, startArticleNum, endArticleNum);
			totalNum = boardService.getSearchTotalNum(type, keyword);
		}
		else {
			
			boardList = boardService.getBoardList(startArticleNum, endArticleNum);
			totalNum = boardService.getTotalNum();
		}
		
		StringBuffer pageHtml = BoardPageHtml.getPageHtml(currentPage, totalNum, showArticleLimit, showPageLimit, type, keyword);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardList", boardList);
		mav.addObject("pageHtml", pageHtml);
		mav.setViewName("/board/list");
		
		return mav;
	}
	
	@RequestMapping("/view.do")
	public ModelAndView boardView (HttpServletRequest request) {
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		BoardModel board = boardService.getOneArticle(idx);
		
		boardService.updateHitcount(board.getHitcount() + 1, idx);
		
		List<BoardCommentModel> commentList = boardService.getCommentList(idx);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("board", board);
		mav.addObject("commentList", commentList);
		mav.setViewName("/board/view");
		return mav;
	}
	
	@RequestMapping("/write.do")
	public String boardWrite(@ModelAttribute("BoardModel") BoardModel boardModel) {
		return "/board/write";
	}
	
	@RequestMapping(value="/write.do", method = RequestMethod.POST)
	public String boardWriteProc (@ModelAttribute("BoardModel") BoardModel boardModel, MultipartHttpServletRequest request) {
		MultipartFile file = request.getFile("file");
		String fileName = file.getOriginalFilename();
		File uploadFile = new File(uploadPath + fileName);
		
		if(uploadFile.exists()) {
			fileName = new Date().getTime() + fileName;
			uploadFile = new File(uploadPath + fileName);
		}
		
		try{
			file.transferTo(uploadFile);
		}catch(Exception e) {
			
		}
		boardModel.setFileName(fileName);
		
		String content = boardModel.getContent().replaceAll("\r\n", "<br/>");
		boardModel.setContent(content);
		boardService.writeArticle(boardModel);
		
		return "redirect:list.do";
	}
	
	@RequestMapping("/commentWrite.do")
	public ModelAndView commentWriteProc(@ModelAttribute("CommentModel") BoardCommentModel commentModel) {
		String content = commentModel.getContent().replaceAll("\r\n", "<br/>");
		commentModel.setContent(content);
		
		boardService.writeComment(commentModel);
		ModelAndView mav = new ModelAndView();
		mav.addObject("idx", commentModel.getLinkedArticleNum());
		mav.setViewName("redirect:view.do");
		
		return mav;
	}
	
	@RequestMapping("/modify.do")
	public ModelAndView boardModify(HttpServletRequest request, HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		BoardModel board = boardService.getOneArticle(idx);
		
		String content = board.getContent().replaceAll("<br/>", "\r\n");
		
		ModelAndView mav = new ModelAndView();
		
		if(!userId.equals(board.getWriterId())) {
			mav.addObject("errCode", "1");
			mav.addObject("idx", idx);
			mav.setViewName("redirect:view.do");
		}
		else {
			mav.addObject("board", board);
			mav.setViewName("/board/modify");
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	public ModelAndView boardModifyProc(@ModelAttribute("BoardModel")BoardModel boardModel, MultipartHttpServletRequest request) {
		String orgFileName = request.getParameter("orgFile");
		MultipartFile newFile = request.getFile("newFile");
		String newFileName = newFile.getOriginalFilename();
		
		boardModel.setFileName(orgFileName);
		
		if(newFile != null && !newFileName.equals("")) {
			if(orgFileName != null && orgFileName.equals("")) {
				File removeFile = new File(uploadPath + orgFileName);
				removeFile.delete();
			}
			
			File newUploadFile = new File(uploadPath + newFileName);
			try{
				newFile.transferTo(newUploadFile);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			boardModel.setFileName(newFileName);
		}
		
		String content = boardModel.getContent().replaceAll("\r\n", "<br/>");
		boardModel.setContent(content);
		
		boardService.modifyArticle(boardModel);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("idx", boardModel.getIdx());
		mav.setViewName("redirect:/board/view.do");
		
		return mav;
		
	}
	
	@RequestMapping("/delete.do")
	public ModelAndView boardDelete (HttpServletRequest request, HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		BoardModel board = boardService.getOneArticle(idx);
		ModelAndView mav = new ModelAndView();
		
		if(!userId.equals(board.getWriterId())) {
			mav.addObject("errcode", "1");
			mav.addObject("idx", idx);
			mav.setViewName("redirect:view.do");
		}
		else {
			List<BoardCommentModel> commentList = boardService.getCommentList(idx);
			if(commentList.size() > 0) {
				mav.addObject("errCode", "2");
				mav.addObject("idx", idx);
				mav.setViewName("redirect:view.do");
			}
			else {
				if(board.getFileName() != null) {
					File removeFile = new File(uploadPath + board.getFileName());
					removeFile.delete();
				}
				
				boardService.deleteArticle(idx);
				
				mav.setViewName("redirect:list.do");
			}
		}
		return mav;
	}
	
	@RequestMapping("/commentDelete.do")
	public ModelAndView commendDelete(HttpServletRequest request, HttpSession session) {
		int idx = Integer.parseInt(request.getParameter("idx"));
		int linkedArticleNum = Integer.parseInt(request.getParameter("linkedArticleNum"));
		System.out.println("11111111");
		String userId = (String) session.getAttribute("userId");
		BoardCommentModel comment = boardService.getOneComment(idx);
		
		ModelAndView mav = new ModelAndView();
		
		if(!userId.equals(comment.getWriterId())) {
			mav.addObject("errCode", "1");
		}
		else {
			System.out.println("2222222222");
			boardService.deleteComment(idx);
		}
		System.out.println("33333333");
		mav.addObject("idx", linkedArticleNum);
		mav.setViewName("redirect:view.do");
		
		return mav;
	}
	
	@RequestMapping("/recommend.do")
	public ModelAndView updateRecommendcount(HttpServletRequest request, HttpSession session) {
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		String userId = (String) session.getAttribute("userId");
		BoardModel board = boardService.getOneArticle(idx);
		
		ModelAndView mav = new ModelAndView();
		
		if(userId.equals(board.getWriterId())) {
			mav.addObject("errCode", "1");
		}else {
			boardService.updateRecommendCount(board.getRecommendcount() +1, idx);
		}
		
		mav.addObject("idx", idx);
		mav.setViewName("redirect:/board/view.do");
		
		return mav;
	}
	
	
}
