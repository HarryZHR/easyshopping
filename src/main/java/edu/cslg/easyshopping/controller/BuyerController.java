package edu.cslg.easyshopping.controller;

import edu.cslg.easyshopping.dao.BuyerDetailDao;
import edu.cslg.easyshopping.pojo.*;
import edu.cslg.easyshopping.service.*;
import edu.cslg.easyshopping.util.GoodsCountUtil;
import edu.cslg.easyshopping.util.NumberUtil;
import edu.cslg.easyshopping.util.ValidationCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 控制买家的controller
 */
@Controller
public class BuyerController {

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
    /**
     * 跳转买家的首页
     * @return 首页
     */
    @GetMapping(value = "/buyer_index")
    public String buyerIndex(HttpSession session,Integer currPage){
        List<GoodsType> goodsTypes = goodsTypeService.listGoodsType();
        session.setAttribute("goodsTypeList",goodsTypes);
        Integer pageSize = 5;
        Integer goodsCount = goodsService.countGoodsAll();
        Integer pageCount = (goodsCount + pageSize - 1) / pageSize;
        if(currPage == null || currPage <= 0){
            currPage = 1;
        }
        if(currPage > pageCount){
            currPage = pageCount;
        }
        List<Goods> goodsList = GoodsCountUtil.setGoodsListCount(goodsService.listGoodsAll(pageSize * (currPage - 1), pageSize));
        session.setAttribute("buyerGoodsList",goodsList);
        session.setAttribute("buyerPageCount",pageCount);
        session.setAttribute("buyerCurrPage",currPage);
        return "buyer_index";
    }

    /**
     * 加载更多商品
     * @param currPage 当前页
     * @param session 保存作用域
     * @return 返回ajax
     */
    @GetMapping(value = "/buyer_load_goods")
    @ResponseBody
    public String buyerLoadGoods(Integer currPage, HttpSession session){
        Integer goodsCount = goodsService.countGoodsAll();
        Integer pageSize = 5;
        Integer pageCount = (goodsCount + pageSize - 1) / pageSize;
        if(currPage == null || currPage <= 0){
            currPage = 1;
        }
        if(currPage > pageCount){
            currPage = pageCount;
        }
        List<Goods> goodsList = GoodsCountUtil.setGoodsListCount(goodsService.listGoodsAll(pageSize * (currPage - 1), pageSize));
        session.setAttribute("buyerCurrPage",currPage);
        session.setAttribute("buyerNewGoodsList",goodsList);
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
     * 跳转购买商品的页面
     * @return 商品详情的页面
     */
    @GetMapping(value = "buyer_goods_detail")
    public String buyerGoodsDetail(Integer goodsId,HttpSession session){
        session.setAttribute("lastViewGoods",goodsId);
        Goods goods = GoodsCountUtil.setGoodsCount(goodsService.getGoodsById(goodsId));
        List<Standard> standards = goods.getStandards();
        List<String> sizes = new ArrayList<>();
        Map<String,String> colorMap = new HashMap<>();
        List<Reply> allReplies = new ArrayList<>();
        List<Reply> goodReplies = new ArrayList<>();
        List<Reply> multiReplies = new ArrayList<>();
        List<Reply> badReplies = new ArrayList<>();
        List<Reply> imgReplies = new ArrayList<>();
        // 获取同类型的商品
        List<Goods> listGoodsInCategory = goodsService.listGoodsByCategory(goods.getSeller().getId(),goods.getType().getId());
        for (Standard standard : standards){
            // 获取商品的颜色图片
            if(!colorMap.keySet().contains(standard.getColorImg())){
                colorMap.put(standard.getColorImg(),standard.getColor());
            }
            // 获取商品的尺寸
            if(!sizes.contains(standard.getSize())){
                sizes.add(standard.getSize());
            }
            // 获取商品的评论
            List<Reply> replyList = replyService.listReplyByStandardId(standard.getId());
            for (Reply reply : replyList){
                // 所有评论
                if(!allReplies.contains(reply)){
                    allReplies.add(reply);
                }
                // 好评
                if("goodReply".equals(reply.getType()) && !goodReplies.contains(reply)){
                    goodReplies.add(reply);
                }
                // 中评
                if("multiReply".equals(reply.getType()) && !multiReplies.contains(reply)){
                    multiReplies.add(reply);
                }
                // 差评
                if("badReply".equals(reply.getType()) && !badReplies.contains(reply)){
                    badReplies.add(reply);
                }
                // 晒图评论
                if(reply.getImg() != null && !"".equals(reply.getImg()) && !imgReplies.contains(reply)){
                    imgReplies.add(reply);
                }
            }
        }
        List<String> goodsImgDetails = new ArrayList<>();
        // 商品的细节图
        String[] goodsImgDetailArr = goods.getGoodsImg().getDetailImg().split("_");
        for (String goodsImgDetail : goodsImgDetailArr) {
            if(!goodsImgDetails.contains(goodsImgDetail)){
                goodsImgDetails.add(goodsImgDetail);
            }
        }
        goods.setGoodsImgDetail(goodsImgDetails);
        goods.setColorMap(colorMap);
        goods.setSizes(sizes);
        List<Goods> goodsList = goodsService.listGoodsBySellerAll(goods.getSeller().getId());
        // 进入商品的所在店铺所有的商品类型
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
        session.setAttribute("all_replies",allReplies);
        session.setAttribute("good_replies",goodReplies);
        session.setAttribute("bad_replies",badReplies);
        session.setAttribute("multi_replies",multiReplies);
        session.setAttribute("img_replies",imgReplies);
        session.setAttribute("list_goods_category",listGoodsInCategory);
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if (currBuyer != null){
            // 当前买家是否收藏店铺
            Integer buyerCount = buyerService.buyerLikeSellerOr(currBuyer.getId(),goods.getSeller().getId());
            if(buyerCount > 0){
                session.setAttribute("buyerLikeSeller","like");
            }
            // 当前买家是否收藏商品
            Integer goodsCount = buyerService.buyerLikeGoodsOr(currBuyer.getId(),goodsId);
            if(goodsCount > 0){
                session.setAttribute("buyerLikeGoods","like");
            }
        }
        return "buyer_goods_detail";
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
        Standard standard = standardService.getStandardToCart(goodsId, color, size);
        Integer count = standard.getCount();
        return count.toString();
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
            buyer.setNickName("默认用户" + buyer.getTel());
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
            Standard standard = standardService.getStandardToCart(goodsId, color, size);
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
            Map<Seller,CartItem> cartItemInSellerMap = new HashMap<>();
            Seller seller = null;
            for (CartItem cartItem: cartItems) {
                seller = cartItem.getStandard().getGoods().getSeller();
                if(cartItemInSellerMap.keySet().contains(seller)) {
                    cartItemInSellerMap.put(seller,cartItem);
                }else{
                    Seller newSeller = cartItem.getStandard().getGoods().getSeller();
                    cartItemInSellerMap.put(newSeller,cartItem);
                }
            }
            session.setAttribute("cartItems",cartItems);
            session.setAttribute("cartItemInsellerMap",cartItemInSellerMap);
            return "buyer_cart";
        }
        return "buyer_login";
    }
}
