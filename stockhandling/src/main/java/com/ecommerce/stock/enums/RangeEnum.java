package com.ecommerce.stock.enums;

/**
 * Enum class for time ranges
 * 
 * @author YunusEmre
 *
 */
public enum RangeEnum {
    TODAY("today"), LAST_MONTH("lastMonth");

    private final String value;

    private RangeEnum(String range) {
	this.value = range;
    }

    public String getValue() {
	return this.value;
    }

    public static RangeEnum fromValue(String value) {
	for (RangeEnum range : values()) {
	    if (range.value.equals(value)) {
		return range;
	    }
	}
	throw new IllegalArgumentException(
		"Unknown time range " + value + ", Allowed values are [today, lastMonth]");
    }
}
