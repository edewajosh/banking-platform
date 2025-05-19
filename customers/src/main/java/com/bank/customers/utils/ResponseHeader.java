package com.bank.customers.utils;

public record ResponseHeader(String statusCode, String statusMessage, String messageCode, String messageCodeDescription) {
}
