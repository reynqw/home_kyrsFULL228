package com.smarthome.entity;

// перечисление операторов сравнения для условий правил (например, >, <, ==)
public enum Operator {
    // больше
    GREATER_THAN(">"),
    // меньше
    LESS_THAN("<"),
    // равно
    EQUALS("="),
    // не равно
    NOT_EQUALS("!="),
    // больше или равно
    GREATER_OR_EQUALS(">="),
    // меньше или равно
    LESS_OR_EQUALS("<=");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Operator fromSymbol(String symbol) {
        for (Operator op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Unknown operator symbol: " + symbol);
    }
}
