package net.madvirus.spring4.board;

public class WriteArticleServiceImpl implements WriteArticleService {
	
	private ArticleDao articleDao;
	
	public WriteArticleServiceImpl(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	@Override
	public void write(NewArticleRequest newArticleRequest) {
		System.out.println("WriteArticleServiceImpl.write() 호출 됨");
		Article article = toArticle(newArticleRequest);
		articleDao.insert(article);
		
	}
	
	private Article toArticle(NewArticleRequest newArticleRequest) {
		return new Article(newArticleRequest.getWriterName(),
							newArticleRequest.getTitle(),
							newArticleRequest.getContent());
	}
	
	
	 
}
