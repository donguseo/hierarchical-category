package seo.dongu.category.dto

data class CategoryTreeDto(
  val id: Long,
  val name: String,
  val parentId: Long?,
  val children: List<CategoryTreeDto>
)