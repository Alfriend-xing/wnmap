package com.wnmap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import org.icmp4j.IcmpPingResponse;
import org.icmp4j.IcmpTraceRouteRequest;
import org.icmp4j.IcmpTraceRouteUtil;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION,proxyMode=ScopedProxyMode.TARGET_CLASS)
public class TracerouteComponent {
	private Boolean isRunning = false;
	private File database = new File(System.getProperty("user.dir")+File.separator+"GeoLite2-City.mmdb");

	DatabaseReader reader;
	String dbfile = System.getProperty("user.dir")+File.separator+"ip2region.db";
	String cityfile = System.getProperty("user.dir")+File.separator+"cities500.txt";
	Path citypath = Paths.get(System.getProperty("user.dir")+File.separator+"cities500.txt");
	DbSearcher searcher;
	DbConfig config;
	public TracerouteComponent() {		
		try {
	        config = new DbConfig();
	        searcher = new DbSearcher(config, dbfile);
	        reader = new DatabaseReader.Builder(database).build();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		}
	
	public ArrayList<String[]> traceroute(String ip) {
		
		setIsRunning(true);
		ArrayList<String[]> resArray = new ArrayList<String[]>();
		IcmpTraceRouteRequest req = new IcmpTraceRouteRequest();
		String preGeoIp = "";
		req.setHost(ip);
		req.setPacketSize(64);
		req.setTimeout(2000);
		req.setTtl(30);
		TreeMap<Integer, IcmpPingResponse> rspTree= IcmpTraceRouteUtil.executeTraceRoute(req).getTtlToResponseMap();
		for (Integer k :rspTree.keySet()) {
			try {
				String geoip = rspTree.get(k).getHost();
				if (geoip!=null) {
					if (geoip.equals(preGeoIp) ) {
						break;
					}
					preGeoIp = geoip;
					String[] resString = new String[5];
					Long durationLong = rspTree.get(k).getDuration();
					String[] resLocation = getLocation(geoip);
					resString[0] = geoip;
					resString[1] = durationLong.toString();
					resString[2] = resLocation[0];
					resString[3] = resLocation[1];
					resString[4] = resLocation[2];
		    		resArray.add(resString);
//		    		System.out.println(rspTree.get(k).toString());
				 
		           //根据ip进行位置信息搜索
		           //采用Btree搜索
			}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error");
				System.out.println(e);
			}
		}
		setIsRunning(false);
		for (String[] p : resArray) {
    		System.out.println(Arrays.toString(p));
		}
		return resArray;
	}
	
	public Boolean getIsRunning() {
		return isRunning;
	}
	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}
	private String[] getLocation(String geoip){
		String[] resLocation = new String[3];
		try {
	        DataBlock block = searcher.btreeSearch(geoip);
	        String countryString = block.getRegion().split("\\|")[0];
	        String provinceString = block.getRegion().split("\\|")[2];
	        String cityString = block.getRegion().split("\\|")[3];
	        String nullString = "0";
	        //打印位置信息（格式：国家|大区|省份|城市|运营商）
//	        System.out.println(block.getRegion());
	        if (countryString.contains("中国")) {
	        	if (!cityString.contains("0") && !cityString.contains("内网IP")) {
//		        	System.out.println("cityString"+nullString.getClass());
		        	String[] resGeo = getGeoNames(cityString);
		        	resLocation[0] = cityString;
		        	resLocation[1] = resGeo[0];
		        	resLocation[2] = resGeo[1];
		        } else if (!provinceString.contains("0") && !provinceString.contains("内网IP")) {
//		        	System.out.println("provinceString"+provinceString);
		        	String[] resGeo = getGeoNames(provinceString);
		        	resLocation[0] = provinceString;
		        	resLocation[1] = resGeo[0];
		        	resLocation[2] = resGeo[1];
				}
	        } else {
	        	resLocation = getGeolite(geoip);
	        }
	        
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return resLocation;
	}
	
	private String[] getGeolite(String geoip){
		String[] resGeolite = new String[3];
		try {
			InetAddress ipAddress = InetAddress.getByName(geoip);
			CityResponse response = reader.city(ipAddress);
			
			String g_coun = response.getCountry().getNames().get("zh-CN");
			String g_prov = response.getMostSpecificSubdivision().getNames().get("zh-CN");
			String g_city = response.getCity().getNames().get("zh-CN");
			Location location = response.getLocation();
//			System.out.println(g_coun+"/"+g_prov+"/"+g_city+"/"
//					+location.getLatitude().toString()+"/"
//					+location.getLongitude().toString());
			String geoip_coun = g_coun==null ? "" : g_coun;
			String geoip_prov = g_prov==null ? "" : g_prov;
			String geoip_city = g_city==null ? "" : g_city;
			resGeolite[0] = geoip_coun+geoip_prov+geoip_city;
			resGeolite[1] = location.getLatitude().toString();
			resGeolite[2] = location.getLongitude().toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resGeolite;

	}
	
	private String[] getGeoNames(String cityName){
		String[] resGeoNames = new String[2];
		try {
			FileReader fr = new FileReader(cityfile);
//			BufferedReader bf = new BufferedReader(fr);
			BufferedReader bf = Files.newBufferedReader(citypath, StandardCharsets.UTF_8);
			String str;
			String citynames;
			String cityxlocation;
			String cityylocation;
			// 按行读取字符串
			while ((str = bf.readLine()) != null) {
				citynames = str.split("\t")[3];
				if (citynames.contains(cityName)){
					cityxlocation = str.split("\t")[4];
					cityylocation = str.split("\t")[5];
//					System.out.println(cityName+"/"+cityxlocation+"/"+cityylocation);
					resGeoNames[0] = cityxlocation;
					resGeoNames[1] = cityylocation;
					break;
				}
			}
			bf.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resGeoNames;
	}
}
