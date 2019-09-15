package cn.ltl.sell.repository;

import cn.ltl.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findByUsernameAndPassword(String username, String password);
}
