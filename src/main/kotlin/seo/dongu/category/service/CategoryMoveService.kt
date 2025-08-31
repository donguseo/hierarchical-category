package seo.dongu.category.service

import org.springframework.stereotype.Service

@Service
class CategoryMoveService(
  private val categoryQueryService: CategoryQueryService,
  private val categoryMoveCommandService: CategoryMoveCommandService,
  private val categoryCacheService: CategoryCacheService
) {

  fun moveCategory(categoryId: Long, newParentId: Long?) {
    val ancestorIds = categoryQueryService.getAllAncestorIds(categoryId)

    categoryMoveCommandService.moveCategory(categoryId, newParentId)

    val evictIds = if (newParentId != null) {
      ancestorIds + categoryQueryService.getAllAncestorIds(categoryId) + categoryId
    } else {
      ancestorIds + categoryId
    }
    evictIds.forEach {
      categoryCacheService.evictCategory(it)
    }
    categoryCacheService.evictAllCategories()
  }
}