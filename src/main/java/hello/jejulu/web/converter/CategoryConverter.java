package hello.jejulu.web.converter;

import hello.jejulu.domain.util.Category;
import org.springframework.core.convert.converter.Converter;

public class CategoryConverter implements Converter<String, Category> {

    @Override
    public Category convert(String source) {
        return Category.enumOf(Integer.parseInt(source));
    }
}
