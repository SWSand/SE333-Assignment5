package org.example.Amazon;

import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingCartAdaptorTest {

    @Test
    @DisplayName("specification-based")
    void testAddAndGetItems() {
        Database database = new Database();
        database.resetDatabase();

        ShoppingCartAdaptor shoppingCartAdaptor = new ShoppingCartAdaptor(database);

        shoppingCartAdaptor.add(new Item(ItemType.OTHER, "Notebook", 1, 2.5));

        List<Item> items = shoppingCartAdaptor.getItems();
        assertThat(items).isNotNull();
        assertThat(items).hasSize(1);
        Item item = items.get(0);
        assertThat(item.getName()).isEqualTo("Notebook");
        assertThat(item.getPricePerUnit()).isEqualTo(2.5);
    }

    @Test
    @DisplayName("structural-based")
    void testNumberOfItemsReturnsAnInt() {
        Database database = new Database();
        database.resetDatabase();

        ShoppingCartAdaptor shoppingCartAdaptor = new ShoppingCartAdaptor(database);

        int numberOfItems = shoppingCartAdaptor.numberOfItems();

        assertThat(numberOfItems).isGreaterThanOrEqualTo(0);
    }
}
