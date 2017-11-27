import java.util.Collections;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;


public class SampleCollections {

	public static void main(String[] args){
		
		/** 1. Collections Initialization **/
		
		// 1.0
		List<String> lvStringList = Collections.EMPTY_LIST;
		Map<String, String> lvMapString = Collections.EMPTY_MAP;
		Set<String> lvStringSet = Collections.EMPTY_SET;
		
		// 1.1
		List<Integer> lvInts = new ArrayList<>(2);
		lvInts.add(3);
		lvInts.add(4);
		lvInts.add(5);
		
		System.out.println("Size : " + lvInts.size());
		
		// 1.2 : Get Collections From Arrays
		int[] numbers = {9, 4, 1, 2, 3};
		String toStringArrays = arrayToString(numbers);
		int sum = Arrays.stream(numbers).sum();
		int max = Arrays.stream(numbers).max().getAsInt();
		int min = Arrays.stream(numbers).min().getAsInt();
		int[] sorted = Arrays.stream(numbers).sorted().toArray();
		int[] inverse = Arrays.stream(numbers).unordered().toArray();
		
		
		System.out.println("List [" + toStringArrays + "]");
		System.out.println("Sum : " + sum);
		System.out.println("Max : " + max);
		System.out.println("Min : " + min);
		System.out.println("Sorted : " + arrayToString(sorted));
		System.out.println("Inverse : " + arrayToString(inverse));
	
	}
	
	public static String arrayToString(int[] arr){
		return Arrays.stream(arr).mapToObj(e -> e + "").collect(Collectors.joining(","));
	}
}