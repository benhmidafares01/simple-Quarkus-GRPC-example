package com.example;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;

import java.time.Duration;

@GrpcService
public class StreamGrpcService implements StreamGrpc {

    @Override
    public Multi<ItemReply> increment(Empty request) {
       return Multi.createFrom().ticks().every(Duration.ofMillis(2))
                .select().first(100)
                .map(l -> ItemReply.newBuilder().setCount(l).build());

    }
}

