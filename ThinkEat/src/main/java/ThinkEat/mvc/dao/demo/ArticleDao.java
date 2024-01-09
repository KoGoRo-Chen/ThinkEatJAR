package ThinkEat.mvc.dao.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.entity.demo.Article;

public interface ArticleDao extends JpaRepository<Article, Integer>{

}
