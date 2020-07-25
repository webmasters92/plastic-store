package rs.dev.plasticstore.config;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhcacheConfig extends CachingConfigurerSupport {

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration cache1 = new CacheConfiguration("product_by_id", 50);
        cache1.setMemoryStoreEvictionPolicy("LRU");
        cache1.setTimeToLiveSeconds(60);

        CacheConfiguration cache2 = new CacheConfiguration("all_products", 1000);
        cache2.setMemoryStoreEvictionPolicy("LRU");
        cache2.setTimeToLiveSeconds(60);

        CacheConfiguration cache3 = new CacheConfiguration("products_by_category", 100);
        cache3.setMemoryStoreEvictionPolicy("LRU");
        cache3.setTimeToLiveSeconds(120);

        CacheConfiguration cache4 = new CacheConfiguration("products_by_subcategory", 100);
        cache4.setMemoryStoreEvictionPolicy("LRU");
        cache4.setTimeToLiveSeconds(120);

        CacheConfiguration cache5 = new CacheConfiguration("product_by_code", 50);
        cache5.setMemoryStoreEvictionPolicy("LRU");
        cache5.setTimeToLiveSeconds(60);

        CacheConfiguration cache6 = new CacheConfiguration("products_by_price_and_category", 300);
        cache6.setMemoryStoreEvictionPolicy("LRU");
        cache6.setTimeToLiveSeconds(120);

        CacheConfiguration cache7 = new CacheConfiguration("products_by_price_and_subcategory", 200);
        cache7.setMemoryStoreEvictionPolicy("LRU");
        cache7.setTimeToLiveSeconds(120);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cache1);
        config.addCache(cache2);
        config.addCache(cache3);
        config.addCache(cache4);
        config.addCache(cache5);
        config.addCache(cache6);
        config.addCache(cache7);

        CacheConfiguration cache8 = new CacheConfiguration("all_categories", 100);
        cache8.setMemoryStoreEvictionPolicy("LRU");
        cache8.setTimeToLiveSeconds(300);

        CacheConfiguration cache9 = new CacheConfiguration("all_subcategories", 100);
        cache9.setMemoryStoreEvictionPolicy("LRU");
        cache9.setTimeToLiveSeconds(300);

        config.addCache(cache8);
        config.addCache(cache9);

        CacheConfiguration cache10 = new CacheConfiguration("all_orders", 300);
        cache10.setMemoryStoreEvictionPolicy("LRU");
        cache10.setTimeToLiveSeconds(300);

        CacheConfiguration cache11 = new CacheConfiguration("orders_by_customer", 20);
        cache11.setMemoryStoreEvictionPolicy("LRU");
        cache9.setTimeToLiveSeconds(120);

        CacheConfiguration cache12 = new CacheConfiguration("orders_by_guest", 20);
        cache12.setMemoryStoreEvictionPolicy("LRU");
        cache12.setTimeToLiveSeconds(120);

        CacheConfiguration cache13 = new CacheConfiguration("order_by_id", 50);
        cache13.setMemoryStoreEvictionPolicy("LRU");
        cache13.setTimeToLiveSeconds(60);

        CacheConfiguration cache14 = new CacheConfiguration("all_colors", 50);
        cache14.setMemoryStoreEvictionPolicy("LRU");
        cache14.setTimeToLiveSeconds(60);

        config.addCache(cache10);
        config.addCache(cache11);
        config.addCache(cache12);
        config.addCache(cache13);
        config.addCache(cache14);

        CacheConfiguration cache15 = new CacheConfiguration("customer_by_id", 50);
        cache15.setMemoryStoreEvictionPolicy("LRU");
        cache15.setTimeToLiveSeconds(60);

        CacheConfiguration cache16 = new CacheConfiguration("all_customers", 100);
        cache16.setMemoryStoreEvictionPolicy("LRU");
        cache16.setTimeToLiveSeconds(300);

        CacheConfiguration cache17 = new CacheConfiguration("guest_by_id", 50);
        cache17.setMemoryStoreEvictionPolicy("LRU");
        cache17.setTimeToLiveSeconds(60);

        CacheConfiguration cache18 = new CacheConfiguration("all_guests", 100);
        cache18.setMemoryStoreEvictionPolicy("LRU");
        cache18.setTimeToLiveSeconds(300);

        config.addCache(cache15);
        config.addCache(cache16);
        config.addCache(cache17);
        config.addCache(cache18);

        CacheConfiguration cache19 = new CacheConfiguration("img_by_id", 60);
        cache19.setMemoryStoreEvictionPolicy("LRU");
        cache19.setTimeToLiveSeconds(60);

        config.addCache(cache19);

        CacheConfiguration cache20 = new CacheConfiguration("all_messages", 100);
        cache20.setMemoryStoreEvictionPolicy("LRU");
        cache20.setTimeToLiveSeconds(120);

        CacheConfiguration cache21 = new CacheConfiguration("msg_by_id", 50);
        cache21.setMemoryStoreEvictionPolicy("LRU");
        cache21.setTimeToLiveSeconds(60);

        config.addCache(cache20);
        config.addCache(cache21);

        CacheConfiguration cache22 = new CacheConfiguration("all_reviews", 100);
        cache22.setMemoryStoreEvictionPolicy("LRU");
        cache22.setTimeToLiveSeconds(120);

        CacheConfiguration cache23 = new CacheConfiguration("review_by_id", 50);
        cache23.setMemoryStoreEvictionPolicy("LRU");
        cache23.setTimeToLiveSeconds(60);

        CacheConfiguration cache24 = new CacheConfiguration("avg_rating", 100);
        cache24.setMemoryStoreEvictionPolicy("LRU");
        cache24.setTimeToLiveSeconds(120);

        config.addCache(cache22);
        config.addCache(cache23);
        config.addCache(cache24);

        CacheConfiguration cache25 = new CacheConfiguration("wish_by_id", 50);
        cache25.setMemoryStoreEvictionPolicy("LRU");
        cache25.setTimeToLiveSeconds(60);

        CacheConfiguration cache26 = new CacheConfiguration("all_wishes", 100);
        cache26.setMemoryStoreEvictionPolicy("LRU");
        cache26.setTimeToLiveSeconds(120);

        config.addCache(cache25);
        config.addCache(cache26);


        return net.sf.ehcache.CacheManager.newInstance(config);
    }

}
