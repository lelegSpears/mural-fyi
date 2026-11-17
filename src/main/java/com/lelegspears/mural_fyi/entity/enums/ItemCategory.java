package com.lelegspears.mural_fyi.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemCategory {
    ELECTRONICS("Eletrônicos"),
    DOCUMENTS("Documentos"),
    CLOTHING("Vestuário"),
    ACCESSORIES("Acessórios"),
    OTHER("Outros");

    private final String categoryLabel;
}