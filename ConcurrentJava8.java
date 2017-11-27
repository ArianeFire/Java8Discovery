import java.util.*;
import java.util.stream.*;
import java.util.stream.Collectors.*;
import static java.util.stream.Collectors.*;
import java.util.concurrent.*;
import java.util.concurrent.CompletableFuture;

class ConcurrentJava8 {
	
	// Stream are lazy : ===========================================================
	// 	It doesn't execute a functin on a collection of data
	//	But it instead executes a collection of function on a piece of data.	   
	//==============================================================================

	public static void main(String[] args){
		// Concurent Nature Problem
		// 1- Speed, 
		// 2- With Collection of data that may be handle together (Speed) => Parallel Stream
		// 
		
		List<Integer> numbers = numbers();
		
		// Sum Double of Odd Integer 
		// (Here the chainning of method is called : Collection Pipeline Pattern)
		System.out.println(
		numbers.stream() // parallelStream()
			.parallel()
			.filter(e -> e % 2 == 0)
			.mapToInt(e-> e * 2)
			.sum()
		);
		
		
		//understandingStreamExecution();
		
		//
		understandingCompletableFeature();
		
 	}
	
	public static void understandingCompletableFeature(){
		
		// Runnable -> void run() "doesn't take any param & doesn't return anything"
		// Solution => Java CompletableFuture = Promise (resolve, reject or pending)
		// 	- 2 channels (data "f1, f2", error "")
		//		func
		// 			.then(f1)
		//			.catch(e1)
		//			.then(f2)
		//			.catch(e2)
		
		// Example of CompletableFuture
		CompletableFuture
			.runAsync(() -> System.out.println("do work"));
		
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(ConcurrentJava8::generate);
		CompletableFuture<Integer> parametrableFuture = new CompletableFuture<>();
		CompletableFuture<Integer> futureWithException = CompletableFuture.supplyAsync(ConcurrentJava8::generate);
		
		System.out.println(
			future
			//.get()
			.getNow(-1)
		);
		
		future
			.thenApply(data -> data * 2) // = map operation
			.thenAccept(ConcurrentJava8::printTR); // = forEach
			//thenRun(() -> do something at the end).
			
		// future state
		System.out.println(future
			.isDone()
			//.isCanceled()	
		);
			
		//Calling parametrable future
		parametrableFuture
			.thenApply(data -> data * 2)
			.thenAccept(ConcurrentJava8::printTR);
		
		parametrableFuture.complete(6); // Launch the future
		
		// CompletableFuture Exception Handling
		futureWithException
			.thenApply(data -> applyWithException(data, false))
			.exceptionally(ConcurrentJava8::handleExeption) // Above 'thenApply' Exception handler : Ignore it doesn't throw exception
			//.thenAccept(ConcurrentJava8::printTR)
			.thenApply(data -> applyWithException(data, true))
			.exceptionally(ConcurrentJava8::handleExeption)  // Above 'thenApply' Exception handler : Ignore it doesn't throw exception
			.thenAccept(ConcurrentJava8::printTR);
		
		System.out.println("In main");
		
		
	}
	
	
	public static int handleExeption(Throwable throwable){
		return 0;
	}
	
	public static int applyWithException(int data, boolean withException){
		if(withException)
			throw new RuntimeException(data + "");
		
		return data * 2;
	}
	
	public static void printTR(Integer v){
		System.out.println(v);
	}
	
	public static int generate(){
		System.out.println("doing work...");
		return 2;
	}
	
	
	public static int transform(int n){
		System.out.println("transform: " + Thread.currentThread());
		return n;
	}
	
	public static void printIt(Integer n){
		System.out.println(n + " " + Thread.currentThread());
	}
	
	public static int add(int total, int e){
		int result = total + e;
		
		System.out.println("Total : " + total + " e: " + e + " result: " + result);
		
		return result;
	}
	
	public static void understandingStreamExecution(){
		
		System.out.println("Parallel Execution on Map Operation Test....");
		numbers().stream()
			.parallel()	// Run Stream in parallel (May loose the order of the List)
			.map(ConcurrentJava8::transform)
			//.forEach(System.out::println);
			.forEachOrdered(ConcurrentJava8::printIt); // Order the result of the parallel Stream
			
			// Parallel is ok for filter & map operations
			
		// Parallel with reduce operation
		System.out.println("Parallel Execution on Reduce Operation Test...");
		System.out.println("Reduce Operation RES : " + 
		numbers().stream()
			.parallel()
			.reduce(0, (total, e) -> add(total, e)) // Here 0 is the identity value (identity op v = v) not initial value
													// This stuation is observed with parallel stream (Change 0 to 2 and see)
		);
		
		// NB : Number of Thread must be <= to the number of Cores of the machine (Else it will cause performance issue)
		// Number of operation on a List = (Number of Element / Number of available processors)
		System.out.println("Available Processors : " + Runtime.getRuntime().availableProcessors());
		
		// Specify the number of Thread to create programmatically
		//ForkJoinPool pool = new ForkJoinPool(50);
		//pool.submit(() -> {});
		//pool.shutdown();
		//pool.awaitTermination(...)
		
		// Why Parallel not always Fast ? 
		// When Parallel make no sense ?
			// Size of the collection ? Very Big => parallel OK / Small => parallel KO
			
		// Stream are lazy :
		// 	It doesn't execute a functin on a collection of data
		//	But it instead executes a collection of function on a piece of data
		
		// Parallel in finding one => use findAny as 'terminal' operation
	}
	
	public static List<Integer> numbers(){
		return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	}
}