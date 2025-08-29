package seo.dongu.category.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import seo.dongu.category.entity.CategoryTreeEntity
import seo.dongu.category.repository.CategoryRepository
import seo.dongu.category.repository.CategoryTreeRepository

@Service
@Transactional
class CategoryMoveService(
  private val categoryRepository: CategoryRepository,
  private val categoryTreeRepository: CategoryTreeRepository
) {

  fun moveCategory(categoryId: Long, newParentId: Long?) {
    val category = categoryRepository.findById(categoryId)
      .orElseThrow { IllegalArgumentException("Category not found with id: $categoryId") }

    if (newParentId != null) {
      require(categoryRepository.existsById(newParentId)) {
        "Parent category not found with id: $newParentId"
      }

      val descendants = categoryTreeRepository.findByAncestorId(categoryId)
        .map { it.descendantId }
      require(!descendants.contains(newParentId)) {
        "Cannot move category to its own descendant"
      }
    }

    val allDescendantIds = categoryTreeRepository.findByAncestorId(categoryId)
      .map { it.descendantId }
      .distinct()

    val oldTreeEntries = allDescendantIds.flatMap { descendantId ->
      categoryTreeRepository.findByDescendantId(descendantId)
        .filter { it.ancestorId !in allDescendantIds }
    }
    categoryTreeRepository.deleteAll(oldTreeEntries)

    category.parentId = newParentId
    categoryRepository.save(category)

    if (newParentId != null) {
      val newAncestors = categoryTreeRepository.findByDescendantId(newParentId)

      val newTreeEntries = mutableListOf<CategoryTreeEntity>()
      for (descendantId in allDescendantIds) {
        val depthFromRoot = if (descendantId == categoryId) {
          0
        } else {
          categoryTreeRepository.findByAncestorId(categoryId)
            .find { it.descendantId == descendantId }?.depth ?: 0
        }

        for (ancestor in newAncestors) {
          newTreeEntries.add(
            CategoryTreeEntity(
              ancestorId = ancestor.ancestorId,
              descendantId = descendantId,
              depth = ancestor.depth + 1 + depthFromRoot
            )
          )
        }
      }
      categoryTreeRepository.saveAll(newTreeEntries)
    }
  }
}