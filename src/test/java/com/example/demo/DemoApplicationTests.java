package com.example.demo;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.SysUser;

import com.example.demo.framework.web.domain.AjaxResult;
import com.example.demo.mapper.SysMenuMapper;
import com.example.demo.mapper.SysUserMapper;
import org.ehcache.core.spi.store.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootTest
//@MapperScan("com.example.demo.mapper") //配置Mapper扫描
public class DemoApplicationTests {


 	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	public DemoApplicationTests(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

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




	//测试mongodb
	private MongoTemplate mongoTemplate;


	 //1.创建集合
	@Test
	public void testCreateCollextion(){
		 mongoTemplate.createCollection("products");
	}

	//2.删除集合
	@Test
	public void testDropCollection(){
		mongoTemplate.dropCollection("products");
	}

	//测试easyExcle
	@Test
	public void testEasyExcle(){

		//读取文件
		//创建ExcleReaderBuilder实例
		ExcelReaderBuilder readerBuilder = EasyExcel.read();
	    //获取要读取的文件对象
		readerBuilder.file("C:\\Users\\521\\Desktop\\sys_user.xls");
		//指定sheet:不指定，会默认读取全部的sheet
		readerBuilder.sheet("sys_user");
		//自动关闭输入流
		readerBuilder.autoCloseStream(true);
		//设置excle文件格式 .xls
		readerBuilder.excelType(ExcelTypeEnum.XLS);
		//注册一个监听器：将每一行读取的结果，进行解析
		readerBuilder.registerReadListener(new AnalysisEventListener<Map<Integer,String>>() {

			@Override
			public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
				Set<Integer> keySet = integerStringMap.keySet();
				Iterator<Integer> interator = keySet.iterator();
				while(interator.hasNext()) {
					Integer key = interator.next();
					System.out.print(key +":" +integerStringMap.get(key)+", ");

				}
				System.out.println("");
			}

			@Override
			public void doAfterAllAnalysed(AnalysisContext analysisContext) {
				//通知用户：所有的行已经读取完毕
				System.out.println("数据读取完毕");
			}
		});
		//构建读取器
		ExcelReader reader = readerBuilder.build();
		//读取数据
		reader.readAll();
		//读取完毕
		reader.finish();


		// 读取文件，读取完之后会自动关闭
        /*
            pathName        要读的文件路径；"d:\\模拟在线202003班学员信息.xls"
            head            每行数据对应的实体类；Student.class
            readListener    读监听器，每读一样就会调用一次该监听器的invoke方法；invoke中可以操作读取到的数据

            sheet方法参数： 工作表的顺序号（从0开始）或者工作表的名字，不传默认为0
        */
		//获得一个工作簿对象
//		EasyExcel.read();
		//获得一个工作表对象




	}

	//简化版
	//测试easyExcle
	@Test
	public void testEasyExcle1(){
		List<Map<Integer, String>>  list = new LinkedList<>();
		EasyExcel.read("C:\\Users\\521\\Desktop\\sys_user.xls") //获取要读取的文件对象
				.sheet("sys_user") 		//指定sheet:不指定，会默认读取全部的sheet
				.registerReadListener(new AnalysisEventListener<Map<Integer, String>>(){ //注册一个监听器：将每一行读取的结果，进行解析
					@Override
					public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
						//把每一次读取的一行integerStringMap放入list
						list.add(integerStringMap);
					}

					@Override
					public void doAfterAllAnalysed(AnalysisContext analysisContext) {
						System.out.println("数据读取完毕");
					}
				}).doRead();
		//比例List中的数据
		for(Map<Integer, String> integerStringMap:list){
			Set<Integer> keySet =integerStringMap.keySet();
			Iterator<Integer> interator= keySet.iterator();
			while(interator.hasNext()){
				Integer key =interator.next();
				System.out.print(key+":"+integerStringMap.get(key)+", ");
			}
			//遍历完一个元素：输出一个换行
			System.out.println("");
		}

	}


	//简化版：绑定映射的实体类
	//测试easyExcle
	//写入数据
	@Test
	public void testEasyExcle3(){
		List<SysUser> list = new ArrayList<>();
		EasyExcel.read("C:\\Users\\521\\Desktop\\sys_user.xls") //获取要读取的文件对象
				.head(SysUser.class) //每一列的标题
				.sheet("sys_user") 		//指定sheet:不指定，会默认读取全部的sheet
				.registerReadListener(new AnalysisEventListener<SysUser>(){ //注册一个监听器：将每一行读取的结果，进行解析

					@Override
					public void invoke(SysUser sysUser, AnalysisContext analysisContext) {
						list.add(sysUser);
					}

					@Override
					public void doAfterAllAnalysed(AnalysisContext analysisContext) {
						System.out.println("数据读取完毕");
					}
				}).doRead();
//		for(SysUser sysUser : list){
//			System.out.println(sysUser);
//		}

		System.out.println(list);
		//list写入excle文件
		EasyExcel.write("C:\\Users\\521\\Desktop\\sys_user_副本.xls")
				.head(SysUser.class) //每一列的标题
				.excelType(ExcelTypeEnum.XLS)
				.sheet("sys_user")
				.doWrite(list);

	}


	/**
	 * 查询一个用户id的所有权限列表
	 * @return
	 */
	@Test
	public AjaxResult testl123(){
		//创建page对象
		Page<SysUser> page = new Page<>();
		//设置每页大小
		page.setSize(1);
		//设置当前页码
		page.setCurrent(1);
		sysUserMapper.selectMenuByUserId(page);
		System.out.println(page.getRecords()); //获取传到的数据
		System.out.println(page.getTotal()); //获取总的记录数
		return new AjaxResult(200,"查询一个用户id的所有权限列表",page);
	}

}
