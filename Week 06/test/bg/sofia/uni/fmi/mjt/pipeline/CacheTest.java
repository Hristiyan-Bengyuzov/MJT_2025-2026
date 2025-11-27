package bg.sofia.uni.fmi.mjt.pipeline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {
    Cache cache;

    @BeforeEach
    void setUp() {
        cache = new Cache();
    }

    @Test
    void testCacheShouldBeEmptyInitially() {
        assertTrue(cache.isEmpty(), "Cache should initially be empty.");
    }

    @Test
    void testCacheValueShouldStoreEntry() {
        cache.cacheValue("ala", "bala");

        assertFalse(cache.isEmpty(), "Cache should not be empty");
        assertEquals("bala", cache.getCachedValue("ala"), "Cache should properly store key-value.");
    }

    @Test
    void testCacheValueShouldOverwriteExistingValue() {
        cache.cacheValue("ala", "bala1");
        cache.cacheValue("ala", "bala2");

        assertEquals("bala2", cache.getCachedValue("ala"), "Value should be overwritten.");
    }

    @Test
    void testContainsKeyShouldReturnTrueForStoredKey() {
        cache.cacheValue("ala", "bala");

        assertTrue(cache.containsKey("ala"), "Stored key should be in cache.");
    }

    @Test
    void testContainsKeyShouldReturnFalseForMissingKey() {
        assertFalse(cache.containsKey("missing"), "Missing key should return false.");
    }

    @Test
    void testGetCachedValueShouldReturnNullForMissingKey() {
        assertNull(cache.getCachedValue("missing"), "Missing key should be null.");
    }

    @Test
    void testClearShouldEmptyCache() {
        cache.cacheValue("ala", "bala");
        cache.clear();

        assertTrue(cache.isEmpty(), "Cache should be empty after clear.");
        assertFalse(cache.containsKey("ala"), "Cache should not have key after clear.");
    }

    @Test
    void testCacheValueShouldThrowExceptionWhenKeyNull() {
        assertThrows(IllegalArgumentException.class, () -> cache.cacheValue(null, "value"),
                "IllegalArgumentException should be thrown when key is null.");
    }

    @Test
    void testCacheValueThrowsExceptionWhenValueNull() {
        assertThrows(IllegalArgumentException.class, () -> cache.cacheValue("key", null),
                "IllegalArgumentException should be thrown when value is null.");
    }

    @Test
    void testGetCachedValueThrowsExceptionWhenKeyNull() {
        assertThrows(IllegalArgumentException.class, () -> cache.getCachedValue(null),
                "IllegalArgumentException should be thrown when key is null.");
    }

    @Test
    void testContainsKeyThrowsExceptionWhenKeyNull() {
        assertThrows(IllegalArgumentException.class, () -> cache.containsKey(null),
                "IllegalArgumentException should be thrown when key is null.");
    }
}