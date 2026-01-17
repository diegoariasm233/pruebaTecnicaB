package co.com.ecommerce.adapters.entities;

import co.com.ecommerce.model.Price;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICE")
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Integer priceList;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer priority;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "curr", nullable = false, length = 3)
    private String currency;

    public Price toDomainModel() {
        return new Price(id, brand.toDomainModel(), startDate, endDate, priceList, productId,
                priority, price, currency);
    }


}
