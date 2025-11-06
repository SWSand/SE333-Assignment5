package org.example.Barnes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

class BarnesSpecificationTest {

    @Test
    @DisplayName("specification-based")
    void testMoreThanAvailable() {

        // Arrange
        BookDatabase bookDatabase = Mockito.mock(BookDatabase.class);
        BuyBookProcess buyProcess = Mockito.mock(BuyBookProcess.class);

        Book book = new Book("ISBN-1", 10, 2);
        Mockito.when(bookDatabase.findByISBN("ISBN-1")).thenReturn(book);

        BarnesAndNoble barnesNoble = new BarnesAndNoble(bookDatabase, buyProcess);
        // Act
        PurchaseSummary purchSummary = barnesNoble.getPriceForCart(Map.of("ISBN-1", 5));


        // Assert
        assertThat(purchSummary).isNotNull();

        assertThat(purchSummary.getTotalPrice()).isEqualTo(20);

        assertThat(purchSummary.getUnavailable()).hasSize(1);

        assertThat(purchSummary.getUnavailable().get(book)).isEqualTo(3);

        Mockito.verify(buyProcess, times(1)).buyBook(book, 2);
    }

    @Test
    @DisplayName("specification-based")
    void testMultipleIsbnsShouldChargeCorrectly() {
        BookDatabase database = Mockito.mock(BookDatabase.class);

        BuyBookProcess buyBookProcess = Mockito.mock(BuyBookProcess.class);

        Book b1 = new Book("ISBN-A", 5, 2);

        Book b2 = new Book("ISBN-B", 3, 1);

        Mockito.when(database.findByISBN("ISBN-A")).thenReturn(b1);

        Mockito.when(database.findByISBN("ISBN-B")).thenReturn(b2);

        BarnesAndNoble barnesNoble = new BarnesAndNoble(database, buyBookProcess);

        PurchaseSummary purchaseSummary = barnesNoble.getPriceForCart(Map.of("ISBN-A",2, "ISBN-B",1));

        assertThat(purchaseSummary).isNotNull();
    
        assertThat(purchaseSummary.getTotalPrice()).isEqualTo(2*5 + 1*3);

        Mockito.verify(buyBookProcess, times(1)).buyBook(b1,2);

        Mockito.verify(buyBookProcess, times(1)).buyBook(b2,1);
    }
}
