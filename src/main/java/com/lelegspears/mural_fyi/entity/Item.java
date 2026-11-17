package com.lelegspears.mural_fyi.entity;

import com.lelegspears.mural_fyi.entity.dto.ItemUpdateDTO;
import com.lelegspears.mural_fyi.entity.enums.ItemCategory;
import com.lelegspears.mural_fyi.entity.enums.ItemStatus;
import com.lelegspears.mural_fyi.util.Patcher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "tb_item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    @Column(nullable = false)
    private String localFound;

    @Column(nullable = false)
    private LocalDate timeFound;

    @Column(nullable = false)
    private String redeemLocal;

    @Column(nullable = true)
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private ItemStatus status = ItemStatus.UNCLAIMED;

    public Item(String name, String description, ItemCategory category, String localFound,
                LocalDate timeFound, String redeemLocal, String imgUrl, ItemStatus status) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.localFound = localFound;
        this.timeFound = timeFound;
        this.redeemLocal = redeemLocal;
        this.imgUrl = imgUrl;
    }

    public void setRedeemed(){
        this.status = ItemStatus.REDEEMED;
    }

    public void updateItemDetails(ItemUpdateDTO dto){
        if (dto == null) return;

        Patcher.apply()
                .ifNotBlank(dto.name(), val -> this.name = val)
                .ifNotBlank(dto.description(), val -> this.description = val)
                .ifPresent(dto.category(), val -> this.category = val)
                .ifNotBlank(dto.localFound(), val -> this.localFound = val)
                .ifPresent(dto.timeFound(), val -> this.timeFound = val)
                .ifNotBlank(dto.redeemLocal(), val -> this.redeemLocal = val)
                .ifNotBlank(dto.imgUrl(), val -> this.imgUrl = val);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemId, item.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemId);
    }
}
