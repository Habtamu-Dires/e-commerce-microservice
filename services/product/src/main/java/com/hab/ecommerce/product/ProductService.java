package com.hab.ecommerce.product;

import com.hab.ecommerce.product.exeception.ProductPurchaseRequestException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product not found with ID:: " + productId
                ));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .toList();
    }

    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProduct(
            List<ProductPurchaseRequest> requestList
    ) {
        var productIds = requestList
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

//        var storedProducts = repository.findAllByIdInOrderById(productIds);
 //       List<Product> storedProducts = new ArrayList<>();
//        productIds.forEach(id ->{
//            repository.findById(id).ifPresentOrElse(storedProducts::add,
//                    () -> {throw new ProductPurchaseRequestException(
//                            "Product with id " + id + " not found");
//                    }
//            );
//        });
        List<Product> storedProducts = repository.findByListOfIds(productIds);

        if(productIds.size() != storedProducts.size()){
            throw new ProductPurchaseRequestException(
                    "One or more product does not exists"
            );
        }
        var sortedRequests = requestList
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var sortedProducts = storedProducts
                .stream()
                .sorted(Comparator.comparing(Product::getId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for(int i=0; i < sortedProducts.size(); i++){
            var product = sortedProducts.get(i);
            var productRequest = sortedRequests.get(i);
            if(product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseRequestException(
                        "Insufficient stock quantity for product ID:: "
                                + productRequest.productId()
                );
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(
                    mapper.toProductPurchaseResponse(product, productRequest.quantity())
            );
        }

        return purchasedProducts;
    }
}
