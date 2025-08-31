package seo.dongu.category.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import seo.dongu.category.dto.CategoryTreeDto
import seo.dongu.category.dto.buildCategoryTree
import seo.dongu.category.repository.CategoryRepository
import seo.dongu.category.repository.CategoryTreeRepository

@Service
@Transactional(readOnly = true)
class CategoryQueryService(
  private val categoryTreeRepository: CategoryTreeRepository,
  private val categoryRepository: CategoryRepository
) {

  fun getCategory(categoryId: Long): CategoryTreeDto {
    val categories = categoryTreeRepository.findCategoriesByAncestorId(categoryId)
    return buildCategoryTree(categoryId, categories)
  }

  fun getAllCategories(): List<CategoryTreeDto> {
    val rootIds = categoryRepository.findIdByParentIdIsNull()
    return rootIds.map(this::getCategory)
  }

  fun getAllAncestorIds(categoryId: Long): List<Long> {
    return categoryTreeRepository.findByDescendantId(categoryId)
      .map { it.ancestorId }
      .filter { it != categoryId }
  }
}