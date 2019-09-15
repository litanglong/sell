package cn.ltl.sell.service.impl;

import cn.ltl.sell.dataobject.SellerInfo;
import cn.ltl.sell.repository.SellerInfoRepository;
import cn.ltl.sell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo login(SellerInfo sellerInfo) {
        return sellerInfoRepository.findByUsernameAndPassword(sellerInfo.getUsername(), sellerInfo.getPassword());
    }
}
