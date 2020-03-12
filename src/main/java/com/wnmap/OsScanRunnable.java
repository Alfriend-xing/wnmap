package com.wnmap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.nmap4j.Nmap4j;
import org.nmap4j.data.NMapRun;
import org.nmap4j.data.host.Address;
import org.nmap4j.data.host.os.OsMatch;
import org.nmap4j.data.nmaprun.Host;

import com.wnmap.bean.OsTask;
import com.wnmap.bean.PortTask;
import com.wnmap.repository.HostRepository;
import com.wnmap.repository.OsTaskRepository;

public class OsScanRunnable implements Runnable {
    private String ip;
    private Long id;
    private String scanType = "osscan";
    private String flag = "-O --osscan-guess";
	private NmapPath nmapPath = (NmapPath)SpringUtil.getBean("nmapPath");
	private String nmap = nmapPath.nmappath;

//  @Autowired
    HostRepository hostRepository = (HostRepository)SpringUtil.getBean("hostRepository");
    OsTaskRepository osTaskRepository = (OsTaskRepository)SpringUtil.getBean("osTaskRepository");
    
    final SimpleDateFormat ft = new SimpleDateFormat ("MM-dd HH:mm:ss");

    public OsScanRunnable(OsTask osTask) {
    	this.id = osTask.getId();
        this.ip = osTask.getIp();
	}
    
    @Override
    public void run() {
    	OsTask osTask;
    	for (int ii=0; ii<3; ii++) {
        	try {
		        Thread.sleep(2000);
            	osTask = osTaskRepository.findById(id).get();
            	osTask.setRuntime(new Date().getTime());
            	osTask.setState("running");
            	osTaskRepository.save(osTask);
            	break;
    		} catch (Exception e) {
    			// TODO: handle exception
    			System.err.println(e);
    			System.err.println("id"+id);
    		}
    	}
    	
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
		    	 String resTime = nMapRun.getStart();
			     	Long resTimeLong = Long.parseLong(resTime)*1000;
			     	ArrayList<Host> resList = nMapRun.getHosts();
			     	for (Host host : resList) {
			     		ArrayList<OsMatch> osmatchs = host.getOs().getOsMatches();
			     		String ostype = "other";
			     		for (OsMatch osMatch:osmatchs) {
			     			String osname = osMatch.getName();
			     			if(osname.contains("Linux")) {
			     				ostype = "linux";
			     			}
			     			if(osname.contains("Windows")) {
			     				ostype = "windows";
			     			}
			     			if(osname.contains("Apple")) {
			     				ostype = "macos";
			     			}
			     			break;
			     		}
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
			     			nmaphost.setOstype(ostype);
		     				hostRepository.save(nmaphost);
			     		} else {
			     			// 在线主机第一次发现
//			     			com.wnmap.bean.Host newHost = new com.wnmap.bean.Host();
//			     			newHost.setIp(onlineip);
//			     			newHost.setState(true);
//			     			newHost.setOstype(ostype);
//			     			newHost.setFoundtime(resTimeLong);
//			     			newHost.setMacaddress(onlinemac);
//			     			newHost.setMacvendor(onlinemacvendor);
//		     				hostRepository.save(newHost);
			     		}
			     	}
		    	 }
	    	}
    	 else {
    		 System.out.println( nmap4j.getExecutionResults().getErrors() ) ; }
    	osTask = osTaskRepository.findById(id).get();
    	osTask.setRuntime(0L);
    	osTask.setState("stopped");
    	osTaskRepository.save(osTask);
    }
}
