import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.wso2.grpc.Event;
import org.wso2.grpc.EventServiceGrpc;

import java.util.Map;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:"+9806).usePlaintext().build();
        Event.Builder requestbuilder = Event.newBuilder();
        EventServiceGrpc.EventServiceStub asyncstub = EventServiceGrpc.newStub(channel);
//        String jString = "{ \"message\": \"Benjamin Watson\", \"name\": \"Lahiru Chandima\"}";
            String s = "{\"meta_clientType\": \"PRODUCTION\",\"applicationConsumerKey\": \"ipxiWMu0Be_hl_IzhCbIqli6ufwa\",\"applicationName\": \"MCGatewayApp\",\"applicationId\": \"3\",\"applicationOwner\": \"admin\", \"apiContext\": \"/catalogue/1.0.0\",\"apiName\": \"CatalogueAPI\",\"apiVersion\": \"1.0.0\",\"apiResourcePath\": \"/getVersion\", \"apiResourceTemplate\": \"/getVersion\", \"apiMethod\": \"GET\",\"apiCreator\": \"admin\",\"apiCreatorTenantDomain\": \"carbon.super\",\"apiTier\": \"\", \"apiHostname\": \"localhost:9095\",\"username\": \"admin@carbon.super\",\"userTenantDomain\": \"carbon.super\", \"userIp\": \"localhost\",\"userAgent\": \"curl/7.58.0\",\"requestTimestamp\": 1568271336841,\"throttledOut\": false,\"responseTime\": 728,\"serviceTime\": 624,\"backendTime\": 104,\"responseCacheHit\": false,\"responseSize\": 0,\"protocol\": \"http\",\"responseCode\": 200,\"destination\": \"http://localhost:8280/services/Version\",\"securityLatency\": 621,\"throttlingLatency\": 3,\"requestMedLat\": 0,\"responseMediationLatency\": 0,\"backEndLatency\": \"104\",\"otherLatency\": \"0\",\"gatewayType\": \"MICRO\",\"label\": \"MICRO\"}";
      String jString = "{ \"message\": \"Benjamin Watson\", \"name\": \"Lahiru Chandima\"}";
        requestbuilder.setPayload(s);
        requestbuilder.putHeaders("stream.id", "InComingRequestStream");
        Event sequennceCallRequest = requestbuilder.build();

        StreamObserver<Empty> responseObserver = new StreamObserver<com.google.protobuf.Empty>() {
            @Override
            public void onNext(com.google.protobuf.Empty value) {
                System.out.println("On next is happened");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("On error happened");
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed happened");
            }
        };
//        Map <String,String> sa = new Map();


        StreamObserver requestObserver = asyncstub.consume(responseObserver);
        requestObserver.onNext(sequennceCallRequest);
        System.out.println("Here it achieved");
        Thread.sleep(10);
        requestObserver.onCompleted();
        Thread.sleep(1000);
    }
}
