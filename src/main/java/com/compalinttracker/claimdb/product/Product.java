package com.compalinttracker.claimdb.product;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Product")
@Data
@NoArgsConstructor
@Table(
        name = "product"
)
public class Product {

    //TODO: create product entity

    @Id
    @SequenceGenerator(                                 // BIGSERIAL data type
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1                          // how much a sequence increase from
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "group_name",
            nullable = false
    )
    private String productName;


    // TODO: product many to one relationship
}
