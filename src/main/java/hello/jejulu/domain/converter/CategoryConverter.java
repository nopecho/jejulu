package hello.jejulu.domain.converter;

import hello.jejulu.domain.util.Category;

import javax.persistence.AttributeConverter;

public class CategoryConverter implements AttributeConverter<Category,Integer> {

    @Override
    public Integer convertToDatabaseColumn(Category attribute) {
        if(attribute == null) return null;
        return attribute.getValue();
    }

    @Override
    public Category convertToEntityAttribute(Integer dbData) {
        if(dbData == null) return null;
        return Category.enumOf(dbData);
    }
}
