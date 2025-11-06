package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class AmazonUnitTest {

    @Test
    @DisplayName("specification-based")
    void testUsesRules() {

        ShoppingCart cart = Mockito.mock(ShoppingCart.class);


        var items = List.of(new Item(ItemType.OTHER, "Pen", 1, 5.0));
       
        Mockito.when(cart.getItems()).thenReturn(items);

        PriceRule r1 = Mockito.mock(PriceRule.class);
        PriceRule r2 = Mockito.mock(PriceRule.class);

        Mockito.when(r1.priceToAggregate(items)).thenReturn(5.0);

        Mockito.when(r2.priceToAggregate(items)).thenReturn(2.0);

        Amazon amazon = new Amazon(cart, List.of(r1, r2));

        double subTotal = amazon.calculate();

        assertThat(subTotal).isEqualTo(7.0);

    }


    @Test
    @DisplayName("specification-based")
    void testNormalCostAndDeliveryAndExtra() {

        var items = List.of(new Item(ItemType.OTHER, "A", 2, 3.0));
        var regularCost = new org.example.Amazon.Cost.RegularCost();

        assertThat(regularCost.priceToAggregate(items)).isEqualTo(6.0);

        var deliveryPrice = new org.example.Amazon.Cost.DeliveryPrice();

        assertThat(deliveryPrice.priceToAggregate(items)).isEqualTo(5.0);

        var extraCost = new org.example.Amazon.Cost.ExtraCostForElectronics();

        assertThat(extraCost.priceToAggregate(items)).isEqualTo(0.0);
    }


    @Test
    @DisplayName("structural-based")
    void testEmptyLists() {
        var regularCost = new org.example.Amazon.Cost.RegularCost();

        assertThat(regularCost.priceToAggregate(List.of())).isEqualTo(0.0);

        var deliveryPrice = new org.example.Amazon.Cost.DeliveryPrice();

        assertThat(deliveryPrice.priceToAggregate(List.of())).isEqualTo(0.0);

        var extraCost = new org.example.Amazon.Cost.ExtraCostForElectronics();

        assertThat(extraCost.priceToAggregate(List.of())).isEqualTo(0.0);

    }

    @Test
    @DisplayName("structural-based")
    void testAddToCart() {
        ShoppingCart shopCart = Mockito.mock(ShoppingCart.class);
        Amazon amazon = new Amazon(shopCart, List.of());

        Item item = new Item(ItemType.OTHER, "Toy", 1, 3.0);
        amazon.addToCart(item);

        Mockito.verify(shopCart, Mockito.times(1)).add(item);

    }

    @Test
    @DisplayName("specification-based")
    void testDeliveryPriceRanges_midAndLarge() {

        var deliveryPrice = new org.example.Amazon.Cost.DeliveryPrice();

        var five = java.util.stream.IntStream.range(0,5)
                .mapToObj(i -> new Item(ItemType.OTHER, "it"+i,1,1.0))
                .toList();
                
        assertThat(deliveryPrice.priceToAggregate(five)).isEqualTo(12.5);

        var eleven = java.util.stream.IntStream.range(0,11)
                .mapToObj(i -> new Item(ItemType.OTHER, "it"+i,1,1.0))
                .toList();

        assertThat(deliveryPrice.priceToAggregate(eleven)).isEqualTo(20.0);
    }

    @Test
    @DisplayName("specification-based")
    void testExtraCostWithElectronicItem() {

        var extraCost = new org.example.Amazon.Cost.ExtraCostForElectronics();

        var list = List.of(new Item(ItemType.ELECTRONIC, "Phone",1,100.0));

        assertThat(extraCost.priceToAggregate(list)).isEqualTo(7.50);
    }

}
