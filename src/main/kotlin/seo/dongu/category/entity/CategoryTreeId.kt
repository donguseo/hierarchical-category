package seo.dongu.category.entity

import java.io.Serializable

data class CategoryTreeId(
  val ancestorId: Long = 0,
  val descendantId: Long = 0
) : Serializable