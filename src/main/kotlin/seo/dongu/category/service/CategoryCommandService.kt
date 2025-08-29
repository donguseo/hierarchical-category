package seo.dongu.category.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import seo.dongu.category.dto.CategoryTreeDto
import seo.dongu.category.dto.buildCategoryTree
import seo.dongu.category.entity.CategoryEntity
import seo.dongu.category.entity.CategoryTreeEntity
import seo.dongu.category.repository.CategoryRepository
import seo.dongu.category.repository.CategoryTreeRepository

@Service
@Transactional
class CategoryCommandService(
  private val categoryRepository: CategoryRepository,
  private val categoryTreeRepository: CategoryTreeRepository
) {

  fun createCategory(name: String, parentId: Long? = null): CategoryTreeDto {
    require(parentId == null || categoryRepository.existsById(parentId)) {
      "Parent category not found with id: $parentId"
    }

    val savedCategory = categoryRepository.save(CategoryEntity(name = name, parentId = parentId))

    val selfEntry = CategoryTreeEntity(
      ancestorId = savedCategory.id!!,
      descendantId = savedCategory.id!!,
      depth = 0
    )
    categoryTreeRepository.save(selfEntry)

    if (parentId != null) {
      val parentAncestors = categoryTreeRepository.findByDescendantId(parentId)
      parentAncestors.forEach { ancestor ->
        val treeEntry = CategoryTreeEntity(
          ancestorId = ancestor.ancestorId,
          descendantId = savedCategory.id!!,
          depth = ancestor.depth + 1
        )
        categoryTreeRepository.save(treeEntry)
      }
    }
    return buildCategoryTree(savedCategory.id!!, listOf(savedCategory))
  }

  fun deleteCategory(categoryId: Long) {
    require(categoryRepository.existsById(categoryId)) {
      "Category not found with id: $categoryId"
    }

    val descendantIds = categoryTreeRepository.findByAncestorId(categoryId)
      .map { it.descendantId }
      .distinct()

    val treeEntriesToDelete = descendantIds.flatMap { descendantId ->
      categoryTreeRepository.findByDescendantId(descendantId) +
          categoryTreeRepository.findByAncestorId(descendantId)
    }.distinct()

    categoryTreeRepository.deleteAll(treeEntriesToDelete)

    categoryRepository.deleteAllById(descendantIds)
  }
}