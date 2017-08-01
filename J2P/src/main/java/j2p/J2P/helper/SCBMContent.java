package j2p.J2P.helper;

import java.util.Arrays;

public class SCBMContent {
	
	private int size;
	private int length;
	private float allowance = 0.5f;
	
	private String[] underlyingArray;
	
	public SCBMContent(int size) {
		this.size = size;
		underlyingArray = new String[size];
	}
	
	public void add(String item) {
		check();
		underlyingArray[length] = item;
		length++;
	}
	
	public String getAtIndex(int i) {
		return underlyingArray[i];
	}
	
	public void sort() {
		Arrays.sort(underlyingArray);
	}
	
	public int getIndexOf(String s) {
		int x = -1;
		for(int i=0; i<length;i++) {
			if(underlyingArray[i] == s) x = i;
		}
		return x;
	}
	
	private void check() {
		if(needForExpansion()) {
			doubleArray();
		}
	}
	
	private Object[] doubleArray() {
		size = underlyingArray.length * 2;
		Object[] newArray = new Object[size];
		
		for(int i=0;i<underlyingArray.length;i++) {
			newArray[i] = underlyingArray[i];
		}
		
		return newArray;
	}
	
	private boolean needForExpansion() {
		return allowance <= (double)(length)/(double)(size);
	}
	
	public int length() {
		return length;
	}
	
	public int size() {
		return size();
	}
	
}
