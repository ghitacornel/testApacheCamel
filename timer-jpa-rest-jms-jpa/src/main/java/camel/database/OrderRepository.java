package camel.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where o.status = camel.database.OrderStatus.NEW order by o.id")
    List<Order> findNewOrders();

    @Query("select o from Order o where o.status = camel.database.OrderStatus.TRY_FOR_VOUCHER_PERCENTAGE order by o.id")
    List<Order> findTryForVoucherPercentageOrders();

    @Transactional
    @Query("delete from Order o where o.status = camel.database.OrderStatus.PROCESSED")
    @Modifying
    void deleteProcessedOrders();

}
