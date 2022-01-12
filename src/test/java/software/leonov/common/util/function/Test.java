package software.leonov.common.util.function;

import static software.leonov.common.util.function.CheckedFunction.evalUnchecked;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) throws IOException {
        
        
        Stream.of("https://www.google.com").map(t -> {
            try {
                return new URL(t);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });
        
        Stream.of("https://www.google.com").map(evalUnchecked(URL::new));
        
    }

}
