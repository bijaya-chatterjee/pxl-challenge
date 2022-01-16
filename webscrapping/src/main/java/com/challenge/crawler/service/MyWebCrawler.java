package com.challenge.crawler.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.challenge.crawler.dto.AutomobileInfo;

@Service
public class MyWebCrawler {
	
	private static String URL = "https://en.wikipedia.org/wiki/List_of_automobile_sales_by_model";
	
	/**
	 * This method will return the automobile details for the name passed as input
	 * @author Bijaya
	 * @param name
	 * @return
	 */
	public AutomobileInfo getAutomobileDetailsByName(String name)
	{
		String car = null;
		AutomobileInfo autoInfo = new AutomobileInfo();
		try {
			Document doc = Jsoup.connect(URL).get();
			Elements tables = doc.select("table > tbody");
			for(Element table : tables)
			{
				Elements rows = table.select("tr");
				for (int i = 1; i < rows.size(); i++) { 
					Element row = rows.get(i);
					Elements ths = row.select("th");
					if (ths != null && ths.size() == 1) {
						Element carName = ths.get(0);
						car = carName.text();
					}
					if (ths != null && ths.size() == 2) {
						Element carName = ths.get(1);
						car = carName.text();
					}
					if(!StringUtils.isEmpty(name) && name.equalsIgnoreCase(car))
						autoInfo = this.crawlRow(row,name);
				}
			}
		} catch (IOException e) {
			System.err.println("For '" + URL + "': " + e.getMessage());
		}
		return autoInfo;
	}
	
	/**
	 * This method will return all the details of all the automobiles after crawling the URL
	 * @author Bijaya
	 * @return
	 */
	public List<AutomobileInfo> getAutomobileDetails()
	{
		List<AutomobileInfo> carList = new ArrayList<AutomobileInfo>();
		List<AutomobileInfo> distinctList = null;
		try {
			Document doc = Jsoup.connect(URL).get();
			Elements tables = doc.select("table > tbody");
			for(Element table : tables)
			{
				Elements rows = table.select("tr");
				for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
					Element row = rows.get(i);
					if(!StringUtils.isEmpty(this.extractNames(row)))
						   carList.add(this.crawlRow(row));
				}
			}
			distinctList = carList.stream().distinct().collect(Collectors.toList());
		} catch (IOException e) {
			System.err.println("For '" + URL + "': " + e.getMessage());
		}
		return distinctList;
	}
	
	/**
	 * This method will return all the unique/distinct automobile names after crawling the URL
	 * @author Bijaya
	 * @return
	 */
	public SortedSet<String> getAutomobileNames()
	{
		SortedSet<String> carList = new TreeSet<String>();
		try {
			Document doc = Jsoup.connect(URL).get();
			Elements tables = doc.select("table > tbody");
			for(Element table : tables)
			{
				Elements rows = table.select("tr");
				for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
					Element row = rows.get(i);
					if(!StringUtils.isEmpty(this.extractNames(row)))
					   carList.add(this.extractNames(row));
				}
			}
		} catch (IOException e) {
			System.err.println("For '" + URL + "': " + e.getMessage());
		}
		return carList;
	}
	
	
	private String extractNames(Element row)
	{
		String car = null;
		Elements ths = row.select("th");
		if (ths != null && ths.size() == 1) {
			Element carName = ths.get(0);
			car = carName.text();
		}
		if (ths != null && ths.size() == 2) {
			Element carName = ths.get(1);
			car = carName.text();
		}
		return car;
	}
	
	private AutomobileInfo crawlRow(Element row)
	{
		String car = null;String manufacturer = null;
		AutomobileInfo carDetails = new AutomobileInfo();
		Elements ths = row.select("th");
		if (ths != null && ths.size() == 1) {
			Element carName = ths.get(0);
			car = carName.text();
		}
		if (ths != null && ths.size() == 2) {
			Element carName = ths.get(1);
			car = carName.text();
			manufacturer = ths.get(0).text();
		}
		
		carDetails.setName(car);
		if(!StringUtils.isEmpty(manufacturer))
		{
			carDetails.setManufacturer(manufacturer);
		}
		Elements images = row.select("td > div.center > div.floatnone > a > img");
		for (Element a : images) {
			carDetails.setImageSource(a.attr("src"));
		}

		Elements tds = row.select("td");
		if (tds != null && tds.size() == 3) {
			for (int k = 0; k < tds.size(); k++) {
				carDetails.setProdYear(tds.get(1).text());
				carDetails.setSales(tds.get(2).text());
			}
		}
		if (tds != null && tds.size() == 4) {
			for (int k = 0; k < tds.size(); k++) {
				carDetails.setProdYear(tds.get(1).text());
				carDetails.setSales(tds.get(2).text());
				carDetails.setAssembly(tds.get(3).text());
			}
		}
		if (tds != null && tds.size() == 5) {
			for (int k = 0; k < tds.size(); k++) {
				carDetails.setProdYear(tds.get(1).text());
				carDetails.setSales(tds.get(2).text());
				carDetails.setNotes(tds.get(3).text());
				carDetails.setAssembly(tds.get(4).text());
			}
		}
		return carDetails;
	}
	
	private AutomobileInfo crawlRow(Element row,String name)
	{
		String car = null;String manufacturer = null;
		AutomobileInfo carDetails = new AutomobileInfo();
		Elements ths = row.select("th");
		if (ths != null && ths.size() == 1) {
			Element carName = ths.get(0);
			car = carName.text();
		}
		if (ths != null && ths.size() == 2) {
			Element carName = ths.get(1);
			car = carName.text();
			manufacturer = ths.get(0).text();
		}
		
		carDetails.setName(car);
		if(!StringUtils.isEmpty(manufacturer))
		{
			carDetails.setManufacturer(manufacturer);
		}
		Elements images = row.select("td > div.center > div.floatnone > a > img");
		for (Element a : images) {
			carDetails.setImageSource(a.attr("src"));
			
			carDetails.setImage(this.getImageFromSrc("https:"+a.attr("src")));
		}

		Elements tds = row.select("td");
		if (tds != null && tds.size() == 3) {
			for (int k = 0; k < tds.size(); k++) {
				carDetails.setProdYear(tds.get(1).text());
				carDetails.setSales(tds.get(2).text());
			}
		}
		if (tds != null && tds.size() == 4) {
			for (int k = 0; k < tds.size(); k++) {
				carDetails.setProdYear(tds.get(1).text());
				carDetails.setSales(tds.get(2).text());
				carDetails.setAssembly(tds.get(3).text());
			}
		}
		if (tds != null && tds.size() == 5) {
			for (int k = 0; k < tds.size(); k++) {
				carDetails.setProdYear(tds.get(1).text());
				carDetails.setSales(tds.get(2).text());
				carDetails.setNotes(tds.get(3).text());
				carDetails.setAssembly(tds.get(4).text());
			}
		}
		return carDetails;
	}
	
	private byte[] getImageFromSrc(String url)
	{
		byte[] imageBytes = null;
		InputStream is = null;
		try {
		  is = new URL(url).openStream ();
		 imageBytes = IOUtils.toByteArray(is);
		}
		catch (IOException e) {
		  System.err.printf ("Failed while reading bytes from", e.getMessage());
		  e.printStackTrace ();
		}
		finally {
		  if (is != null) { try {
			is.close();
				} catch (IOException e) {
					e.printStackTrace();
				} }
		}
		return imageBytes;
	}

}
