Unchecked Java
===============
Offering the capability to treat checked exceptions as unchecked and write check-exception-friendly streams.

Overview
--------

[Functional Interfaces](https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html) provided in Java 8+ do not throw checked exceptions. As a consequence handling checked exceptions in lambda expressions can be overly cumbersome and verbose.

For example:

```java
Stream.of("https://www.google.com").map(URL::new);
```

Must be coded as:

```java
Stream.of("https://www.google.com").map(t -> {
    try {
        return new URL(t);
    } catch (final MalformedURLException e) {
        throw new RuntimeException(e);
    }
});
```

Besides the extra boiler-plate code, we are also required to obfuscate the original exception by wrapping it in a `RuntimeException` which adds unnecessary bloat to the stack trace. Using **Unchecked Java** we can again write concise code afforded to us by lambda expressions, without wrapping checked exceptions in runtime exceptions:

```java
import static software.leonov.common.util.function.CheckedFunction.evalUnchecked;
...
Stream.of("https://www.google.com").map(evalUnchecked(URL::new));
```

Goals
-----
- Variants of all [Functional Interfaces](https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html) in [java.util.function](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html) which can throw checked exceptions
- Adapter methods to view all checked variants as unchecked
- Java 8 or higher
- **No dependencies** (other than the JDK)
- And more...

Documentation
-------------
The latest API documentation can be accessed [here](https://zleonov.github.io/unchecked-java/apidocs/latest).

WARNING
=======
**Unchecked Java** bypasses Java's exception handling idiom and can lead to horrible errors when misused.

As Brian Goetz (Java Language Architect) put it:

> Just because you don’t like the rules, doesn’t mean its a good idea to take the law into your own hands. Your advice is irresponsible because it places the convenience of the code writer over the far more important considerations of transparency and maintainability of the program.


It is only safe to use if you ensure the caller will catch all possible checked exceptions that could occur. If in doubt <b>do not use</b>.

For further discussion see:

[https://stackoverflow.com/questions/18198176](https://stackoverflow.com/questions/18198176)  
[https://stackoverflow.com/questions/19757300](https://stackoverflow.com/questions/19757300)  
[https://stackoverflow.com/questions/14039995](https://stackoverflow.com/questions/14039995)  
[Unchecked Exceptions — The Controversy](https://docs.oracle.com/javase/tutorial/essential/exceptions/runtime.html)

Similar Libraries
-----------------
- [SneakyThrow](https://github.com/rainerhahnekamp/sneakythrow)
- [throwing-function](https://github.com/pivovarit/throwing-function)
- [jOOλ](https://github.com/jOOQ/jOOL) (org.jooq.lambda.Unchecked)
- [Project Lombok](https://projectlombok.org/features/SneakyThrows)
