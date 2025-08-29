package seo.dongu.category.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import seo.dongu.category.entity.CategoryEntity

interface CategoryRepository : JpaRepository<CategoryEntity, Long> {

  @Query("SELECT c.id FROM CategoryEntity c WHERE c.parentId IS NULL")
  fun findIdByParentIdIsNull(): List<Long>
}