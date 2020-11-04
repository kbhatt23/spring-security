package com.learning.springsecurity.helloworldsecurity.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OptionalFilterUsage {
public static void main(String[] args) {
	List<Integer> numbers = IntStream.iterate(1, i -> i+1)
									 .limit(10)
									 .collect(ArrayList::new, List::add, List::addAll);
	Optional<Integer> findFirst = numbers.stream()
		   .filter(i -> i > 5)
		   .findFirst();
	
	//map is used for transformation  -> if optionalis empty then it wont go to ransformation and directly goes to orelse case
	int resultIntMap = findFirst.map(i -> i*2)
		     .orElse(-1)
	;
		System.out.println(resultIntMap);
		
		
	//filter is used for filtering  -> output could be 0 or and not moe than that
		
		Integer filterResult = findFirst
				//.map(i -> i*2)
				.filter(i -> i > 6).orElse(-1);
		System.out.println(filterResult);
		
		//in case of size having less than limit it gives full list
		List<Integer> asList = Arrays.asList(1,2,3,4,5);
		
		List<Integer> collect = asList.stream()
			 .limit(20)
			 .collect(Collectors.toList());
		System.out.println("collect "+collect);
}
}
