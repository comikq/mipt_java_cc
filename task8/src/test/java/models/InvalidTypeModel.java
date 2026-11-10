package models;

import annotations.Size;

public class InvalidTypeModel {
    @Size(min = 0, max = 5)
    private Integer value;
}