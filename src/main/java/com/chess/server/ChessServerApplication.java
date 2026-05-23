package com.chess.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chess.server.mapper")
public class ChessServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChessServerApplication.class, args);
    }
}
