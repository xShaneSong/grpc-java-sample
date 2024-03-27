package ecommerce;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import ecommerce.ProductInfoServiceGrpc;
import ecommerce.ProductInfo;
import java.util.logging.Logger;

public class ProductInfoClient {

    private static final Logger logger = Logger.getLogger(ProductInfoClient.class.getName());

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

        ProductInfoServiceGrpc.ProductInfoServiceBlockingStub stub =
                ProductInfoServiceGrpc.newBlockingStub(channel);

        ProductInfo.ProductID productID =
                stub.addProduct(ProductInfo.Product.newBuilder().setValue("Samsung S10")
                        .setDescription("Samsung Galaxy S10 is the latest smart phone, "
                                + "launched in February 2019")
                        .setPrice(700.0f).build());
        logger.info("Product ID: " + productID.getValue() + " added successfully.");

        logger.info("--------------------------------------:" + productID.getValue());
        ProductInfo.Product product = stub.getProduct(productID);
        logger.info("Product: " + product.toString());
        channel.shutdown();
    }
}
