package com.lelegspears.mural_fyi.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemStatus {
    UNCLAIMED("Aguardando retirada"),
    REDEEMED("Recuperado");

    private final String statusLabel;
}