package com.services.tradedoubler.product.processorservice.service.mapper;
import com.services.tradedoubler.product.processorservice.bo.Category;
import com.services.tradedoubler.product.processorservice.model.CategoryEntity;
import org.mapstruct.Mapper;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity map(Category category);
    Set<CategoryEntity> mapToList(Set<Category> categories);
}
