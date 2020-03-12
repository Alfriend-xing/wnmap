package com.wnmap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.nmap4j.Nmap4j;
import org.nmap4j.data.NMapRun;
import org.nmap4j.data.host.Address;
import org.nmap4j.data.host.ports.Port;
import org.nmap4j.data.nmaprun.Host;

import com.wnmap.bean.HostTask;
import com.wnmap.bean.PortTask;
import com.wnmap.repository.HostRepository;
import com.wnmap.repository.PortTaskRepository;

public class PortScanRunnable implements Runnable {
    private String ip;
    private Long id;
    private String scanType = "portscan";
    private String flag = "-sS";
	private NmapPath nmapPath = (NmapPath)SpringUtil.getBean("nmapPath");
	private String nmap = nmapPath.nmappath;
    
//  @Autowired
	HostRepository hostRepository = (HostRepository)SpringUtil.getBean("hostRepository");
    PortTaskRepository portTaskRepository = (PortTaskRepository)SpringUtil.getBean("portTaskRepository");
  
    final SimpleDateFormat ft = new SimpleDateFormat ("MM-dd HH:mm:ss");

    public PortScanRunnable(PortTask portTask) {
    	this.id = portTask.getId();
        this.ip = portTask.getIp();
	}
    
    @Override
    public void run() {
    	PortTask portTask;
    	for (int ii=0; ii<3; ii++) {
        	try {
		        Thread.sleep(2000);
            	portTask = portTaskRepository.findById(id).get();
            	portTask.setRuntime(new Date().getTime());
            	portTask.setState("running");
            	portTaskRepository.save(portTask);
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
		     		ArrayList<Address> arr= host.getAddresses();
		     		String onlineip = arr.get(0).getAddr();
		     		String onlinemac = null;
		     		String onlinemacvendor = null;
	     			ArrayList<Port> portlist = host.getPorts().getPorts();
	     			Integer portsum = 0;
	     			StringBuffer openport = new StringBuffer();
	     			for (Port port : portlist) {
	     				if(port.getState().getState().equals("open")) {
		     				openport.append(port.getPortId());
		     				openport.append(",");
		     				portsum+=1;
	     				}
	     			}
	     			if(portsum>0) {
	     				openport.deleteCharAt(openport.length()-1);
	     			}
		     		if (arr.size()==2) {
			     		onlinemac = arr.get(1).getAddr();
			     		onlinemacvendor = arr.get(1).getVendor();
		     		}
		     		
		     		com.wnmap.bean.Host nmaphost= hostRepository.findByIp(onlineip);
		     		if (nmaphost!=null) {
	     				// 在线主机已经发现
		     			nmaphost.setOpenports(openport.toString());
		     			nmaphost.setPortsum(portsum);
	     				hostRepository.save(nmaphost);
		     		} else {
		     			// 在线主机第一次发现
//		     			com.wnmap.bean.Host newHost = new com.wnmap.bean.Host();
//		     			newHost.setIp(onlineip);
//		     			newHost.setState(true);
//		     			newHost.setFoundtime(resTimeLong);
//		     			newHost.setOpenports(openport.toString());
//		     			newHost.setPortsum(portsum);
//		     			newHost.setMacaddress(onlinemac);
//		     			newHost.setMacvendor(onlinemacvendor);
//	     				hostRepository.save(newHost);
		     		}
		     	}
	    	 }
    	}
    	 else {
    		 System.out.println( nmap4j.getExecutionResults().getErrors() ) ; }
    	portTask = portTaskRepository.findById(id).get();
    	portTask.setRuntime(0L);
    	portTask.setState("stopped");
    	portTaskRepository.save(portTask);
    }
}
