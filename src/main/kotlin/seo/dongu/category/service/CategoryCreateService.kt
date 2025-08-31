package seo.dongu.category.service

import org.springframework.stereotype.Service
import seo.dongu.category.dto.CategoryTreeDto

@Service
class CategoryCreateService(
  private val categoryCommandService: CategoryCommandService,
  private val categoryQueryService: CategoryQueryService,
  private val categoryCacheService: CategoryCacheService
) {

  fun createCategory(name: String, parentId: Long? = null): CategoryTreeDto {
    val category = categoryCommandService.createCategory(name, parentId)

    if (parentId != null) {
      categoryQueryService.getAllAncestorIds(category.id).forEach {
        categoryCacheService.evictCategory(it)
      }
      categoryCacheService.evictAllCategories()
    }

    return category
  }
}