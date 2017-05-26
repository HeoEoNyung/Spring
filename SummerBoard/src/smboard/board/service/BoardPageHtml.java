package smboard.board.service;

public class BoardPageHtml {
	
	public static StringBuffer getPageHtml(int currentPage, int totalNum, int showArticleLimit, int showPageLimit, String type, String keyword){
		
		StringBuffer pageHtml = new StringBuffer();
		int startPage = 0;
		int lastPage = 0;
		
		startPage = ((currentPage -1) / showPageLimit) * showPageLimit + 1;
		lastPage = startPage + showPageLimit -1;
		
		if(lastPage > totalNum / showArticleLimit) {
			lastPage = (totalNum / showArticleLimit) + 1;
		}
		
		if(type  == null && keyword == null) {
			if(currentPage == 1) {
				pageHtml.append("<span>");
			}
			else {
				pageHtml.append("<span><a href=\"list.do?page="+(currentPage -1) + "\"><이전></a>&nbsp;&nbsp;");
			}
			
			for(int i = startPage ; i<= lastPage ; i++) {
				if(i == currentPage) {
					pageHtml.append(".&nbsp;<strong>");
					pageHtml.append("<a href=\"list.do?page=" + i + "\" class=\"page\">" + i + "</a>");
					pageHtml.append("&nbsp;</strong>");
				} else {
					pageHtml.append(".&nbsp;<a href=\"list.do?page=" +i + "\" class=\"page\">" + i + "</a>&nbsp;");
				}
			}
			
			if(currentPage == lastPage) {
				pageHtml.append(".</span>");
			}
			else {
				pageHtml.append(".&nbsp;&nbsp;<a href=\"list.do?page=" + (currentPage+1) + "\"><다음></a></span>");
			}
		}
		else {
			if(currentPage == 1) {
				pageHtml.append("<span>");
			}
			else {
				pageHtml.append("<span><a href=\"list.do?page=" + (currentPage -1) + 
						"&type=" + type + "&keyword=" + keyword + "\"><이전></a>&nbsp;&nbsp;");
			}
			
			for(int i = startPage ; i <= lastPage ; i++) {
				if(i == currentPage){
					pageHtml.append(".&nbsp;<strong>");
					pageHtml.append("<a href=\"list.do?page=" +i + "&type=" + type + "&keyword=" + keyword + "\" class=\"page\">" + i + "</a>&nbsp;");
					pageHtml.append("&nbsp;</strong>");
				} else {
					pageHtml.append(".&nbsp;<a href=\"list.do?page=" +i + "&type=" + type + "&keyword=" + keyword + "\" class=\"page\">" + i + "</a>&nbsp;");
				}
				
			}
			
			if(currentPage == lastPage){
				pageHtml.append("</span>");
			} else {
				pageHtml.append(".&nbsp;&nbsp;<a href=\"list.do?page=" + (currentPage+1) + "&type=" + type + "&keyword=" + keyword + "\"><다음></a></span>");
			}
		}
		return pageHtml;
		
	}
}
