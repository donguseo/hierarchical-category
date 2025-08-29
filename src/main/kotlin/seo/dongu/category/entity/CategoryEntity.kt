package seo.dongu.category.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Table(
  name = "categories",
  indexes = [
    Index(name = "idx_category_name", columnList = "name"),
    Index(name = "idx_category_parent_id", columnList = "parent_id")
  ]
)
@Entity
class CategoryEntity(
  @Column(name = "name", nullable = false)
  var name: String,
  @Column(name = "parent_id")
  var parentId: Long? = null
) : BaseEntity() {


}