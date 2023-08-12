package hu.webuni.webclientservice;

public interface WebClientServiceInterface {

    String callGetMicroservice(String port, String endpoint);
    Long callPostMicroservice(String port, String endpoint, String object);
}
