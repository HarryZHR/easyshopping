package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.AddressDao;
import edu.cslg.easyshopping.pojo.Address;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressService {

    @Resource
    private AddressDao addressDao;

    /**
     * 通过买家id获取买家所有收货地址
     * @param buyerId 买家的id
     * @return 所有的收货地址
     */
    public List<Address> listAddressByBuyerId(Integer buyerId){
        return addressDao.listAddressByBuyerId(buyerId);
    }

    /**
     * 保存买家地址
     * @param address 地址的参数
     */
    public void saveAddress(Address address){
        addressDao.saveAddress(address);
    }

    /**
     * 获取默认地址
     * @return 默认地址
     */
    public Address getDefaultAddress(){
        return addressDao.getDefaultAddress();
    }

    /**
     * 获取所有非默认地址
     * @return 其他地址
     */
    public List<Address> listNonDefaultAddress(){
        return addressDao.listNonDefaultAddress();
    }

    /**
     * 通过id获取收货地址
     * @param addressId 地址的id
     * @return 地址
     */
    public Address getAddressById(Integer addressId){
        return addressDao.getAddressById(addressId);
    }
}
