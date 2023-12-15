package com.example.schoolProject.propertyFilter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

public class DomainPropertyFilter {
    private String id;
    private String[] propertyArray;

    public DomainPropertyFilter(String id, String[] propertyArray) {
        this.id = id;
        this.propertyArray = propertyArray;
    }

    public Object getFilteredObject(Object grade){
        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAllExcept(propertyArray);
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter(id, simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(grade);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }
}
