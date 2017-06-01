package net.madvirus.spring4.board;

public interface ArticleDao {
	
	void insert(Article article);
	
	Article selectByid(Integer id);
	
}
