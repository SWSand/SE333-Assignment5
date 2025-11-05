package org.example.Barnes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BarnesStructuralTest {

    @Test
    @DisplayName("structural-based")
    void whenOrderIsNull_shouldReturnNull() {

        // Arrange
        BookDatabase bookDatabase = isbn -> new Book("ISBN-1", 5, 10);
        
        BuyBookProcess bookProcess = (book, amount) -> {};

        BarnesAndNoble barnesNoble = new BarnesAndNoble(bookDatabase, bookProcess);

        // Act
        PurchaseSummary summary = barnesNoble.getPriceForCart(null);

        // Assert

        assertThat(summary).isNull();
    }
}
