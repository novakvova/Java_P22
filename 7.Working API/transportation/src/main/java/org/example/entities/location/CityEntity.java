package org.example.entities.location;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entities.common.BaseEntity;
import org.hibernate.Length;

@Data
@Entity
@Table(name = "cities")
public class CityEntity extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private CountryEntity country;

    @Column(length = 50000)
    private String description;

    //Кількість населення
    @Column
    private int population;

    //Часовий пояс
    @Column
    private String timezone;

    //Код головного аеропорту
    @Column
    private String mainAirportCode;

    //Середня вартість обіду
    @Column
    private double avgMealPrice;

    //Середня ціна готелю
    @Column
    private double avgHotelPrice;

    //Чи можна купатися
    @Column
    private Boolean hasRecreationalWater;
}

