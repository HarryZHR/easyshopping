package edu.cslg.easyshopping.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
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
import java.text.DecimalFormat;
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
    @Resource
    private AddressService addressService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private OrderStatusService orderStatusService;
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
            List<Address> addresses = addressService.listAddressByBuyerId(currBuyer.getId());
            address.setBuyer(currBuyer);
            address.setDelFlag(true);
            if(addresses.size() != 0){
                address.setDefaultAddress(false);
            }else {
                address.setDefaultAddress(true);
            }
            addressService.saveAddress(address);
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
                orderService.saveOrder(order);
                order.setId(order.getId());
                orders.add(order);
            }
            // 购物车中购买付款的流程 保存订单详情
            StringBuilder orderItemIdStr = new StringBuilder();
            if(cartItemIdStr != null){
                String[] cartItemIdArr = cartItemIdStr.split("_");
                for(String cartItemId : cartItemIdArr){
                    OrderItem orderItem = new OrderItem();
                    CartItem cartItem = cartItemService.getCartItemById(Integer.parseInt(cartItemId));
                    orderItem.setBuyCount(cartItem.getBuyCount());
                    orderItem.setOrderStatus(orderStatusService.getOrderStatusById(1));
                    orderItem.setStandard(cartItem.getStandard());
                    for (Order order : orders){
                        if(order.getSeller().getId().equals(cartItem.getStandard().getGoods().getSeller().getId())){
                            orderItem.setOrder(order);
                        }
                    }
                    orderItemService.saveOrderItem(orderItem);
                    Goods goods = orderItem.getStandard().getGoods();
                    goods.setSaleCount(goods.getSaleCount() + 1);
                    goodsService.updateGoods(goods);
                    orderItemIdStr.append(orderItem.getId()).append("_");
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
                orderItem.setOrderStatus(orderStatusService.getOrderStatusById(1));
                orderItem.setStandard(cartItemStraight.getStandard());
                for (Order order : orders){
                    orderItem.setOrder(order);
                }
                orderItemService.saveOrderItem(orderItem);
                Goods goods = orderItem.getStandard().getGoods();
                goods.setSaleCount(goods.getSaleCount() + 1);
                goodsService.updateGoods(goods);
                orderItemIdStr.append(orderItem.getId()).append("_");
                Standard standard = orderItem.getStandard();
                standard.setCount(orderItem.getStandard().getCount() - cartItemStraight.getBuyCount());
                standardService.updateStandard(standard);
            }
            session.setAttribute("orderItemIdStr",orderItemIdStr);
            session.setAttribute("orders",orders);
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
     * @param orderItemIdStr 订单详情id
     * @return 付款的页面
     */
    @GetMapping(value = "buyer_pay_money")
    public String buyerPayMoney(HttpSession session, String orderItemIdStr){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            String[] orderItemIdArr = orderItemIdStr.split("_");
            for(String orderItemId : orderItemIdArr){
                OrderItem orderItem = orderItemService.getOrderItemById(Integer.parseInt(orderItemId));
                orderItem.setOrderStatus(orderStatusService.getOrderStatusById(2));
                orderItemService.updateOrderItem(orderItem);
            }
            return "buyer_pay_success :: buyer_pay_success";
        }
        return "buyer_login";
    }

    @GetMapping(value = "buyer_user_info")
    public String buyerUserInfo(HttpSession session){

        return "buyer_info";
    }

    @GetMapping(value = "buyer_order_show")
    public String buyerOrderAll(Integer orderCurrPage, HttpSession session,Integer orderStatus){
        Buyer currBuyer = (Buyer) session.getAttribute("currBuyer");
        if(currBuyer != null){
            Integer pageSize = 5;
            Integer goodsCount = orderService.countOrderByStatusAndBuyer(currBuyer.getId(),null);
            Integer pageCount = (goodsCount + pageSize - 1) / pageSize;
            if(orderCurrPage == null || orderCurrPage <= 0){
                orderCurrPage = 1;
            }
            if(orderCurrPage > pageCount){
                orderCurrPage = pageCount;
            }
            List<Order> orders = orderService.listOrderByStatusAndBuyer(currBuyer.getId(),pageSize * (orderCurrPage - 1), pageSize,null);
            session.setAttribute("orders",orders);
            session.setAttribute("orderCurrPage",orderCurrPage);
            session.setAttribute("orderPageCount",pageCount);
            session.setAttribute("orderStatus",orderStatus);
            session.setAttribute("order_type","buyer_order_show");
            return "buyer_info";
        }
        return "buyer_login";
    }
}
