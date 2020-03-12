package com.wnmap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.nmap4j.Nmap4j;
import org.nmap4j.data.NMapRun;
import org.nmap4j.data.host.Address;
import org.nmap4j.data.nmaprun.Host;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wnmap.bean.HostTask;
import com.wnmap.repository.HostRepository;
import com.wnmap.repository.HostTaskRepository;

@Component
@Scope("prototype")
public class HostScanRunnable implements Runnable {
    private String ip;
    private Long id;
    private String scanType = "hostscan";
    private String flag = "-sn";
    
	private NmapPath nmapPath = (NmapPath)SpringUtil.getBean("nmapPath");
	private String nmap = nmapPath.nmappath;
    
//    @Autowired
    HostRepository hostRepository = (HostRepository)SpringUtil.getBean("hostRepository");
    HostTaskRepository hostTaskRepository = (HostTaskRepository)SpringUtil.getBean("hostTaskRepository");
    
    final SimpleDateFormat ft = new SimpleDateFormat ("MM-dd HH:mm:ss");

    public HostScanRunnable(HostTask hostTask) {
    	this.id = hostTask.getId();
        this.ip = hostTask.getIp();
	}
    
    @Override
    public void run() {
    	HostTask hostTask;
    	for (int ii=0; ii<3; ii++) {
        	try {
		        Thread.sleep(2000);
            	hostTask = hostTaskRepository.findById(id).get();
            	hostTask.setRuntime(new Date().getTime());
            	hostTask.setState("running");
            	hostTaskRepository.save(hostTask);
            	break;
    		} catch (Exception e) {
    			// TODO: handle exception
    			System.err.println(e);
    			System.err.println("id"+id);
    		}
    	}

    	ArrayList<Host> targetList = getTargetList(ip);
        System.out.println(scanType + ft.format(new Date())+"test" + ip);
        Nmap4j nmap4j = new Nmap4j( nmap ) ;
        nmap4j.includeHosts( ip );
        nmap4j.addFlags(flag);
        try{
        	 nmap4j.execute() ; 
        	}
        catch(Exception e){
        	 System.out.println("error execute");
        	}
    	if( !nmap4j.hasError() ) { 
//	    	 System.out.println(""+nmap4j.getOutput()+"\n");
	    	 NMapRun nMapRun = nmap4j.getResult();
	    	 if (nMapRun!=null) {
		    	 System.out.println(""+nMapRun.toString()+"\n");
		     	ArrayList<Host> resList = nMapRun.getHosts();
		     	String resTime = nMapRun.getStart();
		     	Long resTimeLong = Long.parseLong(resTime)*1000;
		     	for (Host host : resList) {
		     		ArrayList<Address> arr= host.getAddresses();
		     		String onlineip = arr.get(0).getAddr();
		     		String onlinemac = null;
		     		String onlinemacvendor = null;
		     		if (arr.size()==2) {
			     		onlinemac = arr.get(1).getAddr();
			     		onlinemacvendor = arr.get(1).getVendor();
		     		}
		     		
		     		com.wnmap.bean.Host nmaphost= hostRepository.findByIp(onlineip);
		     		if (nmaphost!=null) {
	     				// 在线主机已经发现
		     			if (nmaphost.getState()) {
		     				Long totaltimeLong = resTimeLong-nmaphost.getUptime();
		     				nmaphost.setTotaltime(totaltimeLong);
		     				hostRepository.save(nmaphost);
		     			} else {
		     				nmaphost.setState(true);
		     				nmaphost.setTotaltime(0L);
		     				nmaphost.setUptime(resTimeLong);
		     				nmaphost.setDowntime(null);
		     				hostRepository.save(nmaphost);
						}
		     		} else {
		     			// 在线主机第一次发现
		     			com.wnmap.bean.Host newHost = new com.wnmap.bean.Host();
		     			newHost.setIp(onlineip);
		     			newHost.setFoundtime(resTimeLong);
//		     			newHost.setFoundformat(resTimeLong);
		     			newHost.setState(true);
		     			newHost.setTotaltime(0L);
		     			newHost.setUptime(resTimeLong);
		     			newHost.setDowntime(null);
		     			newHost.setMacaddress(onlinemac);
		     			newHost.setMacvendor(onlinemacvendor);
	     				hostRepository.save(newHost);
		     		}
		     	}
		     	for (Host targethost:targetList) {
		     		// 筛选本次离线主机
		     		String targetip = targethost.getAddresses().get(0).getAddr();
		     		com.wnmap.bean.Host nmaphost= hostRepository.findByIp(targetip);
		     		if (nmaphost!=null) {
	     				// 目标主机曾被发现
		     			if (nmaphost.getState()) {
		     				Long totaltimeLong = resTimeLong-nmaphost.getUptime();
		     				Long totaltime = nmaphost.getTotaltime();
		     				if (totaltime.longValue()!=totaltimeLong.longValue()) {
			     				nmaphost.setTotaltime(0L);
			     				nmaphost.setState(false);
			     				nmaphost.setUptime(null);
			     				nmaphost.setDowntime(resTimeLong);
			     				hostRepository.save(nmaphost);
		     				}
		     			}
		     		}
		     	}
	    	 }
    	}
    	 else {
    		 System.out.println( nmap4j.getExecutionResults().getErrors() ) ; }
    	hostTask = hostTaskRepository.findById(id).get();
    	hostTask.setRuntime(0L);
    	hostTask.setState("stopped");
    	hostTaskRepository.save(hostTask);
    }
    
    private ArrayList<Host> getTargetList(String target) {
        Nmap4j nmap4j = new Nmap4j( nmap ) ;
        nmap4j.includeHosts( target );
        nmap4j.addFlags("-sL");
        try{
        	 nmap4j.execute() ; 
        	}
        catch(Exception e){
        	 System.out.println("error execute");
        	}
    	if( !nmap4j.hasError() ) { 
//	    	 System.out.println(""+nmap4j.getOutput()+"\n");
	    	 NMapRun nMapRun = nmap4j.getResult();
	    	 if (nMapRun!=null) {
		    	 System.out.println(""+nMapRun.toString()+"\n");
		    	 return nMapRun.getHosts();
	    	 }
    	}
    	 else {
    		 System.out.println( nmap4j.getExecutionResults().getErrors() ) ;
    		 }
		 return new ArrayList<Host>();
		
	}
}
