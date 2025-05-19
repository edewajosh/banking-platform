package com.bank.cards.utils;

public record ResponseHeader(String statusCode, String statusMessage, String messageCode, String messageCodeDescription) {
}
