package ECommerceApi.POJO;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderDetail> orders;


    public List<OrderDetail> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetail> orders) {
        this.orders = orders;
    }



}
