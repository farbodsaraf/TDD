package com.bigbox.b2csite.order.service.impl;

import com.bigbox.b2csite.order.dao.OrderDao;
import com.bigbox.b2csite.order.model.domain.OrderSummary;
import com.bigbox.b2csite.order.model.entity.OrderEntity;
import com.bigbox.b2csite.order.model.transformer.OrderEntityToOrderSummaryTransformer;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import java.util.LinkedList;
import java.util.List;

public class OrderServiceImplTest {

    private final static long CUSTOMER_ID = 1L;

	@Test
    public void test_getOrderSummary_success() throws Exception {

        //setup
        OrderServiceImpl target = new OrderServiceImpl();

        OrderDao mockOrderDao = Mockito.mock(OrderDao.class);
        target.setOrderDao(mockOrderDao);

        OrderEntityToOrderSummaryTransformer mockOrderEntityToOrderSummaryTransformer = Mockito.mock(OrderEntityToOrderSummaryTransformer.class);
        target.setTransformer(mockOrderEntityToOrderSummaryTransformer);

        OrderEntity orderEntityFixture = new OrderEntity();
        List<OrderEntity> orderEntityListFixture = new LinkedList<>();

        orderEntityListFixture.add(orderEntityFixture);

        Mockito.when(mockOrderDao.findOrdersByCustomer(CUSTOMER_ID))
                .thenReturn(orderEntityListFixture);

        OrderSummary orderSummaryFixture = new OrderSummary();
        Mockito.when(mockOrderEntityToOrderSummaryTransformer.transform(orderEntityFixture))
                .thenReturn(orderSummaryFixture);

        //execution
        List<OrderSummary> result = target.getOrderSummary(CUSTOMER_ID);

        //verification
        //Mockito.verify(mockOrderDao).findOrdersByCustomer()
        Mockito.verify(mockOrderDao).findOrdersByCustomer(CUSTOMER_ID);
        Mockito.verify(mockOrderEntityToOrderSummaryTransformer).transform(orderEntityFixture);
        Assert.assertNotNull(result);
        Assert.assertEquals(CUSTOMER_ID, result.size());
        Assert.assertSame(orderSummaryFixture, result.get(0));
    }
}
