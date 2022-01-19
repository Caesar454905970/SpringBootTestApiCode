package com.example.demo;


import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysMenuMapper;
import com.example.demo.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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


	 //用户注册时，调用加密密码
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	@Test
	public void BCryptPasswordEncoder1(){
		//模拟用户注册；校验密码：相同返回true
		System.out.println(passwordEncoder.matches("1234","$2a$10$B4uniSHekWjiFns8G.Qdmu7Xh0HaPcS5zWCcK.3h3QMa4x7Cw0xRS"));

	}




	 @Test
	public void BCryptPasswordEncoder(){
		 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		 String encode = bCryptPasswordEncoder.encode("1234");//传入一个字符串进行加密，返回一个秘文；用户注册使用
		 String encode2 = bCryptPasswordEncoder.encode("1234");//传入一个字符串进行加密，返回一个秘文；用户注册使用
		 //虽然是同一个密码，但是加密得到的结果是一样的
		 //用户注册使用，数据库里面存的是加密后的秘文
		 System.out.println(encode); //$2a$10$B4uniSHekWjiFns8G.Qdmu7Xh0HaPcS5zWCcK.3h3QMa4x7Cw0xRS
		 System.out.println(encode2); //$2a$10$8LuziqhyWazWcFF5OUoIC.9FcCht35JcuhqDMvVZDI22BgJIq7wUe

		 //校验密码：相同返回true
		 System.out.println(bCryptPasswordEncoder.matches("1234","$2a$10$B4uniSHekWjiFns8G.Qdmu7Xh0HaPcS5zWCcK.3h3QMa4x7Cw0xRS"));

	 }

	@Autowired
	private SysMenuMapper sysMenuMapper;

	//测试selectPermsByUserId是否能返回对应用户的权限信息
	 @Test
	public void selectPermsByUserId(){
		 List<String> list= sysMenuMapper.selectPermsByUserId(1L);
		 System.out.println(list);
	 }

}
