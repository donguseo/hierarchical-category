package seo.dongu.category.service

import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import seo.dongu.category.dto.CategoryTreeDto

@Service
class CategoryCacheService(
  private val categoryQueryService: CategoryQueryService
) {

  private val log = KotlinLogging.logger {}


  companion object {
    private const val CACHE_GET_CATEGORY = "getCategory"
    private const val CACHE_GET_ALL_CATEGORY = "getAllCategories"
  }

  @Cacheable(value = [CACHE_GET_CATEGORY], key = "#categoryId")
  fun getCategory(categoryId: Long): CategoryTreeDto {
    return categoryQueryService.getCategory(categoryId)
  }

  @Cacheable(value = [CACHE_GET_ALL_CATEGORY])
  fun getAllCategories(): List<CategoryTreeDto> {
    return categoryQueryService.getAllCategories()
  }

  @CacheEvict(value = [CACHE_GET_CATEGORY], key = "#categoryId")
  fun evictCategory(categoryId: Long) {
    log.info { "Evicting cache for categoryId: $categoryId" }
  }

  @CacheEvict(value = [CACHE_GET_ALL_CATEGORY], allEntries = true)
  fun evictAllCategories() {
    log.info { "Evicting all category cache" }
  }
}