package com.example.demo;


import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
//@MapperScan("com.example.demo.mapper") //配置Mapper扫描
public class DemoApplicationTests {


 	@Autowired
	private SysUserMapper sysUserMapper;

	 @Test
	public void testSysUserMapper(){
		 List<SysUser> sysUsers = sysUserMapper.selectList(null);
		 System.out.println(sysUsers);
	 }



	 @Test
	public void BCryptPasswordEncoder(){
		 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		 String encode = bCryptPasswordEncoder.encode("1234");//传入一个字符串进行加密，返回一个秘文；用户注册使用
		 String encode2 = bCryptPasswordEncoder.encode("1234");//传入一个字符串进行加密，返回一个秘文；用户注册使用
		 //虽然是同一个密码，但是加密得到的结果是一样的
		 //用户注册使用，数据库里面存的是加密后的秘文
		 System.out.println(encode);
		 System.out.println(encode2);


	 }


}
