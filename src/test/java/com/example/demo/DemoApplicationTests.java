package com.example.demo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

	 //测试自动填充的字段:插入一个sys_user
	@Test
	public void testFill(){
		SysUser user = new SysUser();
		user.setUserName("三更草堂333");
		user.setPassword("7777888");
		int r = sysUserMapper.insert(user);
		System.out.println(r);
	}


	//测试自动填充的字段:更新一个sys_user
	@Test
	public void testFill1(){
		SysUser user = new SysUser();
		user.setUserName("三更草堂3331");
		user.setId(4L);
		int r = sysUserMapper.update(user,null);
		System.out.println(r);
	}


	//测试逻辑删除
	@Test
	public void testDel(){
		int i = sysUserMapper.deleteById(4L);
		System.out.println(i);
	}
	//删除之后进行查询
	@Test
	public void testSel(){
		sysUserMapper.selectList(null);
	}



	//测试乐观锁
	@Test
	public void testVersion(){
		//查询id为4的数据
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>(); //queryWrapper用来封装查询条件
		queryWrapper.eq("id",5);
		SysUser sysUser = sysUserMapper.selectOne(queryWrapper);


		//对id为4的数据进行更新  把price修改为88
		sysUser.setUserName("三更草堂1111");
		sysUserMapper.updateById(sysUser);
	}
}
