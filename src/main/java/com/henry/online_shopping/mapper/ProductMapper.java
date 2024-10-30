package com.henry.online_shopping.mapper;

import com.henry.online_shopping.dto.request.ProductRequest;
import com.henry.online_shopping.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seller", source = "sellerId") // TODO: Fix this line
    @Mapping(target = "category", source = "categoryId") // TODO: Fix this line
    Product dtoToModel(ProductRequest request);
}
