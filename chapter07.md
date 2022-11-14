7 Finding hidden issues using profiling techniques

This chapter covers
Sampling an app’s execution to find the currently executing methods
Observing execution times
Identifying SQL queries the app executes


In chapter 6, I said a profiler is a powerful tool that can show you a path when all the lights have gone out (making an analogy here with the light of Earendil, which Galadriel gives Frodo in the epic Lord of the Rings novel by J.R.R Tolkien). Still, I’ve only shown you a small part yet. The profiler offers powerful techniques for investigating an app’s execution, and learning to use these techniques properly helps you in many scenarios.

In many cases, I have had to evaluate or investigate app executions for codebases I could barely read – old apps without even a code design kept hidden in a wardrobe by companies. In such cases, the profiler was the only efficient way to find what was executing when a specific capability was triggered. You understand now why I compared the profiler with the light of Earendil. Because as Galadriel says, it really was a light in many dark places where all the other lights were out for me.

In this chapter, we’re going to analyze three investigation techniques through profiling which I consider to be extremely valuable:

1.Sampling for finding out what part of an app’s code executes
2.Profiling the execution (also named sometimes “instrumentation”) to identify wrong behavior and optimization
3.Profiling the app to identify SQL queries an app uses to communicate with a Database Management System (DBMS)

We’ll then continue our discussion in chapter 8 with advanced visualization techniques of an app’s execution. When used appropriately, these techniques can save you tremendous time spent finding the causes of various issues. Unfortunately, even if these techniques are powerful, with experience, I observed many developers are unfamiliar with them. Some developers know these techniques exist but tend to believe they are difficult to use (I’ll show you the contrary in this chapter). Consequently, many developers try using other methods to solve problems that could be solved much more efficiently with a profiler (as presented in this chapter).

To make sure you properly understand how to use these techniques and what issues can be investigated, I created four small projects. We’ll use these projects to apply the profiling techniques we discuss. Section 7.1 discusses sampling – a technique you use to identify what code executes at a given time. In section 7.2, you’ll learn how to profile for more details about the execution than sampling can offer. Section 7.3 discusses how to use a profiler to get details about SQL queries an app sends to a DBMS.


7.1Sampling to observe executing code

What is sampling, and how can it benefit you? Sampling is an approach in which you use a profiler to identify what code the app executes. Sampling doesn’t provide many details about the execution, but it draws the big picture of what happens, giving you valuable information on what you’ll need to analyze further. For this reason, sampling should always be the first step when profiling an app, and, as you’ll observe with our discussion, sampling may even be enough in many cases. For this section, I prepared project da-ch7-ex1. We’ll use a profiler to sample this app and understand how we would use VisualVM to identify issues related to the execution time of a given capability.

The project we’ll use to demonstrate sampling is a tiny app that exposes an endpoint /demo. When someone calls this endpoint, using cURL, Postman, or a similar tool, the app further calls an endpoint exposed by httpbin.org.

I like a lot using the site httpbin.org for many examples and demonstrations. Httpbin.org is an open-source web app and tool written in Python that exposes mock endpoints you can use to test different things you’re implementing. In the current case, we call an endpoint where httpbin.org responds with a given delay. We’ll use a 5-second delay for this example. We’ll use this simple example to simulate a latency scenario in our app, and httpbin.org simulates the root cause of the problem.

***

The scenario is also visually represented in figure 7.1.

***


The profiling approach of an app has two steps:

1.Sampling to find out what code executes and where you should go more in detail (the approach we discuss in this section).
2.Profiling (also named “instrumentation” sometimes) to get more details about the execution of specific pieces of code.


Sometimes, step 1 (sampling) is enough to understand everything you need about a problem. So you might have cases where you don’t necessarily need to get more details by profiling the app. As you’ll learn in this chapter and chapters 8 through 10, step 2 (profiling) can give you more details about the execution if you need. But you need to know what part of the code to profile before, and for that, you use sampling.

How does the problem occur in our example? When calling the /demo endpoint, the execution takes 5 seconds (figure 7.2), which we consider too long. Suppose that ideally, we would like the execution to take less than 1 second. We want to understand why calling the /demo endpoint takes so long – who causes the latency? It’s our app, or something else that causes it to wait for this amount of time? Suppose, of course, that you don’t already know the cause.

In similar cases, when you want to investigate a slowness problem in an unknown code base, using a profiler should be your first choice. The problem doesn’t necessarily need to involve an endpoint. For this example, an endpoint was the easiest solution to implementing a simple app that allows you to focus on the investigation technique we discuss. But any situation where something is slow: calling an endpoint, executing a process, or a simple method call upon a particular event classify in this range of issues for which you should choose a profiler as your first option.

***

We start the app and then VisualVM (the profiler we use for our investigations). Remember to add the VM option -Djava.rmi.server.hostname=localhost, as we discussed in chapter 6. That allows VisualVM to connect to the process. You select the process from the list on the left and then select the Sampler tab as presented in figure 7.3 to start sampling the execution.

***

You sample the execution for three purposes:

1.Find out what code executes – sampling shows you what executes behind the scenes to it’s an excellent way to find out what part of the app your need to investigate when you don’t know exactly what happens during execution.
2.Identify CPU consumption – this is what we’ll use to investigate latency issues and understand how methods share the execution time
3.Identify memory consumption – to analyze memory-related issues. We’ll discuss more sampling and profiling memory in chapter 11.

Select CPU (as shown in figure 7.4) to start sampling for performance data. Doing so, VisualVM displays a list of all the active threads and their stack traces. The profiler now intercepts the process execution and displays all the methods called and the approximate execution time. When you call the /demo endpoint, the profiler shows what happens behind the scenes while the app executes that capability.

Figure 7.4 The profiler shows all the active threads in a list. You can expand each item to see the execution stack and an approximate execution time. While the app executes, the newly created threads will appear in the list, and you can analyze their execution.
***

We can now call the /demo endpoint and observe what happens. As also shown in figure 7.5, some new threads appear in the list. The app started these threads when we called the /demo endpoint. Opening them, you should observe precisely what the app does during its execution.

Before I go even deeper and discuss details such as the execution time, I want to highlight how vital this part already is. In many situations where I had to analyze code, I only used sampling to figure out where to look. I might not even have to investigate a performance or latency issue, but just find out where to start debugging. Remember our discussions in chapters 2 through 4. To debug something, you need to know where to add that breakpoint to pause the app’s execution. Suppose you have no clue where to add a breakpoint – then you can’t debug. Sampling can be a way to shed some light in a situation where you can’t figure out where to start debugging (especially in cases like the ones in my story at the beginning of the chapter about apps lacking completely a code design).

Figure 7.5 The stack traces show what the app executes. You observe every method and what other method it called further. This view helps you quickly find the code you focus on when investigating an issue on a certain capability.
***

Let’s have a look now at the execution stack and understand what the profiler shows us. When you want to figure out what code executes, you simply expand the stack trace up to the point where it shows the methods of the app you are interested in. When you investigate a latency problem (as in this example), you expand the stack trace following the maximum execution time. In figure 7.6, you can observe how the profiler shows the execution time.

I have expanded the execution stack by selecting the small (+) button to the last method. The tool shows the time spent was about 5 seconds to understand the execution and find which method causes the latency.

 In this particular case, we observe that only one method causes the slowness. This method is getResponseCode() of the HttpURLConnection class.
 
 ***
 
 An important aspect of this example is that the CPU time (how long the method works) is 0, even if the method spends 5 seconds in execution. The reason is that the method waits for something, and it doesn’t spend CPU resources. This method waits for the HTTP call to end and to get a response. We can conclude that the problem is not in the app, since it’s slow only because it waits for a response for its HTTP request.

It’s extremely valuable to differentiate between the total CPU time and the total execution time. If a method spends CPU time, it means the method “works”, so to improve the performance in such a case, you’d usually have to adjust (if possible) the algorithm to minimize its complexity. If the execution spends a small CPU time, but has a large execution time, the method waits for something instead. So it can be that an action takes a long time, but the app doesn't do anything.In such a case, you need to figure out what your app is waiting for.

Figure 7.6 Expanding the execution stack, you find which methods execute and how much time they spend executing. You can also deduce how much time they wait and how much they do work. The profiler shows both the app’s codebase methods and the methods called from specific dependencies (libraries or frameworks) the app uses.
***


Another essential aspect to observe is that the profiler doesn’t only intercept your app’s codebase. You can see here also the dependencies’ methods that are called during the app’s execution. In this example, the app uses a dependency named OpenFeign to call the httpbin.org endpoint. You can observe in the stack trace packages that don’t belong to your app’s codebase. These packages are part of the dependencies your app uses to implement its capabilities. OpenFeign can be one of them, like in this example.

OpenFeign is a project part of the Spring ecosystem of technologies that a Spring app can use to call REST endpoints. Since this example is a Spring app, you will find packages of Spring-related technologies in the stack trace. No worries, you don’t have to understand what each part of the stack trace does. Neither you’ll know in a real-world scenario in all the cases. In fact, this book is about understanding the code that you don’t know yet, and, as discussed starting with chapter 1, one of the ways of using the techniques you learn in this book is helping you learn faster a new technology. On the other hand, if you want to learn Spring, I recommend starting with Spring Start Here (Manning 2021), another book I wrote. You’ll also find in Spring Start Here details about using OpenFeign.

But, why is this point of observing dependencies’ methods so important? Because sometimes, it’s almost impossible to figure out what executes from a given dependency using other means. Take a look at the code written in our app to call the httpbin.org endpoint (listing 7.1). You can’t see anywhere the actual implementation for sending the HTTP request. That’s because, as it happens in many Java frameworks today, the dependency uses dynamic proxies to decouple the implementation.


Listing 7.1 The HTTP client implementation using OpenFeign

<code>

    @FeignClient(name = "httpBin", url = "${httpBinUrl}")
        public interface DemoProxy {
 
        @PostMapping("/delay/{n}")
        void delay(@PathVariable int n);

    }

</code>


Dynamic proxies give an app a way to choose a method implementation at runtime. When an app capability uses dynamic proxies, it might actually call a method declared by an interface without knowing what implementation it will be given to execute at runtime (figure 7.7).

It is easier to use the framework's capabilities, but the disadvantage is that you don’t know where to investigate an issue.

Figure 7.7 The framework keeps the implementations for an abstraction separate and provides them dynamically during execution. Because the implementation is decoupled and the app provides it during runtime, it’s more difficult to find it by reading the code.
***

***

7.2 Profiling to learn how many times a method executed
Finding what code executes is essential, but sometimes not enough. Often, we need to get more details to understand precisely a given behavior. For example, one of the essential details you’ll miss when sampling is the number of method invocations. You might have a method that takes only 50 milliseconds to execute, but if the app calls it a thousand times, then you will observe it takes 50 seconds to execute when sampling. To demonstrate getting these details about the execution with a profiler and situations where this is useful, we’ll again use some projects provided with the book.

We’ll start with project da-ch7-ex1, which we also used in section 7.1, but this time to discuss profiling for details about the execution.

Start the app provided with project da-ch7-ex1. One crucial detail is that when you profile an app, you shouldn’t investigate the entire codebase. Instead, you need to filter only on what’s essential to your investigation. Profiling is a very resource-consuming operation, so trying to profile everything unless you have a really powerful system would take a ton of time. That’s one more reason why we always start with sampling – to identify what exactly to profile further if needed.

***

For this example, we’ll ignore the app’s codebase (without dependencies) and only take OpenFeign classes from the dependencies. Be aware that you can’t usually refer to all the app’s code in a real-world app since the app might be extensive. For this small example, won’t be a problem but for large apps always restrict to as much as possible the intercepted code when profiling.

In figure 7.8, you find out how to apply these restrictions. On the right side of the Profiler tab, you can specify which part of the app is intercepted. In this example, we use

-com.example.** - meaning code in all the packages and subpackages of com.example
-feign.** – meaning code in all the packages and subpackages of feign

The syntax you can use to filter the packages and classes you want to profile has just a few simple rules:

1.Write each rule on a separate line.
2.Use one * to refer to a package – for example, we could have used com.example.* if we wanted to mean that we want to profile all classes in package “com.example”.
3.Use two * to refer to a package and all its subpackages – as we do in this case, using com.example.** we mean all classes in package “com.example” as well as any of its subpackages.
4.Write the full name of a class if you want to profile only that class – for example, we could have used com.example.controllers.DemoController to only profile this class.

I chose these packages after sampling the execution as discussed in section 7.1. Because I observed that the call of the method with the latency problem comes from classes of the feign package, I decided to add this package and its subpackages to the list, to get more info about it.

Figure 7.8 Profiling a part of the app during execution to get details about the times a given method was invoked. We observe that the method causing the 5 seconds latency is invoked only once, meaning the number of invocations doesn’t cause a problem here.

***

In this particular case, the number of invocations doesn’t seem to cause issues – the method executes only once and takes about 5 seconds to finish its execution. A small number of method invocations implies at least that we don’t have repeated unnecessary executions (which, as you’ll learn later in this chapter is a common problem in many apps).

In another scenario, you might have observed that the call to the given endpoint only takes 1 second, but the method is (because of some lousy design) called five times. Then, the problem would have been in the app, and we knew what we needed to solve and where. In section 7.3, we’ll also analyze such a problem having as a cause multiple calls to a method.


7.3 Using a profiler to identify SQL queries an app executes
In this section, you’ll learn how to identify SQL queries an app sends to a DBMS using a profiler. This subject is by far one of my favorites. Today, almost every app uses at least one relational database, and almost all scenarios encounter latencies caused by SQL queries from time to time. Moreover, the apps use fancier ways to implement the persistence layer today: in many cases, the SQL queries the app sends are created dynamically by a framework and a library. These dynamically generated queries are hard to identify, but a profiler can do some magic and simplify your investigation a lot.

We’ll use a scenario implemented with project da-ch7-ex2. This app runs some queries on a relational database. With this example, you’ll learn how to observe how many times a method executes and intercepts a SQL query the app runs. We’ll then demonstrate that the executed SQL queries can be retrieved even when the app works with a framework and doesn’t handle the queries directly – we’ll discuss this subject later with a couple of examples.

7.3.1 Using a profiler to retrieve SQL queries not generated by a framework

p1
p2

In figure 7.9, we directly use the profiler tab since you already learned sampling in section 7.1, but remember that in any real-world scenario, you start with sampling. We start the app, and using cURL or Postman, and we call the /products endpoint. The profiler shows us precisely what happens when calling the /products endpoint:

1.A method findPurchsedProductNames() that belongs to the PurchaseController class was called.
2.This method delegated the call to a method getProductNamesForPurchases() in class PurchaseService.
3.The method getProductNamesForPurchases() in ProductService calls findAll() in PurchaseRepository.
4.The method getProductNamesForPurchases() in ProductService calls findProduct() in ProductRepository 10 times.

Figure 7.9 Profiling the app, we observe that one of the methods is called 10 times. We now need to ask ourselves if this is a design issue. We have the big picture over the entire algorithm, and since we know what code is executed, we could also debug it if we can’t figure out straightforwardly what happens.

***

Isn’t this amazing? We didn’t even look into the code, and we already know a lot of things about this execution. These details are fantastic, because now, you know exactly where to go into the code and also what you expect to find. The profiler gave you class names, method names, and how they call each other. Let’s now take a look into the code and figure out where all this happens. Using the profiler, we can see that most things happen in the getProductNamesForPurchases() method in PurchaseService class, so that’s most likely the place we need to analyze. Listing 7.2 shows the PurchaseService class that contains the algorithm’s implementation.

Listing 7.2 The algorithm’s implementation in the PurchaseService class
<code>
@Service
public class PurchaseService {
 
  private final ProductRepository productRepository;
  private final PurchaseRepository purchaseRepository;
 
  public PurchaseService(ProductRepository productRepository,
                         PurchaseRepository purchaseRepository) {
    this.productRepository = productRepository;
    this.purchaseRepository = purchaseRepository;
  }
 
  public Set<String> getProductNamesForPurchases() {
    Set<String> productNames = new HashSet<>();
    List<Purchase> purchases = purchaseRepository.findAll();
    for (Purchase p : purchases) {
      Product product =
        productRepository.findProduct(p.getProduct());
      productNames.add(product.getName());
    }
 
    return productNames;
  }
}
</code>

Observe the implemented behavior: the app fetches some data in a list and then iterates over it to get more data from the database. Whenever I find such an implementation where some data is retrieved from a database table, the app iterates over it and gets more data from another table usually indicates a design issue. The reason is you can usually reduce the execution of so many queries to only one. Obviously, the fewer queries executed, the more efficient the app is.

In this example, it’s effortless to retrieve the queries directly from the code. Since the profiler shows us exactly where they are, and the app is tiny, it won’t be a problem finding the queries. But real-world apps are not small, and in many cases, it’s not easy to retrieve the query from the code directly. But fear no more! You can use the profiler to retrieve all the SQL queries the app sends to a DBMS.

You find this demonstration in figure 7.10. Instead of selecting the CPU button, you now select the JDBC button to start profiling for SQL queries.

Figure 7.10 The profiler intercepts the SQL queries the app sends to the DBMS through the JDBC driver. This capability provides you an easy way to get the queries and run them, observe what part of the codebase runs them and how many times a query is executed.

***

What the tool does behind the scenes is pretty simple. Any Java app sends the SQL queries to a DBMS through a JDBC driver. The profiler intercepts the driver and copies the queries before the driver sends them to the Database Management System (DBMS). Figure 7.11 shows visually this approach. The result is fantastic, as you can simply copy-paste the queries in your database client where you can run them or investigate their explain plan.

Figure 7.11 In a Java app, the communication with a relational DBMS is done through the JDBC driver. A profiler can intercept all method calls, including those of the JDBC driver, and retrieve the SQL queries the app sends to a DBMS. You can get the queries and use them in your investigations.

***

The profiler also shows you how many times a query was sent. In this case, the app sent the first query ten times. This design is definitely a faulty one since it repeats the same query multiple times, spending unnecessary time and resources. The developer who implemented the code tried to obtain the purchases and then get the product details for each purchase. But a straightforward query with a join between the two tables (“product” and “purchase”) would have solved the problem in one step. Fortunately, using VisualVM, you identified the cause, and you know exactly what to change to improve this app.

Figure 7.12 shows you how to find the part of the codebase that sent the query. You can expand the execution stack and usually find the first method in the app’s codebase.

Figure 7.12 For each query, the profiler also provides the execution stack trace. You can use the stack trace to identify which part of your app’s codebase sent the query.

***

Listing 7.2 shows the code behind the scenes whose call we identified using the profiler. Once you identify where the problem comes from, it’s time to read the code and find a way to optimize the implementation. In this example, everything could have been merged in only one query. It may look like a dummy mistake, but trust me, you’ll find such cases even in larger apps implemented by powerful organizations.

Listing 7.2 The algorithm’s implementation in the ProductService class
<code>
@Service
public class PurchaseService {
 
  // Omitted code
 
  public Set<String> getProductNamesForPurchases() {
    Set<String> productNames = new HashSet<>();
    List<Purchase> purchases = purchaseRepository.findAll();
    for (Purchase p : purchases) {
      Product product = productRepository.findProduct(p.getProduct());
      productNames.add(product.getName());
    }
    return productNames;
  }
}
</code>

Example da-ch7-ex2 is using JDBC to send the SQL queries to a DBMS. The app has the SQL queries in their native shape directly in the Java code (listing 7.3), so, somehow, you could say that copying the queries directly from the code is not that difficult. But, in today’s apps, you’ll encounter less often the use of native queries in the code. Today, many apps use frameworks such as Hibernate (the most used Java Persistence API [JPA] implementation) or Java Object Oriented Querying (JOOQ). You find more details about JOOQ on their GitHub repository here: https://github.com/jOOQ/jOOQ. What’s different with these frameworks is that you won’t find the native queries directly in the code anymore (native queries can be used with these frameworks, but it’s quite unusual and mostly not recommended to use them).

Listing 7.3 The repositories use native SQL queries
<code>
@Repository
public class ProductRepository {
 
  private final JdbcTemplate jdbcTemplate;
 
  public ProductRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
 
  public Product findProduct(int id) {
    String sql = "SELECT * FROM product WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id);
  }
}
</code>

7.3.2 Using the profiler to get the SQL queries generated by a framework
Now, let me show you something even more extraordinary. To prove the usefulness of a profiler even more in investigating SQL queries, let’s investigate project da-ch7-ex3. From an algorithm point of view, this project does the same thing as the previous project did – returns the name of the purchased products. I intentionally kept the same logic to simplify understanding the example and be able to make a comparison.

Take a look at listing 7.4, which shows the definition of a Spring Data JPA repository. The repository is now a simple interface, and you don’t find the SQL queries anywhere. With Spring Data JPA, the app generates the queries behind the scenes either based on method’s names or on a particular way to define the queries, named Java Persistence Query Language (JPQL), based on app’s objects. Either way, there’s no simple way to copy-paste the query from the code anymore.

Some frameworks generate the SQL queries behind the scenes based on the code and configurations you write. In these cases, it's particularly more challenging to get the executed queries. But a profiler helps you get the queries by extracting them from the JDBC driver before they are sent to the DBMS.

Listing 7.4 The Spring Data repository doesn’t use native SQL queries
<code>
public interface ProductRepository
    extends JpaRepository<Product, Integer> {
}
</code>

But the profiler comes to the rescue. Since the tool intercepts the queries before the app sends them to the DBMS, we can still use it to find exactly what queries the app uses. Start app da-ch7-ex3 and use VisualVM to profile the SQL queries same as we did for the previous two projects.

Figure 7.13 shows you what the tool displays when profiling the /products endpoint call. The app sent two SQL queries. Observe that the aliases in the query have strange names because the queries are framework generated. Also, observe an interesting aspect: even if the logic in the service is the same, and the app calls the repository method ten times, the second query is executed only once. This behavior happens because Hibernate optimizes the execution where it can. Now you can copy and investigate this query with an SQL development client if needed. In many cases, investigating a slow query requires running it in a SQL client to observe which part of the query puts the DBMS in difficulty.

The query is executed only once even if the method is called 10 times. Do these persistence frameworks usually do these kinds of tricks? Persistence frameworks are smart, but sometimes what they do behind the scenes can also add complexity. Someone not understanding properly the framework could write code that causes problems. This is another reason to use a profiler to check the queries the framework generated and make sure the app does what you expected to happen.

Figure 7.13 Even when working with a framework, the profiler can still intercept the SQL queries. This aspect makes your investigation a lot easier since you can’t copy the query from the code directly as you may do when directly using JDBC and native queries.
***

The issues that I mostly encountered with frameworks and had to investigate are:

Slow queries causing latencies – easy to spot using the execution time with a profiler.
Multiple unneeded queries generated by the framework (usually caused by what the developers call the N+1 query problem) – easy to spot using the number of execution of a query with a profiler.
Long transaction commits generated by poor app design - easy to spot using CPU profiling.
When a framework needs to get data from multiple tables, it usually knows to compose one query and get all the needed data in one call. However, if you don't correctly use the framework it might take only a part of the data with an initial query, and, then, for each record initially retrieved it runs a separate query. So, instead of running only one query, the framework will send an initial one plus N others (one for each of the N records retrieved by the first) – we call this an N+1 query problem. An N+1 query problem usually causes significant latency by executing many queries instead of only one.

I observed that most developers seem to be tempted to work with logs or the debugger for investigating such problems. But also, in my experience, logs or the debugger are not the best options for such problems to identify the problem’s root cause.

The first problem of using the logs for this case is that it’s challenging to identify which query causes a problem. In real-world scenarios, the app may send dozens of queries – some of these multiple times and in most cases long and using a large number of parameters. With the profiler, which displays all the queries in a list together with their execution time and the number of executions, you can almost instantaneously spot the problem in most cases. The second difficulty is that even if you identify the potential query causing the problem (say you observe a query the app takes long to execute a given query while monitoring the logs), it’s not straightforward taking the query and running it. In the log, you’ll find parameters separated from the query.

You can configure your app to print the queries generated by Hibernate in the logs by adding some parameters to the application.properties of app da-ch7-ex3 file as shown in the next snippet.
<code>
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.type.descriptor.sql=trace
</code>

