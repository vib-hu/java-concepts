package Practice.UrlShortner;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

// Approach -1
// Issue with AtomicInteger use - It can overflow and result in negative number
// and same can happen with Atomic Long

// Approach -2
// Use 7 random numbers between 0-62 and combine them, if collision happen then retry
public class UrlShorter {
    private final ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
    AtomicInteger counter = new AtomicInteger();
    private final String shortBaseUrl = "https://www.example.com/";
    private final String BASE62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private SecureRandom random = new SecureRandom(); //Using SecureRandom, because Random is predictable (can be guessed with enough samples)

    public static void main(String[] args) throws InterruptedException{
        UrlShorter shortner = new UrlShorter();
        CountDownLatch startLatch = new CountDownLatch(1);
        int totalThreads = 5000;
        CountDownLatch doneLatch = new CountDownLatch(totalThreads);

        for(int i=0;i<totalThreads;i++){
            final int index = i;
            new Thread(()->{
                try {
                    startLatch.await();
                    String longUrl = "https://www.game.com/test" + index;
                    String shortUrl = shortner.getShortUrl(longUrl);
                    System.out.println("From Thread: " + Thread.currentThread().getName() + " Long url: " + longUrl + " Short url: " + shortUrl);
                    System.out.println("From Thread: " + Thread.currentThread().getName() + " Get long url:" + shortner.getLongUrl(shortUrl));
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                finally {
                    doneLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        doneLatch.await();

        System.out.println("All threads finished!");
    }

    public String getShortUrl(String longUrl){
        //int num =  counter.incrementAndGet(); // after 2.1 billion calls, it will overflow
        int maxRetries = 10;
        int attempt = 0;
        while(attempt<maxRetries){
            attempt++;
            StringBuilder sb = new StringBuilder(shortBaseUrl);
            for(int i=0;i<7;i++){
                sb.append(BASE62_CHARS.charAt(random.nextInt(62)));
            }
            String shortUrl = sb.toString();
            if(map.putIfAbsent(shortUrl, longUrl)==null){ //This is thread-safe and atomic!
                return shortUrl;
            }
        }
        throw new RuntimeException("Failed to generate unique short URL after " + maxRetries + " attempts");
    }

    public String getLongUrl(String shortUrl){
        if(!map.containsKey(shortUrl))
            throw new RuntimeException("url not found");

        return map.get(shortUrl);
    }

    private String encodeBase62(int num){
        if(num<=0) // What will happen during int overflow?
            return "0";
        StringBuilder sb = new StringBuilder();
        while(num>0){
          int temp = num%62;
          sb.append(BASE62_CHARS.charAt(temp));
          num = num/62;
        }
        return sb.toString();
    }
}
