package com.hab.ecommerce.product.exeception;

public class ProductPurchaseRequestException extends RuntimeException{
    public ProductPurchaseRequestException(String msg){
        super(msg);
    }
}
