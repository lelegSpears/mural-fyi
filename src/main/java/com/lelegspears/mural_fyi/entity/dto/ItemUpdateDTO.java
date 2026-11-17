package com.lelegspears.mural_fyi.entity.dto;

import com.lelegspears.mural_fyi.entity.enums.ItemCategory;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ItemUpdateDTO(
        @Size(max = 100) String name,
        @Size(max = 500) String description,
        ItemCategory category,
        @Size(max = 150) String localFound,
        @PastOrPresent LocalDate timeFound,
        @Size(max = 150) String redeemLocal,
        String imgUrl
) {}