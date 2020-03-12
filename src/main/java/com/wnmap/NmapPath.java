package com.wnmap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NmapPath {

	@Value("${nmap.path}")
	public String nmappath;
}
