package com.henry.online_shopping.mapper;

import com.henry.online_shopping.dto.request.ProductRequest;
import com.henry.online_shopping.entity.Category;
import com.henry.online_shopping.entity.Product;
import com.henry.online_shopping.entity.Seller;
import com.henry.online_shopping.service.CategoryService;
import com.henry.online_shopping.service.SellerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring", uses = {SellerService.class, CategoryService.class})
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seller", source = "sellerId")
    @Mapping(target = "category", source = "categoryId")
    Product requestToModel(ProductRequest request);

    default Seller mapSeller(int sellerId, @NonNull SellerService sellerService) {
        return sellerService.getById(sellerId);
    }

    default Category mapCategory(int categoryId, @NonNull CategoryService categoryService) {
        return categoryService.getById(categoryId);
    }
}
