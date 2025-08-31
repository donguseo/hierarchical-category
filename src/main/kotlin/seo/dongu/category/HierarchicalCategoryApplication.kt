package seo.dongu.category

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
class HierarchicalCategoryApplication

fun main(args: Array<String>) {
  runApplication<HierarchicalCategoryApplication>(*args)
}
