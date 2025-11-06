package org.example.Amazon;

import org.example.Amazon.Cost.ExtraCostForElectronics;
import org.example.Amazon.Cost.RegularCost;
import org.example.Amazon.Cost.DeliveryPrice;
import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Cost.PriceRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class AmazonIntegrationTest {
    @BeforeEach
    void reset() {
        new Database().resetDatabase();
    }


    @AfterAll
    static void cleanup() {
        new Database().close();
    }

    @Test
    @DisplayName("specification-based")
    void testIntegrationShouldAggregateAllRules() {

        Database database = new Database();
        ShoppingCart shopCart = new ShoppingCartAdaptor(database);

        List<PriceRule> priceRules = List.of(new RegularCost(), new DeliveryPrice(), new ExtraCostForElectronics());
        
        Amazon amazon = new Amazon(shopCart, priceRules);

        amazon.addToCart(new Item(ItemType.ELECTRONIC, "TV", 1, 100.0));
        amazon.addToCart(new Item(ItemType.OTHER, "Book", 2, 10.0));

        double total = amazon.calculate();

        assertThat(total).isEqualTo(132.5);
    }

        @Test
    @DisplayName("specification-based")
    void testAddAndGetAndNumberOfItems() {
        Database database = new Database();

        ShoppingCartAdaptor shoppingCartAdaptor = new ShoppingCartAdaptor(database);
        
        shoppingCartAdaptor.add(new Item(org.example.Amazon.Cost.ItemType.OTHER, "Notebook", 1, 2.5));

        var items = shoppingCartAdaptor.getItems();

        assertThat(items).isNotNull();
        assertThat(items).hasSize(1);

        Item item = items.get(0);

        assertThat(item.getName()).isEqualTo("Notebook");
        assertThat(item.getPricePerUnit()).isEqualTo(2.5);
    }
    
    @Test
    @DisplayName("structural-based")
    void testEmptyCartShouldReturnZero() {

        Database database = new Database();

        ShoppingCart shopCart = new ShoppingCartAdaptor(database);

        Amazon amazon = new Amazon(shopCart, List.of(new RegularCost(), new DeliveryPrice(), new ExtraCostForElectronics()));

        double total = amazon.calculate();

        assertThat(total).isEqualTo(0.0);
    }



    @Test
    @DisplayName("structural-based")
    void testResetDatabaseClearsTable() {
        Database database = new Database();

        ShoppingCartAdaptor shoppingCartAdaptor = new ShoppingCartAdaptor(database);
        shoppingCartAdaptor.add(new Item(org.example.Amazon.Cost.ItemType.OTHER, "Tmp",1,1.0));

        assertThat(shoppingCartAdaptor.getItems()).hasSize(1);

        database.resetDatabase();

        assertThat(shoppingCartAdaptor.getItems()).isEmpty();
    }
}
