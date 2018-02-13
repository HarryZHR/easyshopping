package edu.cslg.easyshopping.controller;

import edu.cslg.easyshopping.pojo.*;
import edu.cslg.easyshopping.service.*;
import edu.cslg.easyshopping.util.GoodsCountUtil;
import edu.cslg.easyshopping.util.UploadUtil;
import edu.cslg.easyshopping.util.ValidationCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class SellerController {
    @Resource
    private GoodsTypeService goodsTypeService;
    @Resource
    private SellerService sellerService;
    @Resource
    private StuffService stuffService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private GoodsImgService goodsImgService;
    @Resource
    private StandardService standardService;

    /**
     * 登录主页面
     * @return 跳转到店铺登录页面
     */
    @GetMapping(value = "/")
    public String seller_login(){
        return "seller_login";
    }

    /**
     * 商家注册
     * @return 商家注册的页面
     */
    @GetMapping(value = "/seller_register")
    public String seller_register(){
        return "seller_register";
    }

    /**
     * 发送验证码
     * @return 手机号码是否被注册
     */
    @ResponseBody
    @GetMapping(value = "/validate_seller_tel")
    public String send_validation(String tel,HttpSession session){
        Seller seller = sellerService.getSellerByTel(tel);
        if(seller != null){
            return "false";
        }else {
            String validationCode = ValidationCode.getRandom();
            System.out.println("卖家注册的验证码："+validationCode);
            session.setAttribute(tel,validationCode);
            /*try {
                SendSmsResponse response = ValidationCodeSend.sendSms(tel,validationCode ,"SMS_102315072");
                System.out.println("短信接口返回的数据----------------");
                System.out.println("Code=" + response.getCode());
                System.out.println("Message=" + response.getMessage());
                System.out.println("RequestId=" + response.getRequestId());
                System.out.println("BizId=" + response.getBizId());
            }catch (ClientException e){
                e.printStackTrace();
            }*/
            return "true";
        }
    }

    /**
     * 验证验证码
     * @return 返回验证结果
     */
    @ResponseBody
    @GetMapping(value = "/validate_code")
    public String validateCode(String tel, String validate,HttpSession session){
        if(validate.equals(session.getAttribute(tel))){
            session.setAttribute("newSellerTel",tel);
            return "true";
        }
        return "false";
    }

    /**
     * 店铺申请提示的跳转
     * @return 店铺申请
     */
    @GetMapping(value = "/seller_application")
    public String seller_application(){
        return "seller_application";
    }

    /**
     * 店铺申请下一步
     * @return 跳转店铺申请填写信息
     */
    @GetMapping(value = "/seller_input")
    public String seller_input(){
        return "seller_input";
    }

    /**
     * 提交店铺个人信息
     * @return 返回提交申请材料照片的页面
     */
    @PostMapping(value = "/seller_info_sub")
    public String seller_info_sub(Seller seller,HttpSession session){
        Object telObj = session.getAttribute("newSellerTel");
        seller.setTel((String) telObj);
        seller.setScore(100);
        seller.setSellerStatus(false);
        seller.setShopTime(new Date());
        sellerService.saveSeller(seller);
        return "seller_info_sub";
    }

    /**
     * 提交店铺的申请材料
     * @return 返回卖家登录界面
     */
    @PostMapping(value = "/seller_stuff")
    public String seller_stuff(Stuff stuff,HttpSession session){
        Object telObj = session.getAttribute("newSellerTel");
        Seller seller = sellerService.getSellerByTel((String) telObj);
        stuff.setStuffStatus(false);
        stuff.setReason(null);
        stuff.setSeller(seller);
        stuffService.saveStuff(stuff);
        return "seller_login";
    }

    /**
     * 验证密码
     * @param seller 卖家
     * @return 验证正确且状态正确返回"true",待审核返回"wait",否则"false"
     */
    @GetMapping(value = "/seller_check_pwd")
    @ResponseBody
    public String seller_check_pwd(Seller seller,HttpSession session){
        Seller newSeller = sellerService.getSellerByTelAndPwd(seller);
        if(newSeller != null){
            session.setAttribute("currSeller",newSeller);
            if(newSeller.isSellerStatus()){
                return "true";
            }else{
                return "wait";
            }
        }
        return "false";
    }

    /**
     * 店铺主界面
     * @return 登录之后的首页
     */
    @GetMapping(value = "/seller_home")
    public String seller_home(Model model) {
        model.addAttribute("seller_status", "home_page");
        return "index";
    }

    /**
     * 店铺的基础设置
     *
     * @return 店铺基础设置的页面
     */
    @GetMapping(value = "/seller_setting")
    public String setting(Model model,HttpSession session) {
        Seller currSeller = (Seller) session.getAttribute("currSeller");
        if(currSeller != null){
            // 获取当前数据库中最新的店铺信息
            Seller sellerNow = sellerService.getSellerById(currSeller.getId());
            session.setAttribute("sellerNow",sellerNow);
            model.addAttribute("seller_status", "setting");
            return "index";
        }
        return "seller_login";
    }

    @GetMapping(value = "/seller_update")
    @ResponseBody
    public String seller_update(Seller seller,HttpSession session){
        Seller currSeller = (Seller) session.getAttribute("currSeller");
        if(currSeller != null){
            // 更新店铺中的信息
            currSeller.setStoreName(seller.getStoreName());
            currSeller.setIcon(seller.getIcon());
            currSeller.setIntroduce(seller.getIntroduce());
            currSeller.setSellerProvince(seller.getSellerProvince());
            currSeller.setSellerCity(seller.getSellerCity());
            currSeller.setSellerDistrict(seller.getSellerDistrict());
            currSeller.setSellerStreet(seller.getSellerStreet());
            currSeller.setSupply(seller.getSupply());
            sellerService.updateSeller(currSeller);
            return "true";
        }
        return "seller_login";
    }

    /**
     * 发布商品前选择分类
     *
     * @return 店铺发布商品前的页面
     */
    @GetMapping(value = "/goods_category")
    public String goods_category(Model model, HttpSession session) {
        Seller currSeller = (Seller) session.getAttribute("currSeller");
        if(currSeller != null){
            List<GoodsType> goodsTypeList = goodsTypeService.listGoodsType();
            session.setAttribute("goodsTypeList", goodsTypeList);
            model.addAttribute("seller_status", "goods_category");
            model.addAttribute("operate", "goods_operate");
            return "index";
        }
        return "seller_login";
    }

    /**
     * 发布商品
     * @param model 参数
     * @param goods_type 上一步选择的商品分类
     * @param session 保存作用域
     * @return 上架商品
     */
    @PostMapping(value = "/goods_publish")
    public String goods_publish(Model model, String goods_type, HttpSession session) {
        Seller currSeller = (Seller) session.getAttribute("currSeller");
        if(currSeller != null){
            List<String> clothSizes = new ArrayList<>();
            clothSizes.add("S");
            clothSizes.add("M");
            clothSizes.add("X");
            clothSizes.add("L");
            clothSizes.add("XL");
            clothSizes.add("2XL");
            clothSizes.add("3XL");
            session.setAttribute("clothSizes", clothSizes);
            List<Integer> shoesSizes = new ArrayList<>();
            for (int i = 35; i < 46; i++) {
                shoesSizes.add(i);
            }
            session.setAttribute("shoesSizes", shoesSizes);
            List<Integer> childSizes = new ArrayList<>();
            for (int i = 80; i < 161; i += 10) {
                childSizes.add(i);
            }
            session.setAttribute("childSizes", childSizes);
            session.setAttribute("goods_type", goods_type);
            model.addAttribute("seller_status", "goods_publish");
            model.addAttribute("operate", "goods_operate");
            return "index";
        }
        return "seller_login";
    }

    /**
     * 保存商品
     * @return 主页面
     */
    @PostMapping(value = "/goods_save")
    public String goods_save(Goods goods,GoodsImg goodsImg,HttpSession session,Model model,String[] input_img_detail,String[] color,String[] colorImg,String[] size,Integer[] count,Float[] price){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            String type = (String)session.getAttribute("goods_type");
            GoodsType goodsType = goodsTypeService.getGoodsTypeByType(type);
            goods.setGoodsTime(new Date());
            goods.setType(goodsType);
            goods.setSeller(currSeller);
            goodsService.saveGoods(goods);
            Integer goodsId = goods.getId();
            goodsImg.setId(goodsId);
            StringBuilder detailImg = new StringBuilder();
            for (String detailImgArr : input_img_detail){
                detailImg.append(detailImgArr).append("_");
            }
            goodsImg.setDetailImg(detailImg.toString());
            goodsImgService.saveGoodsImg(goodsImg);
            Standard standard = new Standard();
            Float minPrice = Float.MAX_VALUE;
            for (int i = 0; i < color.length; i++){
                if(price[i]< minPrice){
                    minPrice = price[i];
                }
                standard.setGoods(goods);
                standard.setColor(color[i]);
                standard.setColorImg(colorImg[i]);
                standard.setSize(size[i]);
                standard.setCount(count[i]);
                standard.setPrice(price[i]);
                standardService.saveStandard(standard);
            }
            goods.setMinPrice(minPrice);
            goods.setStatus(true);
            goods.setSaleCount(0);
            goods.setDiscount(0f);
            goodsService.updateGoods(goods);
            model.addAttribute("seller_status", "home_page");
            return "index";
        }
        return "seller_login";
    }

    /**
     * 上传图片
     *
     * @return 图片保存的URL
     */
    @ResponseBody
    @PostMapping(value = "/up_img")
    public String up_img(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            return UploadUtil.upImg(file);
        }
        return null;
    }

    /**
     * 多图上传
     * @param files 上传的文件
     * @return 返回上传的路径
     */
    @ResponseBody
    @PostMapping(value = "/up_img_multi")
    public String up_img_multi(@RequestParam("files") MultipartFile[] files){
        if(files.length > 0){
            StringBuilder urlStr = new StringBuilder();
            for (MultipartFile file : files) {
                String url = UploadUtil.upImg(file);
                urlStr.append(url).append("_");
            }
            return urlStr.toString();
        }
        return null;
    }

    /**
     * 编辑商品
     *
     * @return 所有上架商品的页面
     */
    @GetMapping(value = "/goods_list_edit")
    public String goods_list_edit(Model model,HttpSession session,Integer currPage) {
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            listGoodsInSeller(currPage,currSeller,session);
            model.addAttribute("seller_status", "goods_list_edit");
            model.addAttribute("operate", "goods_operate");
            return "index";
        }
        return null;
    }

    /**
     * 分页获取店铺中的商品
     * @param currPage 当前页
     * @param currSeller 当前的卖家
     * @param session 保存作用域
     */
    private void listGoodsInSeller(Integer currPage,Seller currSeller,HttpSession session){
        Integer pageSize = 5;
        Integer goodsCount = goodsService.countGoodsBySeller(currSeller.getId());
        Integer pageCount = (goodsCount + pageSize - 1) / pageSize;
        if(currPage == null || currPage <= 0){
            currPage = 1;
        }
        if(currPage > pageCount){
            currPage = pageCount;
        }
        List<Goods> goodsList = GoodsCountUtil.setGoodsListCount(goodsService.listGoodsBySeller(currSeller.getId(), pageSize * (currPage - 1), pageSize));
        session.setAttribute("goodsList",goodsList);
        session.setAttribute("pageCount",pageCount);
        session.setAttribute("currPage",currPage);
    }

    @GetMapping(value = "goods_edit")
    public String goods_edit(Integer goodsId,HttpSession session,Model model){
        Goods goods = goodsService.getGoodsById(goodsId);
        GoodsCountUtil.setGoodsCount(goods);
        GoodsType goods_type = goods.getType();
        session.setAttribute("goods",goods);
        session.setAttribute("goods_type",goods_type);
        model.addAttribute("seller_status", "goods_edit");
        model.addAttribute("operate", "goods_operate");
        return "index";
    }

    /**
     * 商品店铺中的商品，修改商品的状态
     * @param goodsId 商品的id
     * @return 商品删除成功与否
     */
    @GetMapping(value = "seller_delete_goods")
    @ResponseBody
    public String seller_delete_goods(String goodsId){
        Goods goods = goodsService.getGoodsById(Integer.valueOf(goodsId));
        goods.setStatus(false);
        goodsService.updateGoods(goods);
        return "true";
    }
    /**
     * 设置商品优惠
     * @return 商品优惠设置的页面
     */
    @GetMapping(value = "/goods_discount")
    public String goods_discount(Model model,HttpSession session,Integer currPage) {
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            listGoodsInSeller(currPage,currSeller,session);
            model.addAttribute("seller_status", "goods_discount");
            model.addAttribute("operate", "goods_operate");
            return "index";
        }
        return null;
    }

    /**
     * 商品参加滚动图
     * @param model model
     * @return 参加滚动图活动的页面
     */
    @GetMapping(value = "/goods_activity")
    public String goods_activity(Model model,HttpSession session,Integer currPage) {
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            listGoodsInSeller(currPage,currSeller,session);
            model.addAttribute("seller_status", "goods_activity");
            model.addAttribute("operate", "goods_operate");
            return "index";
        }
        return null;
    }

    /**
     * 参与活动
     * @param activityName 参与活动的名称
     * @param goodsId 商品的id
     * @return 参与是否成功
     */
    @GetMapping(value = "seller_attend_activity")
    @ResponseBody
    public String seller_attend_activity(String activityName, Integer goodsId,HttpSession session){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Goods goods = goodsService.getGoodsById(goodsId);
            goods.setActivity(Integer.valueOf(activityName));
            goodsService.updateGoods(goods);
            return "true";
        }
        return null;
    }

    /**
     * 修改优惠
     * @param session 保存作用域
     * @param goodsId 商品的id
     * @param discount 折扣
     * @return 修改状态
     */
    @GetMapping(value = "seller_set_discount")
    @ResponseBody
    public String seller_set_discount(HttpSession session,Integer goodsId,Float discount){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Goods goods = goodsService.getGoodsById(goodsId);
            goods.setDiscount(discount);
            goodsService.updateGoods(goods);
            return "true";
        }
        return null;
    }

    /**
     * 卖家退出
     * @return 登录界面
     */
    @GetMapping(value = "seller_exit")
    public String seller_exit(HttpSession session){
        session.removeAttribute("currSeller");
        return "seller_login";
    }

    /**
     * 跳转登录界面
     * @return 登录界面
     */
    @GetMapping(value = "seller_login")
    public String sellerLogin(){
        return "seller_login";
    }
}
