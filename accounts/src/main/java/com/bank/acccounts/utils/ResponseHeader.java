package com.bank.acccounts.utils;

public record ResponseHeader(String statusCode, String statusMessage, String messageCode, String messageCodeDescription) {
}
