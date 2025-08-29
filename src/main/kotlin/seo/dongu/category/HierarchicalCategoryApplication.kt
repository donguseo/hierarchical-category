package seo.dongu.category

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class HierarchicalCategoryApplication

fun main(args: Array<String>) {
  runApplication<HierarchicalCategoryApplication>(*args)
}
