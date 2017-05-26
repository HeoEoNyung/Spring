package smboard.board.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	// ¾÷·Îµå
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
	
	/*@RequestMapping("/commentWrite.do")
	public ModelAndView commentWriteProc(@ModelAttribute)*/
	
	
}
