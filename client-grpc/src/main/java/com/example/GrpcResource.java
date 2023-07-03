package com.example;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GrpcResource {

    @GrpcClient
    HelloGrpc hello;
    @GrpcClient
    MutinyStreamGrpcGrpc.MutinyStreamGrpcStub stream;

    @GET
    @Path("/mutiny/{name}")
    public Uni<String> helloMutiny(String name) {
        return hello.sayHello(HelloRequest.newBuilder().setName(name).build())
                .onItem().transform(HelloReply::getMessage);
    }
    @GET
    @Path("/stream")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Long> streamMutiny() {
        return stream.increment(Empty.newBuilder().build())
                .onItem().transform(ItemReply::getCount);
    }
}
