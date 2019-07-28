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

    /**
     * Creates RangeEnum for given value. This is a simple mapping. If this enum
     * class keeps more time range in the future. It can be created a specific
     * object instead of enum class
     * 
     * @param value
     * @return
     */
    public static RangeEnum fromValue(String value) {
	for (RangeEnum range : values()) {
	    if (range.value.equals(value)) {
		return range;
	    }
	}
	throw new IllegalArgumentException("Unknown time range " + value + ", Allowed values are [today, lastMonth]");
    }
}
