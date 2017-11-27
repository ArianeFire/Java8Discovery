import java.util.*;
import java.util.stream.*;
import java.util.stream.Collectors.*;
import static java.util.stream.Collectors.*;

class Sample {


	public static String getTriple(int m, int n){
		
		int a  = m*m - n*n;
		int b = 2*m*n;
		int c = m*m + n*n;
		
		return String.format("%d %d %d", a, b, c);
	}
	
	public static boolean isPrime(int num) {
	        if (num < 2) return false;
	        if (num == 2) return true;
	        if (num % 2 == 0) return false;
	        for (int i = 3; i * i <= num; i += 2)
	            if (num % i == 0) return false;
	        return true;
	}

	public static void main(String[] args){
	
		System.out.println("Refactoring to Functional Style with Java 8");
		
												//<<<<<<<<<<<<---------------------------------->>>>>>>>>
		//Exercie 3 : Group 
		Map<String, Integer> scores = new HashMap<String, Integer>();
		
		scores.put("Seydou1", 15);
		scores.put("Seydou2", 15);
		scores.put("Bourama1", 12);
		scores.put("Bourama2", 12);
		scores.put("Chaka1", 14);
		scores.put("Chaka2", 14);
		
		/** GROUP BY SCORE **/
		System.out.println(scores.keySet().stream().collect(Collectors.groupingBy(scores::get)));
	
	
												//<<<<<<<<<<<<---------------------------------->>>>>>>>>
												
		//Exercie 4 : Decorator ==> Light Weigth Pattern with Lambda Expression (Fnctionnal Interface)  
		
												//<<<<<<<<<<<<---------------------------------->>>>>>>>>
												
		//Exercie 5 : Pyhagorean triples => Given A Numbers N, we want to find N Pythagoren Triples   
		
		 Stream.iterate(2, e -> e + 1)
			   .flatMap(m ->  /** USE WHEN ONE INPUT CAN PRODUCE MULTIPLE OUTPUT => ONE TO   **/
				   		IntStream.range(1, m)
				                 .mapToObj(n -> getTriple(m, n))
				  )
			 .limit(10)
		     .collect(Collectors.toList())
			 .stream()
			 .forEach(value -> System.out.println(value));
		
												//<<<<<<<<<<<<---------------------------------->>>>>>>>>
												
		//Exercice 6 : Sum of sqrt of k primes starting with n :
		
		System.out.println(
		Stream.iterate(2, e -> e + 1)
			  .filter(Sample::isPrime)
			  .mapToDouble(Math::sqrt)
			  .limit(10)
			  .sum()
		);
		
												//<<<<<<<<<<<<---------------------------------->>>>>>>>>
		//Exercise 7 : Pyramid In Java 8
		System.out.println(
		Stream.iterate(2, e -> e + 1)
			  .flatMap(m -> IntStream.range(0, m)
				  					 .mapToObj(n -> m + n)
				  )
			.limit(3)
		    .collect(Collectors.toList())
		);	
		
								 				//<<<<<<<<<<<<---------------------------------->>>>>>>>>
		
		// Default Method : Method with it implementation in a "interface" (Possible cause Interface didn't have state) => No Diamond Problem
		// We can add getState() "Implemented by the class which implement the 'interface'", then call this method inside default method to get there actual State
		
		// Four rules  of Default methods
		// 1. You get what is in the base interface
		// 2. You may override default method
		// 3. If a method is there in the class hierarchy then, it takes precedence (Take the Last Overriding method in the hierarchy)
		// 4. If there is no method on any of the classes in the hierarchy, 
			//but two of your interfaces that you implements has the default method
			// Solve by using Rule 3.
		
		useFly();
		
											//<<<<<<<<<<<<---------------------------------->>>>>>>>>
											
		
		//Immutability Give Us :
			// --> Referential transparency
			// --> memorization (Stock Result for reusability)
			// --> Easy and Safe parallization
			// --> Lazy 
				// --> Efficient
				// --> Infinite Stream 
					// Example : Total SQRT of even number from n to k
					int n = 10;
					int k = 20;
					System.out.println(
					Stream.iterate(n, e -> e + 1)
						  .filter(e -> e % 2 == 0)
						  .mapToDouble(Math::sqrt)
						  .limit(k)
						  .sum());
					
		
											//<<<<<<<<<<<<---------------------------------->>>>>>>>>
											
		
		//Sorting : 
		List<Personne> people = createPersonnes();
		
		// Way 1 :
		//System.out.println(
		//	Collections.sort(people) //Not Good : cause Personne class need to implements "Comparator" interface
		//);
		
		//Way 2
		//printSorted(people, )
		
		
	}
	
	public static void printSorted(List<Personne> people, Comparator<Personne> comparator){
		
		/*people.stream()
			  .sorted(comparator)
			  .forEach(System.out.println);*/
	}
	
	public static void useFly(){
		
		// RULE 1 Proof
		SeaPlane seaPlane = new SeaPlane();
		seaPlane.land();
		
		//RULE 2 PROOF
		seaPlane.cruise();
		
		//RULE 3 PROOF
		Vehicule v = new Vehicule();
		v.land();
		
		//RULE 4 Proof
		v.turn();
		
	}
	
	public static List<Personne> createPersonnes(){
		return Arrays.asList(
			new Personne("Sara", Gender.FEMALE, 20),
			new Personne("Sara", Gender.FEMALE, 22),
			new Personne("Bob", Gender.MALE, 20),
			new Personne("Paula", Gender.FEMALE, 32),					
			new Personne("Paul", Gender.MALE, 32),
			new Personne("Jack", Gender.MALE, 2),
			new Personne("Sara", Gender.MALE, 72),
			new Personne("Jill", Gender.FEMALE, 12)
		);
	}
}

interface Fly {
	default void land(){ System.out.println("Fly::land"); };
	default void cruise(){ System.out.println("Fly::cruse"); };
	default void turn(){ System.out.println("Fly::turn"); };
	default void takeOff(){ System.out.println("Fly::takeOff"); /* getState(); */ };
	//int getState();
}

interface FastFly extends Fly {
	
	//RULE 2 
	default void cruise(){ System.out.println("FastFly::cruse"); }
}

interface Sail {
	default void turn(){ System.out.println("Sail::turn"); };
}

class SeaPlane implements FastFly {
	
}

//RULE 3
class Vehicule implements FastFly, Sail {
	
	public void land(){
		System.out.println("Vehicule::land");
	}
	
	//RULE 4 SOLVE
	public void turn(){
		Sail.super.turn(); // "super" is used to distingush "static" or "default" method call
	}
	
}

class Personne {
	
	private String name;
	private Gender gender;
	private int age;
	
	public Personne(String name, Gender gender, int age){
		this.name = name;
		this.gender = gender;
		this.age = age;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Gender getGender(){
		return this.gender;
	}
	
	public int getAge(){
		return this.age;
	}
	
}

enum Gender {
	
	MALE("Male"),
	FEMALE("Female");
	
	private String gender;
	
	Gender(String gender){
		this.gender = gender;
	}
}

