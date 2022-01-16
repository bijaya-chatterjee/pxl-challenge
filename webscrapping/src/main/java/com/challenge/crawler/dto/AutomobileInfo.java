package com.challenge.crawler.dto;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AutomobileInfo {
	
	private String name;
	private String prodYear;
	private String sales;
	private String notes;
	private String assembly;
	private String manufacturer;
	private String imageSource;
	private byte[] image;
	
	@Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AutomobileInfo other = (AutomobileInfo) obj;
        return Objects.equals(name, other.name);
    }

	
}
