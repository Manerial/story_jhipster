package com.jher.nid_aux_histoires.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.jher.nid_aux_histoires.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.jher.nid_aux_histoires.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.jher.nid_aux_histoires.domain.User.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.Authority.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.User.class.getName() + ".authorities");
            createCache(cm, com.jher.nid_aux_histoires.domain.Book.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.Book.class.getName() + ".parts");
            createCache(cm, com.jher.nid_aux_histoires.domain.Book.class.getName() + ".images");
            createCache(cm, com.jher.nid_aux_histoires.domain.Part.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.Part.class.getName() + ".chapters");
            createCache(cm, com.jher.nid_aux_histoires.domain.Part.class.getName() + ".images");
            createCache(cm, com.jher.nid_aux_histoires.domain.Chapter.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.Chapter.class.getName() + ".scenes");
            createCache(cm, com.jher.nid_aux_histoires.domain.Chapter.class.getName() + ".images");
            createCache(cm, com.jher.nid_aux_histoires.domain.Scene.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.Scene.class.getName() + ".images");
            createCache(cm, com.jher.nid_aux_histoires.domain.Image.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.Image.class.getName() + ".bookToCovers");
            createCache(cm, com.jher.nid_aux_histoires.domain.Image.class.getName() + ".books");
            createCache(cm, com.jher.nid_aux_histoires.domain.Image.class.getName() + ".parts");
            createCache(cm, com.jher.nid_aux_histoires.domain.Image.class.getName() + ".chapters");
            createCache(cm, com.jher.nid_aux_histoires.domain.Image.class.getName() + ".scenes");
            createCache(cm, com.jher.nid_aux_histoires.domain.Idea.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.WordAnalysis.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.Comment.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.Bonus.class.getName());
            createCache(cm, com.jher.nid_aux_histoires.domain.Library.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
