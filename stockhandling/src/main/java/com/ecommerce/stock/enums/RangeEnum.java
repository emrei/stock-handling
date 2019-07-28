package com.ecommerce.stock.enums;

/**
 * Enum class for time ranges
 * @author YunusEmre
 *
 */
public enum RangeEnum {
    TODAY("today"),
    LAST_MONTH("lastMonth");
    
    private final String range; 
    
    private RangeEnum(String range) {
	this.range = range;
    }
    
    public String range() {
	return this.range;
    }
}
