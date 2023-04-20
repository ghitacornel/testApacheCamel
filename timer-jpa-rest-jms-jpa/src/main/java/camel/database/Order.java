package camel.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_ORDER")
public class Order {

    @Id
    @GeneratedValue
    private Integer id;

    private String product;
    private int price;
    private int quantity;

    private OrderStatus status = OrderStatus.NEW;

}
