package com.cg.example.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cg.example.bean.DiscountBean;
import com.cg.example.bean.FeedbackProductBean;
import com.cg.example.bean.ImageBean;
import com.cg.example.bean.ProductBean;
import com.cg.example.bean.PromosBean;

@RestController
public class FrontController {

	@RequestMapping("/")
	public String consume(){
		return "REST Consumer";
	}
	
	@RequestMapping(value="/DiscountDetails")
	public ModelMap showWelcomePage(String id, ModelMap model){
		
		RestTemplate restTemplate = new RestTemplate();
		DiscountBean discount = restTemplate.getForObject("http://localhost:9002/searchdiscountid?Id="+id, DiscountBean.class);
		System.out.println(discount);
		model.put("discount", discount);
		return model;
	}
	
	@RequestMapping(value="/ProductDetails",method=RequestMethod.GET)
	public ModelMap findByProductId(String productId, ModelMap model){
		
		RestTemplate restTemplate = new RestTemplate();
		ProductBean product = restTemplate.getForObject("http://localhost:9002/getproductdetailsbyid?productId="+productId, ProductBean.class);
		
		model.put("product", product);
		System.out.println(product);
		return model;
	}
	
	@RequestMapping(value="/PromoDetails")
	public ModelMap findPromoById(String id, ModelMap model){
		
		RestTemplate restTemplate = new RestTemplate();
		PromosBean promo = restTemplate.getForObject("http://localhost:9002/searchpromocode?Code="+id, PromosBean.class);
		System.out.println(promo);
		model.put("promo", promo);
		return model;
	}
	
	@RequestMapping(value="/Categories",method=RequestMethod.GET)
	public ModelMap findByCategory(String category, ModelMap model){
		
		RestTemplate restTemplate = new RestTemplate();
		List<ProductBean> response = restTemplate.getForObject("http://localhost:9002/searchcategory?category="+category, List.class);
		
		model.put("products", response);
		System.out.println(response);
		return model;
	}
	
	/*@RequestMapping(value="/Home")
	public DiscountBean viewMerchantProfile(){
		RestTemplate restTemplate = new RestTemplate();
		DiscountBean message = restTemplate.getForObject("http://localhost:9090/getbyDiscountId?discountId=DS50", DiscountBean.class);
		return message;
	}*/
	
	@RequestMapping(value="/addDiscount",method=RequestMethod.POST)
	DiscountBean addDiscountByMerchant(Model model,DiscountBean discount) {
		model.addAttribute("discount", discount);
		System.out.println(discount.toString());
		RestTemplate restTemplate = new RestTemplate();
		
		DiscountBean str=restTemplate.postForObject("http://localhost:9002/adddiscount", discount, DiscountBean.class);
		
		return str;
	}
	
	@RequestMapping(value="/addPromo",method=RequestMethod.POST)
	String addPromoByMerchant(Model model,PromosBean promo) {
		model.addAttribute("promo", promo);
		System.err.println(promo);
		System.err.println("sending..");
		RestTemplate restTemplate = new RestTemplate();
		String promoBean=restTemplate.postForObject("http://localhost:9002/addpromo", promo, String.class);
		System.err.println("Got data.."+promoBean);
		//model.addAttribute("promoBean", promoBean);
		return promoBean;
	}
	
	@RequestMapping(value="/addProduct",method = RequestMethod.POST)
	ProductBean addProductByMerchant(Model model,ProductBean productBean/*,ImageBean imageBean,FeedbackProductBean feedback*/,PromosBean promoBean,DiscountBean discount) {
		
		model.addAttribute("product",productBean);
		/*model.addAttribute("image",imageBean);
		model.addAttribute("feedback",feedback);*/
		model.addAttribute("promoCode", promoBean);
		model.addAttribute("discount", discount);
		System.out.println(productBean.toString());
		RestTemplate restTemplate = new RestTemplate();
		
		ProductBean productBean1=restTemplate.postForObject("http://localhost:9002/addnewproduct", productBean, ProductBean.class);
		return productBean1;
		
	}
	
/*	@RequestMapping(value="/updateProduct",method = RequestMethod.PUT)
	String updateProductByMerchant(Model model,ProductBean productBean,ImageBean imageBean,FeedbackProductBean feedback,PromosBean promoBean,DiscountBean discount) {
		
		model.addAttribute("product",productBean);
		model.addAttribute("image",imageBean);
		model.addAttribute("feedback",feedback);
		model.addAttribute("promoCode", promoBean);
		model.addAttribute("discountId", discount);
		System.out.println(productBean.toString());
		RestTemplate restTemplate = new RestTemplate();
		String str=restTemplate.postForObject("http://localhost:9090/updateProductDetails", productBean, String.class);
		return str;
		
	}*/
	@RequestMapping(value="/updateProduct",method = RequestMethod.PUT)
	 public void updateProductByMerchant(Model model,ProductBean productBean,ImageBean imageBean,FeedbackProductBean feedback,PromosBean promoBean,DiscountBean discount) {
		
		model.addAttribute("product",productBean);
		model.addAttribute("image",imageBean);
		model.addAttribute("feedback",feedback);
		model.addAttribute("promoCode", promoBean);
		model.addAttribute("discountId", discount);
		System.out.println(productBean.toString());
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.patchForObject("http://localhost:9002/updateproductdetails",productBean, ProductBean.class);
	
		
	}
	
	@RequestMapping("/ViewAllDiscounts")
	public ModelMap viewAllDiscounts(ModelMap model) {
		RestTemplate restTemplate = new RestTemplate();
		List<DiscountBean> response = restTemplate.getForObject("http://localhost:9002/viewalldiscount", List.class);
		model.put("discount", response);
		return model;
	}
	
	@RequestMapping("/ViewAllPromos")
	public ModelMap viewAllPromos(ModelMap model) {
		RestTemplate restTemplate = new RestTemplate();
		List<PromosBean> response = restTemplate.getForObject("http://localhost:9002/viewallpromos", List.class);
		model.put("promo", response);
		return model;
	}
	
	@RequestMapping("/ViewAllProducts")
	public ModelMap viewAllProducts(ModelMap model) {
		RestTemplate restTemplate = new RestTemplate();
		List<ProductBean> response = restTemplate.getForObject("http://localhost:9002/displayallproducts", List.class);
		model.put("product", response);
		return model;
	}
	
	@RequestMapping(value="/ViewAllCategories",method=RequestMethod.GET)
	public ModelMap viewAllProductsByCategories(ModelMap model) {
		RestTemplate restTemplate = new RestTemplate();
		List<String> response = restTemplate.getForObject("http://localhost:9002/displayAllCategory", List.class);
		model.put("product", response);
		return model;
	}
	
/*	@RequestMapping(value="/removeProduct",method = RequestMethod.DELETE)
	public String removeProductByMerchant(ProductBean productBean,String productId) {
		RestTemplate restTemplate = new RestTemplate();
		System.err.println(productBean);
		String str=restTemplate.getForObject("http://localhost:9090/deleteProduct?productId="+productId, String.class);
		return str;
		
	}*/
	
	@RequestMapping(value="/removeProduct"/*,method = RequestMethod.DELETE*/)
	public  void removeProductByMerchant(ProductBean productBean,String productId) {
		RestTemplate restTemplate = new RestTemplate();
		System.err.println(productBean);
		restTemplate.delete("http://localhost:9002/deleteproduct?productId="+productId);;
		
	}
	/*@RequestMapping(value="/RemoveCategory",method = RequestMethod.DELETE)
	public String removeCategoryByMerchant(ProductBean productBean,String id) {
		RestTemplate restTemplate = new RestTemplate();
		String str=restTemplate.postForObject("http://localhost:9002/removeExistingCategory?category="+id, productBean, String.class);
		return str;
		
	}*/
	
	
}