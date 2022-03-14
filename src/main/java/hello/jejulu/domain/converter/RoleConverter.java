package hello.jejulu.domain.converter;

import hello.jejulu.domain.util.Role;

import javax.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role attribute) {
        if( attribute == null ) return null;
        return attribute.getValue();
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData) {
        if( dbData == null ) return null;
        return Role.enumOf(dbData);
    }
}
