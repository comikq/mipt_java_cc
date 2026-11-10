package models;

import annotations.Range;

public class InvalidBoundsModel {
    @Range(min = 10, max = 1)
    private int value;
}