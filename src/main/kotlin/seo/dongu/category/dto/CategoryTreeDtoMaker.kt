package seo.dongu.category.dto

import seo.dongu.category.entity.CategoryEntity

fun buildCategoryTree(categoryId: Long, categories: List<CategoryEntity>): CategoryTreeDto {
  val parentChildMap = categories
    .filter { it.parentId != null }
    .groupBy(
      keySelector = { it.parentId!! },
      valueTransform = { it.id!! }
    )
  val categoriesMap = categories.associateBy { it.id!! }
  val nodeDtoMap = mutableMapOf<Long, CategoryTreeDto>()
  val queue = mutableListOf(categoryId)
  val visited = mutableSetOf<Long>()

  while (queue.isNotEmpty()) {
    val currentId = queue.removeAt(0)

    if (currentId in visited) continue
    visited.add(currentId)

    val category = categoriesMap[currentId] ?: continue

    val node = CategoryTreeDto(
      id = category.id!!,
      name = category.name,
      parentId = category.parentId,
      children = mutableListOf()
    )
    if (node.parentId != null) {
      (nodeDtoMap[node.parentId]?.children as MutableList<CategoryTreeDto>?)?.add(node)
    }

    nodeDtoMap[currentId] = node

    val childrenIds = parentChildMap[currentId] ?: emptyList()
    queue.addAll(childrenIds.filter { it in categoriesMap })
  }

  return nodeDtoMap[categoryId] ?: throw IllegalArgumentException("Category not found: $categoryId")
}