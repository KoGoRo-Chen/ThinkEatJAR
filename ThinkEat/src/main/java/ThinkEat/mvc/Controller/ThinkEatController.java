package ThinkEat.mvc.Controller;

import ThinkEat.mvc.Service.EatRepoService;
import ThinkEat.mvc.Service.PriceDataService;
import ThinkEat.mvc.Service.ResDataService;
import ThinkEat.mvc.Service.TagDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class ThinkEatController {
	private EatRepoService eatRepoService;
	private ResDataService resDataService;
	private PriceDataService priceDataService;
	private TagDataService tagDataService;

	@Autowired
	public ThinkEatController(EatRepoService eatRepoService,
							  ResDataService resDataService,
							  PriceDataService priceDataService,
							  TagDataService tagDataService) {
		this.eatRepoService = eatRepoService;
		this.resDataService = resDataService;
		this.priceDataService = priceDataService;
		this.tagDataService = tagDataService;
	}

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


