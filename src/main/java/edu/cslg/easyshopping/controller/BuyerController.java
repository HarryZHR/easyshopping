package edu.cslg.easyshopping.controller;

import edu.cslg.easyshopping.pojo.*;
import edu.cslg.easyshopping.service.*;
import edu.cslg.easyshopping.util.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 控制买家的controller
 */
@Controller
public class BuyerController {

    private final static Integer PAGE_SIZE = 5;
    @Resource
    private GoodsTypeService goodsTypeService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private ReplyService replyService;
    @Resource
    private BuyerService buyerService;
    @Resource
    private BuyerDetailService buyerDetailService;
    @Resource
    private SellerService sellerService;
    @Resource
    private StandardService standardService;
    @Resource
    private CartItemService cartItemService;
    @Resource
    private AddressService addressService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private OrderStatusService orderStatusService;
    @Resource
    private ComplainService complainService;
    @Resource
    private BackGoodsInfoService backGoodsInfoService;
    /**
     * 时间格式化
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 跳转买家的首页
     * @return 首页
     */
    @GetMapping(value = "/buyer_index")
    public String buyerIndex(HttpSession session, String key,String type,Float low, Float high, String operate, Integer currPage,Integer sellerId){
        List<GoodsType> goodsTypes = goodsTypeService.listGoodsType();
        session.setAttribute("goodsTypeList",goodsTypes);
        GoodsType goodsType = goodsTypeService.getGoodsTypeByType(type);
        Integer goodsCount= goodsService.countGoodsByCategoryAndKey(goodsType, key, low, high,null,null,sellerId);
        Integer pageCount = (goodsCount + PAGE_SIZE - 1) / PAGE_SIZE;
        if(pageCount == 0){
            pageCount = 1;
        }
        if(currPage == null || currPage <= 0){
            currPage = 1;
        }
        if(currPage > pageCount){
            currPage = pageCount;
        }
        List<Goods> goodsList = GoodsCountUtil.setGoodsListCount(goodsService.listGoodsByCategoryAndKey(goodsType, key, low, high,operate, PAGE_SIZE * (currPage - 1),PAGE_SIZE,null,null,sellerId));
        session.setAttribute("buyerGoodsList",goodsList);
        session.setAttribute("buyerPageCount",pageCount);
        session.setAttribute("type",type);
        session.setAttribute("key",key);
        session.setAttribute("low",low);
        session.setAttribute("high",high);
        session.setAttribute("operate",operate);
        return "buyer_index";
    }

    /**
     * 按照不同的要求加载商品
     * @param key 搜索关键字
     * @param type 类型
     * @param low 最低价
     * @param high 最高价
     * @param operate 操作 从高到低 还是从低到高
     * @param currPage 当前页
     * @param carouselId 活动的id
     * @return ajax结果
     */
    @GetMapping(value = "/buyer_load_goods")
    @ResponseBody
    public String buyerLoadGoods(HttpSession session, String key,String type,Float low, Float high, String operate, Integer currPage,Integer carouselId,Integer bigType,Integer sellerId){
        GoodsType goodsType = goodsTypeService.getGoodsTypeByType(type);
        Integer goodsCount= goodsService.countGoodsByCategoryAndKey(goodsType, key, low, high, carouselId,bigType,sellerId);
        Integer pageCount = (goodsCount + PAGE_SIZE - 1) / PAGE_SIZE;
        if(pageCount == 0){
            pageCount = 1;
        }
        if(currPage == null || currPage <= 0){
            currPage = 1;
        }
        if(currPage > pageCount){
            currPage = pageCount;
        }
        List<Goods> goodsList = GoodsCountUtil.setGoodsListCount(goodsService.listGoodsByCategoryAndKey(goodsType, key, low, high,operate, PAGE_SIZE * (currPage - 1),PAGE_SIZE, carouselId,bigType,sellerId));
        session.setAttribute("buyerNewGoodsList",goodsList);
        session.setAttribute("type",type);
        session.setAttribute("key",key);
        session.setAttribute("low",low);
        session.setAttribute("high",high);
        session.setAttribute("operate",operate);
        if(currPage < pageCount){
            return "true";
        }else if(currPage.equals(pageCount)){
            return "false";
        }
        return null;
    }

    /**
     * 跳转加载更多的页面
     * @return 页面
     */
    @GetMapping(value = "buyer_load_more")
    public String buyer_load_more(){
        return "buyer_load_more";
    }

    /**
     * 搜索结果
     * @param session 保存作用域
     * @param key 关键字
     * @param type 类型
     * @param low 低价
     * @param high 高价
     * @param operate 操作 从高到低 还是从低到高
     * @param currPage 当前页
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_goods_category_key")
    public String buyerGoodsCategoryKey(HttpSession session, String key,String type,Float low, Float high, String operate, Integer currPage, Integer carouselId,Integer bigType,Integer sellerId){
        GoodsType goodsType = goodsTypeService.getGoodsTypeByType(type);
        Integer goodsCount= goodsService.countGoodsByCategoryAndKey(goodsType, key, low, high,carouselId,bigType,sellerId);
        Integer pageCount = (goodsCount + PAGE_SIZE - 1) / PAGE_SIZE;
        if(pageCount == 0){
            pageCount = 1;
        }
        if(currPage == null || currPage <= 0){
            currPage = 1;
        }
        if(currPage > pageCount){
            currPage = pageCount;
        }
        List<Goods> goodsList = GoodsCountUtil.setGoodsListCount(goodsService.listGoodsByCategoryAndKey(goodsType, key, low, high,operate, PAGE_SIZE * (currPage - 1),PAGE_SIZE,carouselId,bigType,sellerId));
        session.setAttribute("buyerGoodsList",goodsList);
        session.setAttribute("buyerPageCount",pageCount);
        session.setAttribute("type",type);
        session.setAttribute("key",key);
        session.setAttribute("low",low);
        session.setAttribute("high",high);
        session.setAttribute("operate",operate);
        return "success";
    }

    /**
     * 跳转到搜索结果页面
     * @return 搜索结果页面
     */
    @GetMapping(value = "buyer_goods_type_key")
    public String buyerGoodsTypeKey(){
        return "buyer_goods_type_key";
    }

    /**
     * 跳转购买商品的页面
     * @return 商品详情的页面
     */
    @GetMapping(value = "buyer_goods_detail")
    public String buyerGoodsDetail(Integer goodsId,HttpSession session){
        session.setAttribute("lastViewGoods",goodsId);
        Goods goods = GoodsCountUtil.setGoodsCount(goodsService.getGoodsById(goodsId),"");
        // 商品评论数
        Integer countImgReply = replyService.countImgReplyByStandardId(goods.getId());
        Integer countGoodReply = replyService.countAllReplyByStandardId(goods.getId(),"goodReply");
        Integer countMultiReply = replyService.countAllReplyByStandardId(goods.getId(),"multiReply");
        Integer countBadReply = replyService.countAllReplyByStandardId(goods.getId(),"badReply");
        List<Reply> listAllReplyFirst = ReplyImgsUtil.setReplyImgs(replyService.listAllReplyByStandardId(goodsId,null,0,5));
        Integer replyAllCount = replyService.countAllReplyByStandardId(goodsId,null);
        session.setAttribute("replyAllCount",replyAllCount);
        // 获取同类型的商品
        List<Goods> listGoodsInCategory = goodsService.listGoodsByCategoryInSeller(goods.getSeller().getId(),goods.getType().getId());
        // 进入商品的所在店铺所有的商品类型
        List<Goods> goodsList = goodsService.listGoodsBySellerAll(goods.getSeller().getId());
        List<String> goodsTypes = new ArrayList<>();
        for (Goods goodsInSeller: goodsList) {
            GoodsType goodsType = goodsInSeller.getType();
            if(!goodsTypes.contains(goodsType.getType())){
                goodsTypes.add(goodsType.getType());
            }
        }
        // 商品的收藏人数
        Integer goodsLikeCount = goodsService.countBuyerLikeGoods(goodsId);
        session.setAttribute("goodsLikeCount",goodsLikeCount);
        session.setAttribute("goods_types_in_seller",goodsTypes);
        session.setAttribute("this_goods",goods);
        session.setAttribute("countImgReply",countImgReply);
        session.setAttribute("countGoodReply",countGoodReply);
        session.setAttribute("countMultiReply",countMultiReply);
        session.setAttribute("countBadReply",countBadReply);
        session.setAttribute("countAllReply",countGoodReply + countMultiReply + countBadReply);
        session.setAttribute("list_goods_category",listGoodsInCategory);
        session.setAttribute("listReply",listAllReplyFirst);
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null){
            // 当前买家是否收藏店铺
            Integer buyerCount = buyerService.buyerLikeSellerOr(currBuyer.getId(),goods.getSeller().getId());
            if(buyerCount > 0){
                session.setAttribute("buyerLikeSeller","like");
            }else {
                session.setAttribute("buyerLikeSeller","dislike");
            }
            // 当前买家是否收藏商品
            Integer goodsCount = buyerService.buyerLikeGoodsOr(currBuyer.getId(),goodsId);
            if(goodsCount > 0){
                session.setAttribute("buyerLikeGoods","like");
            }else {
                session.setAttribute("buyerLikeGoods","dislike");
            }

        }
        return "buyer_goods_detail";
    }

    /**
     * 加载更多评价
      * @param currReplyPage 当前页
     * @param replyType 评价类型
     * @param goodsId 商品的id
     * @return 返回ajax
     */
    @ResponseBody
    @GetMapping(value = "buyer_list_reply")
    public String buyerListReply(Integer currReplyPage, String replyType, HttpSession session, Integer goodsId){
        if("".equals(replyType) || replyType == null){
            replyType = null;
        }
        Integer replyCount;
        if("imgReply".equals(replyType)){
            replyCount = replyService.countImgReplyByStandardId(goodsId);
        }else {
           replyCount = replyService.countAllReplyByStandardId(goodsId,replyType);
        }
        Integer pageCount = (replyCount + PAGE_SIZE - 1) / PAGE_SIZE;
        if(pageCount == 0){
            pageCount = 1;
        }
        if(currReplyPage == null || currReplyPage <= 0){
            currReplyPage = 1;
        }
        if(currReplyPage > pageCount){
            currReplyPage = pageCount;
        }
        List<Reply> replies;
        if("imgReply".equals(replyType)){
            replies = ReplyImgsUtil.setReplyImgs(replyService.listImgReplyByStandardId(goodsId,PAGE_SIZE * (currReplyPage - 1),PAGE_SIZE));
        }else {
            replies = ReplyImgsUtil.setReplyImgs(replyService.listAllReplyByStandardId(goodsId,replyType,PAGE_SIZE * (currReplyPage - 1),PAGE_SIZE));
        }
        session.setAttribute("listReply",replies);
        session.setAttribute("replyAllCount",replyCount);
        if(currReplyPage < pageCount){
            return "true";
        }else if(currReplyPage.equals(pageCount)){
            return "false";
        }
        return null;
    }

    /**
     * 访问商品的评论
     * @return 评论的代码块
     */
    @GetMapping(value = "buyer_goods_reply")
    public String buyerGoodsReply(){
        return "buyer_goods_reply";
    }

    /**
     * 跳转买家登录
     * @return 买家登录界面
     */
    @GetMapping(value = "buyer_login")
    public String buyerLogin(){
        return "buyer_login";
    }

    /**
     * 得到不同规格的库存
     * @param goodsId 商品id
     * @param color 颜色
     * @param size 尺寸
     * @return 库存
     */
    @ResponseBody
    @GetMapping(value = "get_standard_count")
    public String getStandardCount(Integer goodsId, String color, String size){
        Standard standard = standardService.getStandardByIdAndSizeAndColor(goodsId, size, color);
        Integer count = standard.getCount();
        return count.toString();
    }

    /**
     * 通过商品id和颜色判断哪些尺寸没有库存
     * @param goodsId 商品的id
     * @param color 颜色
     * @return 没有库存的尺寸的字符串
     */
    @ResponseBody
    @GetMapping(value = "check_standard_count_by_color")
    public String checkStandardCountByColor(Integer goodsId, String color){
        List<Standard> standards = standardService.listStandardByGoodsIdAndColor(goodsId,color);
        StringBuilder size = new StringBuilder();
        for(Standard standard : standards){
            Integer count = standard.getCount();
            if(count <= 1){
                size.append(standard.getSize()).append("_");
            }
        }
        return size.toString();
    }

    /**
     * 通过商品id和尺寸确定哪些颜色没有库存
     * @param goodsId 商品id
     * @param size 尺寸
     * @return 没有库存的颜色的字符串
     */
    @ResponseBody
    @GetMapping(value = "check_standard_count_by_size")
    public String checkStandardCountBySize(Integer goodsId, String size){
        List<Standard> standards = standardService.listStandardByGoodsIdAndSize(goodsId, size);
        StringBuilder color = new StringBuilder();
        for (Standard standard : standards){
            Integer count = standard.getCount();
            if(count <= 1){
                color.append(standard.getColor()).append("_");
            }
        }
        return color.toString();
    }

    /**
     * 跳转到店铺
     * @return 店铺
     */
    @GetMapping(value = "buyer_seller_goods_list")
    public String buyerSellerGoodsList(){
        return "buyer_seller_goods_list";
    }

    /* 下面是需要用户登录的模块 */

    /**
     * 跳转买家注册页面
     * @return 买家注册页面
     */
    @GetMapping(value = "buyer_register")
    public String buyerRegister(){
       return "buyer_register";
    }

    /**
     * 验证手机号码是否注册，注册报错，否则发送短信
     * @param tel 手机号码
     * @param session 保存作用域
     * @return 注册与否
     */
    @GetMapping(value = "validate_buyer_tel")
    @ResponseBody
    public String validateBuyerTel(String tel,HttpSession session){
        Buyer buyer = buyerService.getBuyerByTel(tel);
        if(buyer != null){
            return "false";
        }else {
            String validationCode = ValidationCode.getRandom();
            System.out.println("买家注册的验证码："+validationCode);
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
     * @param tel 手机号码
     * @param validate 验证码
     * @param session 保存作用域
     * @return 正确与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_validate_code")
    public String buyerValidateCode(String tel, String validate,HttpSession session){
        if(validate.equals(session.getAttribute(tel))){
            session.setAttribute("newBuyerTel",tel);
            return "true";
        }
        return "false";
    }

    /**
     * 买家注册设置密码
     * @return 设置密码的页面
     */
    @GetMapping(value = "buyer_set_pwd")
    public String buyerSetPwd(){
        return "buyer_set_pwd";
    }

    /**
     * 判断买家是否已经注册成功
     * @param session 保存作用域
     * @return 是否已经注册
     */
    @ResponseBody
    @GetMapping(value = "check_buyer_set_pwd")
    public String checkBuyerSetPwd(HttpSession session){
        Buyer buyer = buyerService.getBuyerByTel((String) session.getAttribute("newBuyerTel"));
        if(buyer != null){
            return "false";
        }else {
            return "true";
        }
    }

    /**
     * 注册买家
     * @param buyer 买家的参数
     * @param session 保存作用域
     * @return 登陆页面
     */
    @PostMapping(value = "save_buyer")
    public String saveBuyer(Buyer buyer,HttpSession session){
        Buyer newBuyer = buyerService.getBuyerByTel((String) session.getAttribute("newBuyerTel"));
        if(newBuyer == null){
            buyer.setNickName("user" + buyer.getTel());
            buyer.setHeadImg("img/buyer_default.jpg");
            buyer.setBuyerStatus(true);
            buyer.setRegisterDate(new Date());
            buyerService.saveBuyer(buyer);
            Integer buyerId = buyer.getId();
            BuyerDetail buyerDetail = new BuyerDetail();
            buyerDetail.setId(buyerId);
            buyerDetail.setBirthday(new Date());
            buyerDetailService.saveBuyerDetail(buyerDetail);
        }
        return "buyer_login";
    }

    /**
     * 验证密码是否正确
     * @param loginId 输入的用户名或者手机号码
     * @param buyer 获取密码
     * @param session 保存作用域
     * @return 验证结果
     */
    @GetMapping(value = "buyer_check_pwd")
    @ResponseBody
    public String buyerCheckPwd(String loginId, Buyer buyer, HttpSession session){
        if(NumberUtil.isNumeric(loginId)){
            buyer.setTel(loginId);
        }else {
            buyer.setNickName(loginId);
        }
        Buyer currBuyer = buyerService.getBuyerByLoginIdAndPwd(buyer);
        Integer lastViewGoodsId = (Integer) session.getAttribute("lastViewGoods");
        if (currBuyer != null){
            session.setAttribute("currBuyer",currBuyer);
            if(lastViewGoodsId != null){
                return lastViewGoodsId.toString();
            }else {
                return "true";
            }
        }else {
            return "false";
        }
    }

    /**
     * 买家收藏卖家
     * @param sellerId 卖家id
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_like_seller")
    public String buyerLikeSeller(Integer sellerId,HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            buyerService.buyerLikeSeller(currBuyer.getId(),sellerId);
            return "success";
        }
        return "fail";
    }

    /**
     * 买家取消收藏卖家
     * @param sellerId 卖家id
     * @return 成功与否
     */
    @GetMapping(value = "buyer_dislike_seller")
    @ResponseBody
    public String buyerDislikeSeller(Integer sellerId,HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            buyerService.buyerDislikeSeller(currBuyer.getId(),sellerId);
            return "success";
        }
        return "fail";
    }

    /**
     * 买家收藏商品
     * @param goodsId 商品的id
     * @return 成功与否
     */
    @GetMapping(value = "buyer_like_goods")
    @ResponseBody
    public String buyerLikeGoods(Integer goodsId,HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            buyerService.buyerLikeGoods(currBuyer.getId(),goodsId);
            return "success";
        }
        return "fail";
    }

    /**
     * 买家取消收藏商品
     * @param goodsId 商品id
     * @return 成功与否
     */
    @GetMapping(value = "buyer_dislike_goods")
    @ResponseBody
    public String buyerDislikeGoods(Integer goodsId,HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            buyerService.buyerDislikeGoods(currBuyer.getId(),goodsId);
            return "success";
        }
        return "fail";
    }

    /**
     * 买家退出
     * @return 登录界面
     */
    @GetMapping(value="buyer_exit")
    public String buyerExit(HttpSession session){
        session.removeAttribute("currBuyer");
        session.removeAttribute("lastViewGoods");
        return "buyer_login";
    }

    /**
     * 添加购物车
     * @param color 颜色
     * @param size 尺寸
     * @param goodsId 商品id
     * @param buyCount 购买数量
     * @param session 保存作用域
     * @return 返回成功与否
     */
    @ResponseBody
    @GetMapping(value="add_cart")
    public String addCart(String color, String size, Integer goodsId, Integer buyCount,HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            Standard standard = standardService.getStandardByIdAndSizeAndColor(goodsId, size, color);
            CartItem cartItem = cartItemService.getCartItemByStandardIdAndBuyerId(currBuyer.getId(),standard.getId());
            if(cartItem != null){
                cartItem.setBuyCount(cartItem.getBuyCount() + buyCount);
                cartItemService.updateCartItem(cartItem);
            }else {
                cartItem = new CartItem(buyCount, standard, currBuyer);
                cartItemService.saveCartItem(cartItem);
            }
            return "success";
        }
        return "fail";
    }

    /**
     * 进入购物车
     * @return 购物车页面
     */
    @GetMapping(value = "buyer_cart")
    public String buyerCart(HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            List<CartItem> cartItems = cartItemService.listCartItemByBuyerId(currBuyer.getId());
            session.setAttribute("cartItems",cartItems);
            Map<Seller,List<CartItem>> cartItemsInSeller = new HashMap<>();
            for (CartItem cartItem : cartItems){
                Seller seller = cartItem.getStandard().getGoods().getSeller();
                List<CartItem> cartItemList = cartItemsInSeller.get(seller);
                if(null == cartItemList){
                    cartItemList = new ArrayList<>();
                    cartItemList.add(cartItem);
                }else {
                    cartItemList.add(cartItem);
                }
                cartItemsInSeller.put(seller,cartItemList);
            }
            session.setAttribute("cartItemInSeller",cartItemsInSeller);
            return "buyer_cart";
        }
        return "buyer_login";
    }

    /**
     * 更新购物车的购买数量
     * @param cartId 购物车的id
     * @param num 更新后的数量
     * @return 返回更新成功
     */
    @ResponseBody
    @GetMapping(value = "update_cart_num")
    public String updateCartNum(Integer cartId, Integer num){
        CartItem cartItem = cartItemService.getCartItemById(cartId);
        cartItem.setBuyCount(num);
        cartItemService.updateCartItem(cartItem);
        return "success";
    }

    /**
     * 删除一条购物车详情
     * @param session 保存作用域
     * @param cartId 购物车的id
     * @return 返回当前购物车是否还有0条记录
     */
    @ResponseBody
    @GetMapping(value = "delete_cart_item")
    public String deleteCartItem(HttpSession session,Integer cartId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        cartItemService.deleteCartItem(cartId);
        List<CartItem> cartItems = cartItemService.listCartItemByBuyerId(currBuyer.getId());
        if (cartItems.size() == 0){
            return "zero";
        }
        return "success";
    }

    /**
     * 删除多条购物车详情
     * @param cartItemIdStr 详情的id的字符串
     * @return 返回当前购物车是否还有0条记录
     */
    @ResponseBody
    @GetMapping(value = "delete_select_cart_item")
    public String deleteSelectCartItem(String cartItemIdStr,HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        String[] cartItemIdArr = cartItemIdStr.split("_");
        for (String cartItemId : cartItemIdArr){
            if(cartItemId != null && !"".equals(cartItemId)){
                cartItemService.deleteCartItem(Integer.valueOf(cartItemId));
            }
        }
        List<CartItem> cartItems = cartItemService.listCartItemByBuyerId(currBuyer.getId());
        if (cartItems.size() == 0){
            return "zero";
        }
        return "success";
    }

    /**
     * 跳转提交订单的页面
     * @return 提交订单的页面
     */
    @GetMapping(value = "buyer_submit_order")
    public String buyerSubmitOrder(HttpSession session,String cartItemIdStr){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            // 获取用户的收货地址
            List<Address> addresses = addressService.listAddressByBuyerId(currBuyer.getId());
            session.setAttribute("addresses",addresses);
            Address addressDefault = addressService.getDefaultAddress();
            session.setAttribute("addressDefault",addressDefault);
            List<Address> addressNonDefault = addressService.listNonDefaultAddress();
            session.setAttribute("addressNonDefault",addressNonDefault);

            // 解析购物车中选择的商品
            session.setAttribute("cartItemIdStr",cartItemIdStr);
            String[] cartItemIdArr = cartItemIdStr.split("_");
            Map<Seller,List<CartItem>> cartItemsInSeller = new HashMap<>();
            Float totalMoney = 0.0f;
            for(String cartItemId : cartItemIdArr){
                if(cartItemId != null && !"".equals(cartItemId)){
                    CartItem cartItem = cartItemService.getCartItemById(Integer.parseInt(cartItemId));
                    Seller seller = cartItem.getStandard().getGoods().getSeller();
                    List<CartItem> cartItemList = cartItemsInSeller.get(seller);
                    Standard standard = cartItem.getStandard();
                    totalMoney += cartItem.getBuyCount() * (standard.getPrice() - standard.getGoods().getDiscount());
                    if(null == cartItemList){
                        cartItemList = new ArrayList<>();
                        cartItemList.add(cartItem);
                    }else {
                        cartItemList.add(cartItem);
                    }
                    cartItemsInSeller.put(seller,cartItemList);
                }
            }
            session.setAttribute("cartItemSelectInSeller",cartItemsInSeller);
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            String totalMoneyStr = decimalFormat.format(totalMoney);
            session.setAttribute("totalMoney",totalMoneyStr);
            return "buyer_submit_order :: buyer_submit_order";
        }
        return "buyer_login";
    }

    /**
     * 保存地址
     * @param address 地址的参数
     * @param session 保存作用域
     * @return 保存成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_save_address")
    public String buyerSaveAddress(Address address,HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            if(address.getId() == null){
                List<Address> addresses = addressService.listAddressByBuyerId(currBuyer.getId());
                address.setBuyer(currBuyer);
                address.setDelFlag(true);
                if(addresses.size() != 0){
                    address.setDefaultAddress(false);
                }else {
                    address.setDefaultAddress(true);
                }
                addressService.saveAddress(address);
            }else {
                Address address1 = addressService.getAddressById(address.getId());
                address.setDelFlag(address1.getDelFlag());
                address.setDefaultAddress(address1.getDefaultAddress());
                addressService.updateAddress(address);
            }
            return "success";
        }
        return "fail";
    }

    /**
     * 生成订单
     * @param addressId 地址id
     * @param orderAll 所有的订单的信息
     * @return 生成与否
     */
    @GetMapping(value = "buyer_save_order")
    public String buyerSaveOrder(HttpSession session,Integer addressId,String orderAll){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null) {
            String cartItemIdStr = (String) session.getAttribute("cartItemIdStr");
            // 保存订单
            Address address = addressService.getAddressById(addressId);
            String[] orderInfoArr = orderAll.split("_");
            List<Order> orders = new ArrayList<>();
            for (String orderInfo : orderInfoArr){
                Order order = new Order();
                Seller seller = sellerService.getSellerById(Integer.parseInt(orderInfo.split(":")[0]));
                String remark = orderInfo.split(":")[1];
                order.setSeller(seller);
                order.setRemark(remark);
                order.setOrderTime(new Date());
                String orderNum = String.valueOf(System.currentTimeMillis()) + String.format("%03d", currBuyer.getId() % 1000);
                order.setOrderNum(orderNum);
                order.setDelFlag(true);
                order.setAddress(address);
                order.setBuyer(currBuyer);
                order.setOrderStatus(orderStatusService.getOrderStatusById(1));
                orderService.saveOrder(order);
                order.setId(order.getId());
                orders.add(order);
            }
            // 购物车中购买付款的流程 保存订单详情
            StringBuilder orderIdStr = new StringBuilder();
            if(cartItemIdStr != null){
                String[] cartItemIdArr = cartItemIdStr.split("_");
                for(String cartItemId : cartItemIdArr){
                    OrderItem orderItem = new OrderItem();
                    CartItem cartItem = cartItemService.getCartItemById(Integer.parseInt(cartItemId));
                    orderItem.setBuyCount(cartItem.getBuyCount());
                    orderItem.setStandard(cartItem.getStandard());
                    for (Order order : orders){
                        orderIdStr.append(order.getId()).append("_");
                        if(order.getSeller().getId().equals(cartItem.getStandard().getGoods().getSeller().getId())){
                            orderItem.setOrder(order);
                        }
                    }
                    orderItemService.saveOrderItem(orderItem);
                    Goods goods = orderItem.getStandard().getGoods();
                    goods.setSaleCount(goods.getSaleCount() + 1);
                    goodsService.updateGoods(goods);
                    Seller seller = goods.getSeller();
                    seller.setSale(seller.getSale() + orderItem.getBuyCount());
                    sellerService.updateSeller(seller);
                    Standard standard = orderItem.getStandard();
                    standard.setCount(orderItem.getStandard().getCount() - cartItem.getBuyCount());
                    standardService.updateStandard(standard);
                    cartItemService.deleteCartItem(Integer.parseInt(cartItemId));
                }
                // 直接购买付款的流程 保存订单详情
            }else {
                CartItem cartItemStraight = (CartItem) session.getAttribute("cartItemStraight");
                OrderItem orderItem = new OrderItem();
                orderItem.setBuyCount(cartItemStraight.getBuyCount());
                orderItem.setStandard(cartItemStraight.getStandard());
                for (Order order : orders){
                    orderItem.setOrder(order);
                    orderIdStr.append(order.getId()).append("_");
                }
                orderItemService.saveOrderItem(orderItem);
                Goods goods = orderItem.getStandard().getGoods();
                goods.setSaleCount(goods.getSaleCount() + 1);
                goodsService.updateGoods(goods);
                Seller seller = goods.getSeller();
                seller.setSale(seller.getSale() + orderItem.getBuyCount());
                sellerService.updateSeller(seller);
                Standard standard = orderItem.getStandard();
                standard.setCount(orderItem.getStandard().getCount() - cartItemStraight.getBuyCount());
                standardService.updateStandard(standard);
            }
            session.setAttribute("orders",orders);
            session.setAttribute("orderIdStr",orderIdStr);
            return "buyer_pay_order :: buyer_pay_order";
        }
        return "buyer_login";
    }

    /**
     * 直接提交商品购买
     * @param num 购买商品的数量
     * @param goods_color 商品的颜色
     * @param goods_size 商品的尺码
     * @param goods_id 商品的id
     * @return 确认订单的页面
     */
    @PostMapping(value = "buyer_straight_submit_order")
    public String buyerStraightSubmitOrder(HttpSession session,Integer num,String goods_color,String goods_size,Integer goods_id){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            // 获取用户的收货地址
            List<Address> addresses = addressService.listAddressByBuyerId(currBuyer.getId());
            session.setAttribute("addresses",addresses);
            Address addressDefault = addressService.getDefaultAddress();
            session.setAttribute("addressDefault",addressDefault);
            List<Address> addressNonDefault = addressService.listNonDefaultAddress();
            session.setAttribute("addressNonDefault",addressNonDefault);
            // 提交的商品
            Standard standard = standardService.getStandardByIdAndSizeAndColor(goods_id,goods_size,goods_color);
            CartItem cartItem = new CartItem();
            cartItem.setStandard(standard);
            cartItem.setBuyCount(num);
            cartItem.setBuyer(currBuyer);
            Seller seller = standard.getGoods().getSeller();
            Map<Seller,List<CartItem>> cartItemSelectInSeller = new HashMap<>();
            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(cartItem);
            cartItemSelectInSeller.put(seller,cartItems);
            session.setAttribute("cartItemStraight",cartItem);
            session.setAttribute("cartItemSelectInSeller",cartItemSelectInSeller);
            //构造方法的字符格式这里如果小数不足2位,会以0补足.
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            Float totalMoney = num * (standard.getPrice() - standard.getGoods().getDiscount());
            String totalMoneyStr = decimalFormat.format(totalMoney);
            session.setAttribute("totalMoney",totalMoneyStr);
            return "buyer_submit_order";
        }
        return "buyer_login";
    }

    /**
     * 付款
     * @param session 保存作用域
     * @param orderIdStr 订单id
     * @return 付款的页面
     */
    @GetMapping(value = "buyer_pay_money")
    public String buyerPayMoney(HttpSession session, String orderIdStr){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            String[] orderIdArr = orderIdStr.split("_");
            for(String orderId : orderIdArr){
                Order order = orderService.getOrderById(Integer.parseInt(orderId));
                order.setOrderStatus(orderStatusService.getOrderStatusById(2));
                orderService.updateOrderStatus(order);
            }
            return "buyer_pay_success :: buyer_pay_success";
        }
        return "buyer_login";
    }

    /**
     * 获取所有的订单
     * @param orderCurrPage 当前页
     * @param session 保存作用域
     * @return 所有订单的页面
     */
    @GetMapping(value = "buyer_order_all")
    public String buyerOrderAll(Integer orderCurrPage, HttpSession session,Integer small, Integer big){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            Integer goodsCount= orderService.countOrderByMoreStatusAndBuyer(small,big,currBuyer.getId());
            Integer pageCount = (goodsCount + PAGE_SIZE - 1) / PAGE_SIZE;
            if(pageCount == 0){
                pageCount = 1;
            }
            if(orderCurrPage == null || orderCurrPage <= 0){
                orderCurrPage = 1;
            }
            if(orderCurrPage > pageCount){
                orderCurrPage = pageCount;
            }
            List<Order> orders = orderService.listOrderByMoreStatusAndBuyer(small,big,currBuyer.getId(),PAGE_SIZE * (orderCurrPage - 1), PAGE_SIZE);
            for (Order order : orders){
                Float orderMoney = 0.0f;
                List<OrderItem> orderItems = order.getOrderItems();
                for (OrderItem orderItem : orderItems){
                    orderMoney += (orderItem.getStandard().getPrice() - orderItem.getStandard().getGoods().getDiscount()) * orderItem.getBuyCount();
                }
                order.setOrderMoney(orderMoney);
            }
            session.setAttribute("orders",orders);
            session.setAttribute("orderCurrPage",orderCurrPage);
            session.setAttribute("orderPageCount",pageCount);
            session.setAttribute("small",small);
            session.setAttribute("big",big);
            session.setAttribute("operate","buyer_order_all");
            return "buyer_info";
        }
        return "buyer_login";
    }

    /**
     * 删除订单
     * @param session 保存作用域
     * @param orderId 订单id
     * @return 是否删除成功
     */
    @ResponseBody
    @GetMapping(value = "del_order")
    public String delOrder(HttpSession session,Integer orderId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            orderService.deleteOrder(orderId);
            return "success";
        }
        return "buyer_login";
    }

    /**
     * 查看物流
     * @return 物流信息页面
     */
    @GetMapping(value = "buyer_post_condition")
    public String buyerPostCondition(HttpSession session, Integer orderId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            Order order = orderService.getOrderById(orderId);
            session.setAttribute("currOrder",order);
            session.setAttribute("operate","buyer_post_condition");
            return "buyer_info";
        }
        return "buyer_login";
    }

    /**
     * 获取订单信息
     * @param session 保存作用域
     * @param orderId 订单id
     * @return 订单页面
     */
    @GetMapping(value = "buyer_order_info")
    public String buyerOrderInfo(HttpSession session,Integer orderId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            session.setAttribute("currOrder",OrderMoneyUtil.setOrderMoney(orderService.getOrderById(orderId)));
            session.setAttribute("operate","buyer_order_info");
            return "buyer_info";
        }
        return "buyer_login";
    }

    /**
     * 跳转到支付页面
     * @param orderId 订单的id
     * @return 支付页面
     */
    @GetMapping(value = "buyer_pay_now")
    public String buyerPayNow(HttpSession session, Integer orderId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            Order order = orderService.getOrderById(orderId);
            Float totalMoney = 0.0f;
            for (OrderItem orderItem : order.getOrderItems()){
                totalMoney += (orderItem.getStandard().getPrice() - orderItem.getStandard().getGoods().getDiscount()) * orderItem.getBuyCount();
            }
            //构造方法的字符格式这里如果小数不足2位,会以0补足.
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            String totalMoneyStr = decimalFormat.format(totalMoney);
            session.setAttribute("totalMoney",totalMoneyStr);
            session.setAttribute("orderIdStr",order.getId()+"_");
            return "buyer_pay_order :: buyer_pay_order";
        }
        return "buyer_login";
    }

    /**
     * 确认收货
     * @param orderId 订单的id
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_submit_receive")
    public String buyerSubmitReceive(HttpSession session,Integer orderId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            Order order = orderService.getOrderById(orderId);
            order.setOrderStatus(orderStatusService.getOrderStatusById(4));
            orderService.updateOrderStatus(order);
            return "success";
        }
        return "buyer_login";
    }

    /**
     * 检验昵称是否合法
     * @param session 保存作用域
     * @param nickName 昵称
     * @param buyerId 买家id
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "getBuyerByNickName")
    public String getBuyerByNickName(HttpSession session,String nickName, Integer buyerId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            Buyer buyer = buyerService.getBuyerByNickName(nickName);
            if (buyer != null && !buyer.getId().equals(buyerId)){
                return "false";
            }
            return "true";
        }
        return "buyer_login";
    }

    /**
     * 跳转到用户信息的页面
     * @param session 保存作用域
     * @return 用户个人信息的页面
     */
    @GetMapping(value = "buyer_user_info")
    public String buyerUserInfo(HttpSession session){
        session.setAttribute("operate","buyer_user_info");
        return "buyer_info";
    }

    /**
     * 更新个人信息
     * @param session 保存作用域
     * @param buyerDetail 用户详情的参数
     * @param nickName 昵称
     * @return 保存成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_update_info")
    public String buyerUpdateInfo(HttpSession session,BuyerDetail buyerDetail,String nickName){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            currBuyer.setNickName(nickName);
            BuyerDetail buyerDetail1 = currBuyer.getBuyerDetail();
            if(!"".equals(buyerDetail.getRealName())){
                buyerDetail1.setRealName(buyerDetail.getRealName());
            }
            if(!"".equals(buyerDetail.getGender())){
                buyerDetail1.setGender(buyerDetail.getGender());
            }
            if(!"".equals(buyerDetail.getLocation())){
                buyerDetail1.setLocation(buyerDetail.getLocation());
            }
            if(null != (buyerDetail.getBirthday()) && buyerDetail.getBirthday().toString().length() > 0){
                buyerDetail1.setBirthday(buyerDetail.getBirthday());
            }
            if(!"".equals(buyerDetail.getRemark())){
                buyerDetail1.setRemark(buyerDetail.getRemark());
            }
            buyerDetailService.updateBuyerDetail(buyerDetail1);
            buyerService.updateBuyer(currBuyer);
            session.setAttribute("currBuyer",currBuyer);
            return "success";
        }
        return "buyer_login";
    }


    /**
     *跳转到用户头像的页面
     * @param session 保存作用域
     * @return 用户头像的页面
     */
    @GetMapping(value = "buyer_user_head")
    public String buyerUserHead(HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            session.setAttribute("operate","buyer_user_head");
            return "buyer_info";
        }
        return "buyer_login";
    }

    /**
     * 更新用户头像
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_update_head_img")
    public String buyerUpdateHeadImg(HttpSession session,String headImg){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            if(headImg != null){
                currBuyer.setHeadImg(headImg);
                buyerService.updateBuyer(currBuyer);
                session.setAttribute("currBuyer",currBuyer);
                return "success";
            }
            return "null";
        }
        return "fail";
    }

    /**
     * 跳转更新密码的页面
     * @return 更新密码的页面
     */
    @GetMapping(value = "buyer_user_pwd")
    public String buyerUserPwd(HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            session.setAttribute("operate","buyer_user_pwd");
            return "buyer_info";
        }
        return "buyer_login";
    }

    /**
     * 确认密码是否正确
     * @param pwd 输入的密码
     * @return 正确与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_update_check_pwd")
    public String buyerUpdateCheckPwd(HttpSession session, String pwd){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            if(pwd.equals(currBuyer.getPwd())){
                return "true";
            }
            return "false";
        }
        return "buyer_login";
    }

    /**
     * 更新密码
     * @param pwd 新密码
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_update_pwd")
    public String buyerUpdatePwd(HttpSession session, String pwd) {
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            currBuyer.setPwd(pwd);
            buyerService.updateBuyer(currBuyer);
            return "success";
        }
        return "buyer_login";
    }

    /**
     * 买家收货地址
     * @return 买家收货地址的页面
     */
    @GetMapping(value = "buyer_user_address")
    public String buyerUserAddress(HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            Buyer buyer = buyerService.getBuyerById(currBuyer.getId());
            session.setAttribute("currBuyer",buyer);
            session.setAttribute("operate","buyer_user_address");
            return "buyer_info";
        }
        return "buyer_login";
    }

    /**
     * 设置默认地址
     * @return 设置成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_set_address_default")
    public String buyerSetAddressDefault(HttpSession session,Integer addressId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            List<Address> addresses = currBuyer.getAddresses();
            for (Address address : addresses){
                address.setDefaultAddress(false);
                addressService.updateAddress(address);
            }
            Address address = addressService.getAddressById(addressId);
            address.setDefaultAddress(true);
            addressService.updateAddress(address);
            Buyer buyer = buyerService.getBuyerById(currBuyer.getId());
            session.setAttribute("currBuyer",buyer);
            return "success";
        }
        return "buyer_login";
    }

    /**
     * 删除地址
     * @param session 保存作用域
     * @param addressId 地址id
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_delete_address")
    public String buyerDeleteAddress(HttpSession session,Integer addressId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            Address address = addressService.getAddressById(addressId);
            address.setDelFlag(false);
            addressService.updateAddress(address);
            return "success";
        }
        return "buyer_login";
    }

    @GetMapping(value = "buyer_order_reply")
    public String buyerOrderReply(HttpSession session, Integer orderId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            session.setAttribute("operate","buyer_order_reply");
            session.setAttribute("currOrder", orderService.getOrderById(orderId));
            return "buyer_info";
        }
        return "buyer_login";
    }

    /**
     * 保存订单的评价
     * @param replies 评价的集合
     * @return 跳转成功的页面
     */
    @PostMapping(value = "buyer_save_order_reply")
    public String buyerSaveOrderReply(HttpSession session, Replies replies){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            Order order = (Order) session.getAttribute("currOrder");
            order.setOrderStatus(orderStatusService.getOrderStatusById(5));
            for (Reply reply : replies.getReplies()){
                reply.setReplyTime(new Date());
                reply.setBuyer(currBuyer);
                reply.setSeller(order.getSeller());
                replyService.saveReply(reply);
                if("goodReply".equals(reply.getType())){
                    Goods goods = reply.getStandard().getGoods();
                    goods.setGoodRelyCount(goods.getGoodRelyCount() + 1);
                    goodsService.updateGoods(goods);
                }
            }
            orderService.updateOrderStatus(order);
            session.setAttribute("operate","buyer_reply_success");
            return "buyer_info";
        }
        return "buyer_login";
    }

    /**
     * 判断订单是否评价过
     * @param orderId 订单的id
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "check_order_reply")
    public String checkOrderRely(Integer orderId){
        Order order = orderService.getOrderById(orderId);
        if(order.getOrderStatus().getId() == 4){
            return "true";
        }
        return "false";
    }

    /**
     * 买家收藏的商品
     * @param session 保存作用域
     * @return 收藏商品的页面
     */
    @GetMapping(value = "buyer_like_goods_list")
    public String buyerLikeGoodsList(HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            List<Goods> goodsList = GoodsCountUtil.setGoodsListCount(goodsService.listGoodsBuyerLike(currBuyer.getId()));
            session.setAttribute("goodsBuyerLike",goodsList);
            session.setAttribute("likeTip","goods");
            return "buyer_like";
        }
        return "buyer_login";

    }

    /**
     * 获取买家收藏的店铺
     * @param session 保存作用域
     * @return 收藏的店铺
     */
    @GetMapping(value = "buyer_like_seller_list")
    public String buyerLikeSellerList(HttpSession session){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            List<Seller> sellers = sellerService.listSellerLikeByBuyerId(currBuyer.getId());
            session.setAttribute("sellersBuyerLike",sellers);
            session.setAttribute("likeTip","seller");
            return "buyer_like";
        }
        return "buyer_login";
    }

    /**
     * 买家投诉
     * @param orderItemId 订单详情id
     * @param complain 投诉参数
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_complain")
    public String buyerComplain(HttpSession session, Integer orderItemId,Complain complain){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            complain.setId(orderItemId);
            complain.setCheckFlag(false);
            complain.setComplainTime(new Date());
            complainService.saveComplain(complain);
            return "success";
        }
        return "buyer_login";
    }

    /**
     * 跳转退货的页面
     * @param orderItemId 订单详情的id
     * @return 跳转页面
     */
    @GetMapping(value = "buyer_back_goods")
    public String buyerBackGoods(HttpSession session,Integer orderItemId){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
            BackGoodsInfo backGoodsInfo = orderItem.getBackGoodsInfo();
            if(null == backGoodsInfo){
                session.setAttribute("backStatus","null");
            }else {
                session.setAttribute("backStatus","notNull");
            }
            session.setAttribute("orderItem",orderItem);
            session.setAttribute("operate","buyer_back_goods");
            return "buyer_info";
        }
        return "buyer_login";
    }

    /**
     * 提交退货信息
     * @param orderItemId 订单详情的id
     * @param backGoodsInfo 退货的信息
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "buyer_sub_back_goods")
    public String buyerSubBackGoods(HttpSession session, Integer orderItemId, BackGoodsInfo backGoodsInfo){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            backGoodsInfo.setBackTime(new Date());
            backGoodsInfo.setBackStatus("退款中");
            backGoodsInfoService.saveBackGoodsInfo(backGoodsInfo);
            OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
            orderItem.setBackGoodsInfo(backGoodsInfo);
            orderItemService.updateOrderItem(orderItem);
            return "success";
        }
        return "buyer_login";
    }

    @GetMapping(value = "buyer_back_goods_info_list")
    public String buyerBackGoodsInfoList(HttpSession session, Integer orderItemCurrPage){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            Integer orderItemCount = orderItemService.countOrderItemByBuyerOrSeller(currBuyer.getId(),"buyer",0);
            Integer pageCount = (orderItemCount + PAGE_SIZE - 1) / PAGE_SIZE;
            if(pageCount == 0){
                pageCount = 1;
            }
            if(orderItemCurrPage == null || orderItemCurrPage <= 0){
                orderItemCurrPage = 1;
            }
            if(orderItemCurrPage > pageCount){
                orderItemCurrPage = pageCount;
            }
            List<OrderItem> orderItems = orderItemService.listOrderItemByBuyerOrSeller(currBuyer.getId(),PAGE_SIZE * (orderItemCurrPage - 1), PAGE_SIZE,"buyer",0);
            session.setAttribute("orderItems",orderItems);
            session.setAttribute("orderItemCurrPage",orderItemCurrPage);
            session.setAttribute("orderItemCount",pageCount);
            session.setAttribute("operate", "buyer_back_goods_info_list");
            return "buyer_info";
        }
        return "buyer_login";
    }

    /**
     * 取消退货
     * @param backGoodsId 退货信息id
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping("buyer_operate_back_goods")
    public String buyerOperateBackGoods(HttpSession session, Integer backGoodsId,String type){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null) {
            BackGoodsInfo backGoodsInfo = backGoodsInfoService.getBackGoodsInfoById(backGoodsId);
            if("cancel".equals(type)){
                backGoodsInfo.setBackStatus("取消退款");
            }else if("again".equals(type)){
                backGoodsInfo.setBackStatus("退款中");
            }
            backGoodsInfoService.updateBackGoodsInfo(backGoodsInfo);
            return "success";
        }
        return "buyer_login";
    }

}
