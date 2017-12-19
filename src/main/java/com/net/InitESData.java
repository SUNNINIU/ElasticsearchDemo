package com.net;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.net.esdemo.manager.ESCRUDManager;
import com.net.esdemo.model.Goods;

/**
 * @author 作者 E-mail: issacgo@outlook.com
 * @date 创建时间：2017年12月14日 下午5:02:43
 * @explain 初始化es数据,本案例使用硬编码添加测试数据,实际应用中应改为获取Database相应的表数据
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Component
public class InitESData implements CommandLineRunner {
	protected final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);
	@Autowired
	private  ESCRUDManager escrud;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		logger.info("初始化数据");

        List<Goods> goodsList = new ArrayList<Goods>();  
          
        String[] r123 = {"r1","r2","r3"};  
        String[] str={"雀巢咖啡","雅哈咖啡", "星巴克咖啡","可口可乐","猫屎咖啡",
    			"Affligem Blonde", "Amsterdam Big Wheel", "Amsterdam Boneshaker IPA", "Amsterdam Downtown Brown", "Amsterdam Oranje Summer White",
    			"Anchor Liberty Ale", "Beaus Lug Tread Lagered Ale", "Beerded Lady", "Belhaven Best Ale", "Big Rock Grasshopper Wheat",
    			"Big Rock India Pale Ale", "Big Rock Traditional Ale", "Big Rock Warthog Ale", "Black Oak Nut Brown Ale", "Black Oak Pale Ale",
    			"Boddingtons Pub Ale", "Boundary Ale", "Caffreys", "Camerons Auburn Ale", "Camerons Cream Ale", "Camerons Rye Pale Ale", "Ceres Strong Ale",
    			"Cheval Blanc", "Crazy Canuck Pale Ale", "Creemore Springs Altbier", "Crosswind Pale Ale", "De Koninck", "Delirium Tremens",
    			"Erdinger Dunkel Weissbier", "Erdinger Weissbier", "Export", "Flying Monkeys Amber Ale", "Flying Monkeys Antigravity",
    			"Flying Monkeys Hoptical", "Flying Monkeys Netherworld", "Flying Monkeys Smashbomb", "Fruli", "Fullers Extra Spec Bitter",
    			"Fullers London Pride", "Granville English Bay Pale", "Granville Robson Hefeweizen", "Griffon Pale Ale", "Griffon Red Ale",
    			"Hacker-Pschorr Hefe Weisse", "Hacker-Pschorr Munchen Gold", "Hockley Dark Ale", "Hoegaarden", "Hops & Robbers IPA", "Houblon Chouffe",
    			"James Ready Original Ale", "Kawartha Cream Ale", "Kawartha Nut Brown Ale", "Kawartha Premium Pale Ale", "Kawartha Raspberry Wheat",
    			"Keiths", "Keiths Cascade Hop Ale", "Keiths Galaxy Hop Ale", "Keiths Hallertauer Hop Ale", "Keiths Hop Series Mixer",
    			"Keiths Premium White", "Keiths Red", "Kilkenny Cream Ale", "Konig Ludwig Weissbier", "Kronenbourg 1664 Blanc", "La Chouffe",
    			"La Messager Red Gluten Free", "Labatt 50 Ale", "Labatt Bass Pale Ale", "Lakeport Ale", "Leffe Blonde", "Leffe Brune",
    			"Legendary Muskoka Oddity", "Liefmans Fruitesse", "Lions Winter Ale", "Maclays", "Mad Tom IPA", "Maisels Weisse Dunkel",
    			"Maisels Weisse Original", "Maredsous Brune", "Matador 2.0 El Toro Bravo", "Mcauslan Apricot Wheat Ale", "Mcewans Scotch Ale",
    			"Mill St Belgian Wit", "Mill St Coffee Porter", "Mill St Stock Ale", "Mill St Tankhouse Ale", "Molson Stock Ale", "Moosehead Pale Ale",
    			"Mort Subite Kriek", "Muskoka Cream Ale", "Muskoka Detour IPA", "Muskoka Harvest Ale", "Muskoka Premium Dark Ale", "Newcastle Brown Ale",
    			"Niagaras Best Blonde Prem", "Okanagan Spring Pale Ale", "Old Speckled Hen", "Ommegang Belgian Pale Ale", "Ommegang Hennepin", "PC IPA",
    			"Palm Amber Ale", "Petrus Blonde", "Petrus Oud Bruin Aged Red", "Publican House Ale", "Red Cap", "Red Falcon Ale", "Rickards Dark",
    			"Rickards Original White", "Rickards Red", "Rodenbach Grand Cru", "Schofferhofer Hefeweizen", "Shock Top Belgian White",
    			"Shock Top Raspberry Wheat", "Shock Top Variety Pack", "Sleeman Cream Ale", "Sleeman Dark", "Sleeman India Pale Ale", "Smithwicks Ale",
    			"Spark House Red Ale", "St. Ambroise India Pale Ale", "St. Ambroise Oatmeal Stout", "St. Ambroise Pale Ale", "Stereovision Kristall Wheat",
    			"Stone Hammer Dark Ale", "Sunny & Share Citrus Saison", "Tetleys English Ale", "Thirsty Beaver Amber Ale", "True North Copper Altbier",
    			"True North Cream Ale", "True North India Pale Ale", "True North Strong", "True North Wunder Weisse", "Twice As Mad Tom IPA",
    			"Unibroue La Fin Du Monde", "Unibroue Maudite", "Unibroue Trois Pistoles", "Upper Canada Dark Ale", "Urthel Hop-It Tripel IPA",
    			"Waterloo IPA", "Weihenstephan Kristalweiss", "Wellington Arkell Best Bitr", "Wellington County Dark Ale", "Wellington Special Pale", "Wells IPA"
    		};
        for (int i = 0; i < str.length; i++) {
			goodsList.add(new Goods(i+1L, str[i], r123));  
		}
          
        try {
        	escrud.createIndex(goodsList,"goods");  
            logger.info("成功初始化数据");
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("初始化数据失败");
			e.printStackTrace();
		}
   
	}

}
