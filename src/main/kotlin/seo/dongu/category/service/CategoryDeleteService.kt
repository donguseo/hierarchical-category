package seo.dongu.category.service

import org.springframework.stereotype.Service

@Service
class CategoryDeleteService(
  private val categoryCommandService: CategoryCommandService,
  private val categoryQueryService: CategoryQueryService,
  private val categoryCacheService: CategoryCacheService
) {

  fun deleteCategory(categoryId: Long) {
    val allAncestorIds = categoryQueryService.getAllAncestorIds(categoryId)

    categoryCommandService.deleteCategory(categoryId)

    allAncestorIds.forEach {
      categoryCacheService.evictCategory(it)
    }
    categoryCacheService.evictAllCategories()
  }
}