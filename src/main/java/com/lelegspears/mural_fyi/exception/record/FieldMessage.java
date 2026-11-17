package com.lelegspears.mural_fyi.exception.record;

public record FieldMessage(
        String fieldName,
        String message
) {
}