package ecommerce;

import io.grpc.Status;
import ecommerce.ProductInfoServiceGrpc;
import ecommerce.ProductInfo;
import java.util.*;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import io.grpc.StatusException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ProductInfoImpl extends ProductInfoServiceGrpc.ProductInfoServiceImplBase {

    private Map<String, ProductInfo.Product> productMap = new HashMap<>();

    @Override
    public void addProduct(ProductInfo.Product request, StreamObserver<ProductInfo.ProductID> responseObserver) {
        System.out.println(request);

        UUID uuid = UUID.randomUUID();
        // request.setId(uuid.toString());
        productMap.put(uuid.toString(), request);
        ProductInfo.ProductID productID = ProductInfo.ProductID.newBuilder().setValue(uuid.toString()).build();
        System.out.println("########################### :" + productID.getValue());
        responseObserver.onNext(productID);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(ProductInfo.ProductID request, StreamObserver<ProductInfo.Product> responseObserver) {
        System.out.println("---------------------");
        System.out.println(request.getValue());
        ProductInfo.Product product = productMap.get(request.getValue());
        if (product != null) {
            responseObserver.onNext(product);
        } else {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
        }
        responseObserver.onCompleted();
    }

}
