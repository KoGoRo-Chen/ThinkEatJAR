package ThinkEat.mvc.controller;

import ThinkEat.mvc.dao.EatDataDao;
import ThinkEat.mvc.dao.ShareEatDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class ThinkEatController {
		
	@Autowired
	@Qualifier("shareEatDaoImplInMemory")
	private ShareEatDao shareEatDao;
	
	@Autowired
	@Qualifier("eatDataDaoImplInMemory")
	private EatDataDao eatDataDao;

	//顯示首頁
	@GetMapping("/Index")
	public String GetIndexPage() {
		return "Index";
	};
	
	//顯示收藏頁面
	@GetMapping("/MyFavEat")
	public String GetMyFavEatPage(){
		return "MyFavEat";
	};

	//顯示抽選頁面
	@GetMapping("/DecideEat")
	public String GetDecideEatPage(){
		return "DecideEat";
	};

	
}


