package com.yassine.jaa.dto;

import java.time.LocalDateTime;

public record TransactionDto(String type, Double amount, Double balance, LocalDateTime date) {
}
