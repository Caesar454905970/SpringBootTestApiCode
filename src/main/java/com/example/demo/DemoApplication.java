package com.example.demo;

import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@MapperScan("com.example.demo.mapper") //配置Mapper扫描
public class DemoApplication {

	public static void main(String[] args) {


		SpringApplication.run(DemoApplication.class, args);
	}



}
