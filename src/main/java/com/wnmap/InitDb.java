package com.wnmap;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wnmap.bean.Host;
import com.wnmap.bean.HostTask;
import com.wnmap.bean.Info;
import com.wnmap.bean.Online;
import com.wnmap.bean.OsTask;
import com.wnmap.bean.Port;
import com.wnmap.bean.PortTask;
import com.wnmap.bean.User;
import com.wnmap.repository.HostRepository;
import com.wnmap.repository.HostTaskRepository;
import com.wnmap.repository.InfoRepository;
import com.wnmap.repository.OnlineRepository;
import com.wnmap.repository.OsTaskRepository;
import com.wnmap.repository.PortRepository;
import com.wnmap.repository.PortTaskRepository;
import com.wnmap.repository.UserRepository;

@Component
public class InitDb {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    HostRepository hostRepository;
    @Autowired
    OnlineRepository onlineRepository;
    @Autowired
    PortRepository portRepository;
    @Autowired
    InfoRepository infoRepository;
    @Autowired
    HostTaskRepository hostTaskRepository;
    @Autowired
    PortTaskRepository portTaskRepository;
    @Autowired
    OsTaskRepository osTaskRepository;
    
    final SimpleDateFormat ft = new SimpleDateFormat ("MM-dd HH:mm");
    
    public void init() {
    	userRepository.save(new User("admin","admin","admin"));
		userRepository.save(new User("root","root","admin"));
		userRepository.save(new User("guest","guest","guest"));
//		hostRepository.save(new Host("192.168.1.1",1580798102234L,ft.format(new Date(1580798102234L)),"80,443,8080",3 ,"linux",
//				"内网",1578747716000L,null,true ,838L ));
//		hostRepository.save(new Host("192.168.1.2",1580798102234L,ft.format(new Date(1580798102234L)),"80,443,22,21",4 ,"linux",
//				"内网",1577747716000L,null,true ,837L ));
//		hostRepository.save(new Host("192.168.1.3",1580798102234L,ft.format(new Date(1580798102234L)),"80",1 ,"linux",
//				"内网",1576747716000L,null,true ,836L ));
//		hostRepository.save(new Host("192.168.1.4",1580798102234L,ft.format(new Date(1580798102234L)),"80",1 ,"linux",
//				"内网",1575747716000L,null,true ,835L ));
//		hostRepository.save(new Host("192.168.1.5",1580798102234L,ft.format(new Date(1580798102234L)),"443",1 ,"linux",
//				"内网",1574747716000L,null,true ,834L ));
//		hostRepository.save(new Host("192.168.1.6",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"linux",
//				"内网",1573747716000L,null,true ,833L ));
//		hostRepository.save(new Host("192.168.1.7",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"linux",
//				"内网",1572747716000L,null,true ,832L ));
//		hostRepository.save(new Host("192.168.1.8",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"linux",
//				"内网",1571747716000L,null,true ,831L ));
//		hostRepository.save(new Host("192.168.1.9",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"linux",
//				"内网",1568747716000L,null,true ,828L ));
//		hostRepository.save(new Host("192.168.1.10",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"other",
//				"内网",1558747716000L,null,true ,827L ));
//		hostRepository.save(new Host("192.168.1.11",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"macos",
//				"内网",null,1578747716000L,false ,826L ));
//		hostRepository.save(new Host("192.168.1.12",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"macos",
//				"内网",null,1578747616000L,false ,825L ));
//		hostRepository.save(new Host("192.168.1.13",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"macos",
//				"内网",null,1578747516000L,false ,824L ));
//		hostRepository.save(new Host("192.168.1.14",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"macos",
//				"内网",null,1578747416000L,false ,823L ));
//		hostRepository.save(new Host("192.168.1.15",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"windows",
//				"内网",null,1578747316000L,false ,822L ));
//		hostRepository.save(new Host("192.168.1.16",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"windows",
//				"内网",null,1578747216000L,false ,821L ));
//		hostRepository.save(new Host("192.168.1.17",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"windows",
//				"内网",null,1578747116000L,false ,820L ));
//		hostRepository.save(new Host("192.168.1.18",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"windows",
//				"内网",null,1578746716000L,false ,819L ));
//		hostRepository.save(new Host("192.168.1.19",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"windows",
//				"内网",null,1578745716000L,false ,818L ));
//		hostRepository.save(new Host("192.168.1.20",1580798102234L,ft.format(new Date(1580798102234L)),"80,443",2 ,"windows",
//				"内网",null,1578743716000L,false ,817L ));
		
//		onlineRepository.save(new Online(1580792102234L,ft.format(new Date(1580792102234L)), 10));
//		onlineRepository.save(new Online(1580793102234L,ft.format(new Date(1580793102234L)), 20));
//		onlineRepository.save(new Online(1580794102234L,ft.format(new Date(1580794102234L)), 10));
//		onlineRepository.save(new Online(1580795102234L,ft.format(new Date(1580795102234L)), 20));
//		onlineRepository.save(new Online(1580796102234L,ft.format(new Date(1580796102234L)), 10));
//		onlineRepository.save(new Online(1580797102234L,ft.format(new Date(1580797102234L)), 20));
//		onlineRepository.save(new Online(1580798102234L,ft.format(new Date(1580798102234L)), 10));
		
//		portRepository.save(new Port("30",30));
//		portRepository.save(new Port("29",29));
//		portRepository.save(new Port("28",28));
//		portRepository.save(new Port("27",27));
//		portRepository.save(new Port("26",26));
//		portRepository.save(new Port("25",25));
//		portRepository.save(new Port("24",24));
//		portRepository.save(new Port("23",23));
//		portRepository.save(new Port("22",22));
//		portRepository.save(new Port("21",21));
//		portRepository.save(new Port("20",20));
		
//		infoRepository.save(new Info(0L,ft.format(new Date(0L)), 80,90));
//		infoRepository.save(new Info(1580792102234L,ft.format(new Date(1580792102234L)), 20,30));
//		infoRepository.save(new Info(1580793102234L,ft.format(new Date(1580793102234L)), 10,40));
//		infoRepository.save(new Info(1580794102234L,ft.format(new Date(1580794102234L)), 50,70));
//		infoRepository.save(new Info(1580795102234L,ft.format(new Date(1580795102234L)), 80,30));
//		infoRepository.save(new Info(1580796102234L,ft.format(new Date(1580796102234L)), 20,20));
//		infoRepository.save(new Info(1580797102234L,ft.format(new Date(1580797102234L)), 60,60));
//		infoRepository.save(new Info(1580798102234L,ft.format(new Date(1580798102234L)), 70,10));
		
//		hostTaskRepository.save(new HostTask("192.168.1.1/24","2019-12-18","5分钟","running",1579266116000L));
//		hostTaskRepository.save(new HostTask("192.168.1.104","2019-12-18","5分钟","running",1579266116000L));
//		hostTaskRepository.save(new HostTask("192.168.1.2","2019-12-18","每小时","running",1579266116000L));
//		hostTaskRepository.save(new HostTask("192.168.1.3","2019-12-18","每天","running",1579266116000L));
//		hostTaskRepository.save(new HostTask("192.168.1.4","2019-12-18","每周","running",1579266116000L));
//		hostTaskRepository.save(new HostTask("192.168.1.5","2019-12-18","每月","running",1579266116000L));
//		hostTaskRepository.save(new HostTask("192.168.1.6","2019-12-18","每年","running",1579266116000L));
		
//		portTaskRepository.save(new PortTask("192.168.1.1/24","2019-12-18","5分钟","running",1579266116000L));
//		portTaskRepository.save(new PortTask("192.168.1.12","2019-12-18","每小时","running",1579266116000L));
//		portTaskRepository.save(new PortTask("192.168.1.13","2019-12-18","每天","running",1579266116000L));
//		portTaskRepository.save(new PortTask("192.168.1.14","2019-12-18","每周","running",1579266116000L));
//		portTaskRepository.save(new PortTask("192.168.1.15","2019-12-18","每月","running",1579266116000L));
//		portTaskRepository.save(new PortTask("192.168.1.16","2019-12-18","每年","running",1579266116000L));

//		osTaskRepository.save(new OsTask("192.168.1.1/24","2019-12-18","5分钟","running",1579266116000L));
//		osTaskRepository.save(new OsTask("192.168.1.22","2019-12-18","每小时","running",1579266116000L));
//		osTaskRepository.save(new OsTask("192.168.1.23","2019-12-18","每天","running",1579266116000L));
//		osTaskRepository.save(new OsTask("192.168.1.24","2019-12-18","每周","running",1579266116000L));
//		osTaskRepository.save(new OsTask("192.168.1.25","2019-12-18","每月","running",1579266116000L));
//		osTaskRepository.save(new OsTask("192.168.1.26","2019-12-18","每年","running",1579266116000L));
	}
}
