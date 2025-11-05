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
}
