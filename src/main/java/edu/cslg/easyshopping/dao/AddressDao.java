package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressDao {
    /**
     * 通过买家id获取买家所有收货地址
     * @param buyerId 买家的id
     * @return 所有的收货地址
     */
    List<Address> listAddressByBuyerId(Integer buyerId);

    /**
     * 保存买家地址
     * @param address 地址的参数
     */
    void saveAddress(Address address);

    /**
     * 获取默认地址
     * @return 默认地址
     */
    Address getDefaultAddress();

    /**
     * 获取所有非默认地址
     * @return 其他地址
     */
    List<Address> listNonDefaultAddress();

    /**
     * 通过id获取收货地址
     * @param addressId 地址的id
     * @return 地址
     */
    Address getAddressById(Integer addressId);
}
